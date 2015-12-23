package misono.smartmvp.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class MvpActivity extends AppCompatActivity {

    LifeCircleDelegate lifeCircleDelegate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(fromLayout());
        lifeCircleDelegate = LifeCircleDelegate.from(MvpInjector.inject(this));
        lifeCircleDelegate.onCreate(savedInstanceState);
    }

    protected abstract int fromLayout();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifeCircleDelegate.onDestroy();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lifeCircleDelegate.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lifeCircleDelegate.onSaveInstanceState(outState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        lifeCircleDelegate.onNewIntent(intent);
    }

}
