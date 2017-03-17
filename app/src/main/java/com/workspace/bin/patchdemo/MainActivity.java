package com.workspace.bin.patchdemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button1, button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        button1 = (Button) findViewById(R.id.patch);
        button2 = (Button) findViewById(R.id.toast);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    public  String extract(Context context) {
        context = context.getApplicationContext();
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        String apkPath = applicationInfo.sourceDir;
        Log.d("chenbin", apkPath);
        return apkPath;
    }

    private void doBspatch() {
        final File destApk = new File(Environment.getExternalStorageDirectory(), "dest.apk");
        final File patch = new File(Environment.getExternalStorageDirectory(), "PATCH.patch");

        //一定要检查文件都存在

        BsPatch.bspatch(extract(this),
                destApk.getAbsolutePath(),
                patch.getAbsolutePath());

        if (destApk.exists())
            install(this, destApk.getAbsolutePath());
    }

    public  void install(Context context, String apkPath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.fromFile(new File(apkPath)),
                "application/vnd.android.package-archive");
        context.startActivity(i);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.patch:
                doBspatch();
                break;
            case R.id.toast:
                Toast.makeText(this,"a new apk",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
