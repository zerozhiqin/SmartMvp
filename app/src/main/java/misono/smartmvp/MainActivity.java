package misono.smartmvp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import misono.smartmvp.annotation.Presenter;
import misono.smartmvp.library.MvpActivity;
import misono.smartmvp.pages.CryingPresenter;
import misono.smartmvp.pages.CryingView;
import misono.smartmvp.pages.HelloPresenter;
import misono.smartmvp.pages.HelloView;
import misono.smartmvp.pages.SleepingPresenter;

public class MainActivity extends MvpActivity implements HelloView, CryingView {

    @Presenter
    CryingPresenter cryingPresenter;
    @Presenter
    HelloPresenter helloPresenter;
    @Presenter
    SleepingPresenter sleepingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cryingPresenter.whyYouCry();
                helloPresenter.sayHello();
                sleepingPresenter.sleep();
            }
        });

    }

    @Override
    protected int fromLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void sayHello(String who) {
        Log.v("MM", "Hello by " + who);
    }

    @Override
    public void cryFor(String what) {
        Log.v("MM", "I am crying for " + what);
    }


}


