package me.reed.sample;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import me.reed.permissionaccess.PermissionAccess;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionAccess.getInstance().requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)
                        .setRationale("测试")
                        .setCallback(new PermissionAccess.PermissionCallback() {
                            @Override
                            public void onPermissionError(List<String> permissions) {
                                for (int i = 0; i < permissions.size(); i++) {
                                    Log.i("未通过的权限", permissions.get(i));
                                }
                            }

                            @Override
                            public void onPermissionSuccess() {
                                Log.i("权限结果", "通过");
                            }
                        }).start(MainActivity.this);
            }
        });
    }
}
