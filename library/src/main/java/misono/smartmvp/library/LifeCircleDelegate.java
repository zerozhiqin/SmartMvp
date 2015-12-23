package misono.smartmvp.library;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class LifeCircleDelegate {

    List<MvpPresenter<?>> presenters = new ArrayList<>();

    public static LifeCircleDelegate from(MvpPresenter<?>... presenters) {
        ArrayList<MvpPresenter<?>> presenterArray = new ArrayList<>();
        for (MvpPresenter<?> presenter : presenters) {
            presenterArray.add(presenter);
        }
        return from(new ArrayList<>(presenterArray));
    }

    public static LifeCircleDelegate from(List<MvpPresenter<?>> presenters) {
        LifeCircleDelegate lifeCircleDelegate = new LifeCircleDelegate();
        lifeCircleDelegate.presenters.addAll(presenters);
        return lifeCircleDelegate;
    }

    public void onCreate(Bundle savedInstanceState) {
        for (MvpPresenter<?> presenter : presenters) {
            presenter.created(savedInstanceState);
        }
    }

    public void onDestroy() {
        for (MvpPresenter<?> presenter : presenters) {
            presenter.destroy();
        }
        presenters.clear();
    }

    public void onNewIntent(Intent intent) {
        for (MvpPresenter<?> presenter : presenters) {
            presenter.newIntent(intent);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        for (MvpPresenter<?> presenter : presenters) {
            presenter.saveInstanceState(outState);
        }
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        for (MvpPresenter<?> presenter : presenters) {
            presenter.restoreInstanceState(savedInstanceState);
        }
    }

}
