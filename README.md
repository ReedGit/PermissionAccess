# PermissionAccess
An Android library to request permissions.

## Getting started

In your `build.gradle`:

```groovy
dependencies {
    compile 'me.reed.permissionaccess:permissionaccess:1.0.0'
}
```

## Usage

```java
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
```

License
-------

    Copyright 2017 Reed.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
