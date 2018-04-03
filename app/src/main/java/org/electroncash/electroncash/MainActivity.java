package org.electroncash.electroncash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.os.Environment;

public class MainActivity extends AppCompatActivity {

    private ElectronRpc electronRpc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent serviceIntent = new Intent(this, ElectronService.class);
        String argument = this.getFilesDir().getAbsolutePath();
        String filesDirectory = argument;
        String app_root_dir = this.getAppRoot();
        serviceIntent.putExtra("androidPrivate", argument);
        serviceIntent.putExtra("androidArgument", app_root_dir);
        serviceIntent.putExtra("serviceEntrypoint", "main.py");
        serviceIntent.putExtra("pythonName", "python");
        serviceIntent.putExtra("pythonHome", app_root_dir);
        serviceIntent.putExtra("pythonPath", app_root_dir + ":" + app_root_dir + "/lib");
        serviceIntent.putExtra("serviceTitle", "Electron Cash");
        serviceIntent.putExtra("serviceDescription", "Electron Cash");
        serviceIntent.putExtra("pythonServiceArgument", "");
        serviceIntent.putExtra("androidExtDir", Environment.getExternalStorageDirectory().getPath() + "/" + BuildConfig.APPLICATION_ID);
        serviceIntent.putExtra("androidDataDir", getFilesDir().getPath() + "/data");
        this.startService(serviceIntent);
        this.electronRpc = new ElectronRpc(getFilesDir().getPath() + "/data");
        this.electronRpc.printVersion();
        //PythonInterface.runCommand("print('hello')");
    }

    public String getAppRoot() {
        String app_root =  getFilesDir().getAbsolutePath() + "/app";
        return app_root;
    }
}
