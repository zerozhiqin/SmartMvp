package misono.smartmvp.pages;

import misono.smartmvp.library.MvpPresenter;

public class SleepingPresenter extends MvpPresenter<HelloView> {

    public void sleep(){
        getView().sayHello("Sleeping beauty");
    }
}
