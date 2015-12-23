package misono.mvp.compile;

import com.google.auto.common.SuperficialValidation;
import com.google.auto.service.AutoService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import misono.smartmvp.annotation.InjectPresenter;
import misono.smartmvp.annotation.Presenter;

@AutoService(Processor.class)
public class MvpProcessor extends AbstractProcessor {
    private Elements elementUtils;
    private Types typeUtils;
    private Filer filer;
    private Messager messager;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);


        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }

    Map<String, BindingClassInfo> presenterCache = Maps.newHashMap();
    Map<String, BindingClassInfo> bundleCache = Maps.newHashMap();

    class BindingClassInfo {

        public BindingClassInfo(String packageName, Element className) {
            this.packageName = packageName;
            this.className = className;
        }

        String packageName;
        Element className;
        List<Element> fields = Lists.newArrayList();

        @Override
        public String toString() {
            return "BindingClassInfo{" +
                    "packageName='" + packageName + '\'' +
                    ", className='" + className + '\'' +
                    ", fields=" + fields +
                    '}';
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        messager.printMessage(Diagnostic.Kind.NOTE, "--------------------------");

        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Presenter.class)) {
            if (!SuperficialValidation.validateElement(annotatedElement)) continue;

            Element classElement = annotatedElement.getEnclosingElement();
            String packageName = elementUtils.getPackageOf(annotatedElement).getQualifiedName().toString();

            BindingClassInfo classInfo = presenterCache.get(packageName + classElement.getSimpleName());
            if (classInfo == null) {
                classInfo = new BindingClassInfo(packageName, classElement);
                presenterCache.put(packageName + classElement.getSimpleName(), classInfo);
            }

            classInfo.fields.add(annotatedElement);
        }

        for (BindingClassInfo classInfo : presenterCache.values()) {
            TypeElement typeElement = (TypeElement) typeUtils.asElement(classInfo.fields.get(0).asType());
            Element mvpPresenter = typeUtils.asElement(typeElement.getSuperclass());

            TypeName arrayListType = ParameterizedTypeName.get(
                    ClassName.get(ArrayList.class),
                    ParameterizedTypeName.get(ClassName.get((TypeElement) mvpPresenter), WildcardTypeName.subtypeOf(Object.class)));

            ParameterSpec paraAct = ParameterSpec.builder(TypeName.get(classInfo.className.asType()), "activity").build();
            MethodSpec.Builder injectBuilder = MethodSpec.methodBuilder("inject")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(arrayListType)
                    .addParameter(paraAct);

            messager.printMessage(Diagnostic.Kind.NOTE, arrayListType.toString());

            injectBuilder.addStatement("$T result = new $T()", arrayListType, arrayListType);

            for (Element field : classInfo.fields) {
                injectBuilder.addStatement("$N." + field.getSimpleName() + " = new $T()", paraAct, field.asType());
                injectBuilder.addStatement("$N." + field.getSimpleName() + ".setView($N)", paraAct, paraAct);
                injectBuilder.addStatement("result.add($N." + field.getSimpleName() + ")", paraAct);
            }

            injectBuilder.addStatement("return result");

            MethodSpec method = injectBuilder.build();


            TypeSpec injectClass = TypeSpec.classBuilder(classInfo.className.getSimpleName() + "$$PresenterBinder")
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(
                            ParameterizedTypeName.get(ClassName.get(InjectPresenter.class),
                                    TypeName.get(classInfo.className.asType()),
                                    arrayListType
                            )
                    ).addMethod(method).build();

            messager.printMessage(Diagnostic.Kind.NOTE, injectClass.toString());

            JavaFile javaFile = JavaFile.builder(classInfo.packageName, injectClass)
                    .build();

            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Sets.newHashSet(Presenter.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
