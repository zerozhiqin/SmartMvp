# a smart android mvp framework

## usage

Activity:

```

public class MainActivity extends MvpActivity implements HelloView, CryingView {

    @Presenter
    CryingPresenter cryingPresenter;
    @Presenter
    HelloPresenter helloPresenter;
    @Presenter
    SleepingPresenter sleepingPresenter;

...

```

Presenter inject code will be generated when compile time looks like

```

public class MainActivity$$PresenterBinder implements InjectPresenter<MainActivity, ArrayList<MvpPresenter<?>>> {
  @Override
  public ArrayList<MvpPresenter<?>> inject(MainActivity activity) {
    ArrayList<MvpPresenter<?>> result = new ArrayList<MvpPresenter<?>>();
    activity.cryingPresenter = new CryingPresenter();
    activity.cryingPresenter.setView(activity);
    result.add(activity.cryingPresenter);
    activity.helloPresenter = new HelloPresenter();
    activity.helloPresenter.setView(activity);
    result.add(activity.helloPresenter);
    activity.sleepingPresenter = new SleepingPresenter();
    activity.sleepingPresenter.setView(activity);
    result.add(activity.sleepingPresenter);
    return result;
  }
}

```

see app module for more info


[http://www.tyz.ren/android-shi-yong-processor-bian-yi-shi-dai-ma-sheng-cheng/](http://www.tyz.ren/android-shi-yong-processor-bian-yi-shi-dai-ma-sheng-cheng/)
