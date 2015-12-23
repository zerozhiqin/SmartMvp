package misono.smartmvp.library;

import android.content.Intent;
import android.os.Bundle;

public class MvpPresenter<T extends MvpView> implements MvpLifeCircle{

    private T view;

    public void setView(T view) {
        this.view = view;
    }

    protected T getView() {
        return view;
    }

    @Override
    public void created(Bundle savedInstanceState) {

    }

    @Override
    public void newIntent(Intent intent) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void saveInstanceState(Bundle outState) {

    }

    @Override
    public void restoreInstanceState(Bundle savedInstanceState) {

    }
}
