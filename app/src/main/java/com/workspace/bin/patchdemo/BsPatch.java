package com.workspace.bin.patchdemo;

/**
 * Created by bin on 2017/3/16.
 */

public class BsPatch {
    static {
        System.loadLibrary("bsdiff");
    }
    public static native int bspatch(String oldApk, String newApk, String patch);
}
