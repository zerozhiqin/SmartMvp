package misono.smartmvp.library;

import java.util.ArrayList;

import misono.smartmvp.annotation.InjectPresenter;

public class MvpInjector {

    public static ArrayList<MvpPresenter<?>> inject(Object activityOrFragment) {
        InjectPresenter<Object, ArrayList<MvpPresenter<?>>> injectPresenter = findInjector(activityOrFragment.getClass());
        return injectPresenter.inject(activityOrFragment);
    }

    private static InjectPresenter<Object, ArrayList<MvpPresenter<?>>> findInjector(Class<?> aClass) {
        try {
            return (InjectPresenter<Object, ArrayList<MvpPresenter<?>>>) Class.forName(aClass.getCanonicalName() + "$$PresenterBinder").newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


}
