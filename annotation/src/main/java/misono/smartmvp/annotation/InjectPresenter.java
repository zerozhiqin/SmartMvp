package misono.smartmvp.annotation;

public interface InjectPresenter<T, R> {
    R inject(T activity);
}
