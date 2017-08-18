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
            Intent intent = new Intent(activity, EmptyActivity.class);
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
