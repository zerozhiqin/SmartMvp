package misono.smartmvp.pages;

import misono.smartmvp.library.MvpPresenter;

public class HelloPresenter extends MvpPresenter<HelloView> {

    public void sayHello() {
        getView().sayHello("Misono");
    }

}
