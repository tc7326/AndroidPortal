package info.itloser.androidportal.adb;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ADBService extends Service {
    public ADBService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
