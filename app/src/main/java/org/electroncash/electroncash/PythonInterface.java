package org.electroncash.electroncash;

/**
 * Created by Marcel O'Neil on 2/19/18.
 */

public class PythonInterface {
    static {
        System.loadLibrary("main");
    }

    public static native void runCommand(String command);
}
