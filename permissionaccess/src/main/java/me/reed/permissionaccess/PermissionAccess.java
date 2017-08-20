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

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import java.util.List;

/**
 * @author reed on 2017/7/16
 */

public class PermissionAccess {

    static final PermissionAccess instance = new PermissionAccess();

    private PermissionAccess() {
    }

    public static PermissionAccess getInstance() {
        instance.callback = null;
        instance.rationale = null;
        instance.permissions = null;
        return instance;
    }


    public interface PermissionCallback {
        void onPermissionError(List<String> permissions);

        void onPermissionSuccess();
    }

    String rationale;
    PermissionCallback callback;
    String[] permissions;

    public PermissionAccess setRationale(String rationale) {
        this.rationale = rationale;
        return instance;
    }

    public PermissionAccess requestPermission(String... permissions) {
        this.permissions = permissions;
        return instance;
    }

    public void start(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (permissions == null || permissions.length == 0) {
                if (callback != null) {
                    callback.onPermissionSuccess();
                }
                return;
            }
            Intent intent = new Intent(activity, PermissionEmptyActivity.class);
            activity.startActivity(intent);
        } else {
            if (callback != null) {
                callback.onPermissionSuccess();
            }
        }
    }

    public PermissionAccess setCallback(PermissionCallback callback) {
        this.callback = callback;
        return instance;
    }

}
