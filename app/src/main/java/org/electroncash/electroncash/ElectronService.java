package org.electroncash.electroncash;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import org.kivy.android.PythonService;

/**
 * Created by Marcel O'Neil on 2/11/18.
 */

public class ElectronService extends PythonService {
    /**
     * Binder given to clients
     */
    private final IBinder mBinder = new ElectronServiceBinder();

    public static void start(Context ctx, String pythonServiceArgument) {
        String argument = ctx.getFilesDir().getAbsolutePath();
        Intent intent = new Intent(ctx, ElectronService.class);
        intent.putExtra("androidPrivate", argument);
        intent.putExtra("androidArgument", argument);
        intent.putExtra("serviceEntrypoint", "{{ entrypoint }}");
        intent.putExtra("serviceTitle", "{{ name|capitalize }}");
        intent.putExtra("serviceDescription", "");
        intent.putExtra("pythonName", "{{ name }}");
        intent.putExtra("pythonHome", argument);
        intent.putExtra("androidUnpack", argument);
        intent.putExtra("pythonPath", argument + ":" + argument + "/lib");
        intent.putExtra("pythonServiceArgument", pythonServiceArgument);
        ctx.startService(intent);
    }

    public static void stop(Context ctx) {
        Intent intent = new Intent(ctx, ElectronService.class);
        ctx.stopService(intent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Class used for the client Binder. Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class ElectronServiceBinder extends Binder {
        ElectronService getService() {
            // Return this instance of ElectronService so clients can call public methods
            return ElectronService.this;
        }
    }
}
