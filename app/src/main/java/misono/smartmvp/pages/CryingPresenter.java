package misono.smartmvp.pages;

import android.os.Bundle;
import android.util.Log;

import misono.smartmvp.library.MvpPresenter;

public class CryingPresenter extends MvpPresenter<CryingView> {

    public void whyYouCry() {
        getView().cryFor("so many bugs");
    }


    @Override
    public void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        Log.v("Created", "AAA");
    }
}
