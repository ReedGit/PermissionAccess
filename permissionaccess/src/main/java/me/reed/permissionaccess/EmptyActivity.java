/*
 * Copyright 2017 Reed. https://github.com/ReedGit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.reed.permissionaccess;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author reed on 2017/7/16
 */
@TargetApi(23)
class EmptyActivity extends Activity {

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isShowRequest = false;
        final List<String> needPermissions = new ArrayList<>();
        for (String permission : PermissionAccess.instance.permissions) {
            int hasPermission = checkSelfPermission(permission);
            if (hasPermission == PackageManager.PERMISSION_GRANTED) {
                continue;
            }
            if (!isShowRequest && shouldShowRequestPermissionRationale(permission)) {
                isShowRequest = true;
            }
            needPermissions.add(permission);
        }
        final String[] permissions = new String[needPermissions.size()];
        needPermissions.toArray(permissions);
        if (!isShowRequest) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        } else {
            if (!TextUtils.isEmpty(PermissionAccess.instance.rationale)) {
                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setMessage(PermissionAccess.instance.rationale)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (PermissionAccess.instance.callback != null) {
                                    PermissionAccess.instance.callback.onPermissionError(needPermissions);
                                }
                                finish();
                            }
                        })
                        .create().show();
            } else {
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (PermissionAccess.instance.callback != null) {
                    List<String> permissionList = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            permissionList.add(permissions[i]);
                        }
                    }
                    if (permissionList.size() > 0) {
                        PermissionAccess.instance.callback.onPermissionError(permissionList);
                    } else {
                        PermissionAccess.instance.callback.onPermissionSuccess();
                    }
                }
                break;
        }
        finish();
    }
}
