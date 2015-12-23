package misono.smartmvp.library;

import android.content.Intent;
import android.os.Bundle;

public interface MvpLifeCircle {

    void created(Bundle savedInstanceState);

    void newIntent(Intent intent);

    void destroy();

    void saveInstanceState(Bundle outState);

    void restoreInstanceState(Bundle savedInstanceState);

}
