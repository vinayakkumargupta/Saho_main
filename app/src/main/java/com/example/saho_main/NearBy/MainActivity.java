/*
 * Copyright (C) 2019-2022 HERE Europe B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * License-Filename: LICENSE
 */

package com.example.saho_main.NearBy;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.saho_main.R;
import com.here.sdk.core.engine.SDKBuildInformation;
import com.here.sdk.core.engine.SDKNativeEngine;
import com.here.sdk.core.engine.SDKOptions;
import com.here.sdk.core.errors.InstantiationErrorException;
import com.here.sdk.mapview.MapError;
import com.here.sdk.mapview.MapScene;
import com.here.sdk.mapview.MapScheme;
import com.here.sdk.mapview.MapView;

public class MainActivity extends AppCompatActivity {




    private static final String TAG = MainActivity.class.getSimpleName();

    private PermissionsRequestor permissionsRequestor;
    private MapView mapView;
    private MapView mapView1;
    private MapView mapView2;
    private SearchExample searchExample;
    private SearchExample1 searchExample1;
    private RoutingExample routingExample;
    private RoutingExample1 routingExample1;
    private SearchExample2 searchExample2;
    private RoutingExample2 routingExample2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Usually, you need to initialize the HERE SDK only once during the lifetime of an application.
        initializeHERESDK();

        setContentView(R.layout.activity_near_by);

        Log.d("", "HERE SDK version: " + SDKBuildInformation.sdkVersion().versionName);

        // Get a MapView instance from layout.
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView1 = findViewById(R.id.map_view1);
        mapView1.onCreate(savedInstanceState);
        mapView2 = findViewById(R.id.map_view2);
        mapView2.onCreate(savedInstanceState);


        handleAndroidPermissions();
    }

    private void initializeHERESDK() {
        // Set your credentials for the HERE SDK.
        String accessKeyID = "ieDKASyaGTe5CxAP3jFLiA";
        String accessKeySecret = "rfxnfCkT1H3GRc__kjuiMW6bLd6HZII_zzfL8wseXgS01ctLvBeWjdUDsFPpRLW1w9dZ26ZWp3lqubLMc_9JDQ";
        SDKOptions options = new SDKOptions(accessKeyID, accessKeySecret);
        try {
            Context context = this;
            SDKNativeEngine.makeSharedInstance(context, options);
        } catch (InstantiationErrorException e) {
            throw new RuntimeException("Initialization of HERE SDK failed: " + e.error.name());
        }
    }

    private void handleAndroidPermissions() {
        permissionsRequestor = new PermissionsRequestor(this);
        permissionsRequestor.request(new PermissionsRequestor.ResultListener(){

            @Override
            public void permissionsGranted() {
                loadMapScene();
                loadMapScene1();
                loadMapScene2();
            }

            @Override
            public void permissionsDenied() {
                Log.e(TAG, "Permissions denied by user.");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsRequestor.onRequestPermissionsResult(requestCode, grantResults);
    }

    private void loadMapScene() {
        // Load a scene from the HERE SDK to render the map with a map scheme.
        mapView.getMapScene().loadScene(MapScheme.NORMAL_DAY, new MapScene.LoadSceneCallback() {
            @Override
            public void onLoadScene(@Nullable MapError mapError) {
                if (mapError == null) {
                    searchExample = new SearchExample(MainActivity.this, mapView);
                    routingExample = new RoutingExample(MainActivity.this, mapView);
                    //searchExample2 = new SearchExample2(MainActivity.this, mapView);
                } else {
                    Log.d(TAG, "onLoadScene failed: " + mapError.toString());
                }
            }
        });
    }
    private void loadMapScene1() {
        // Load a scene from the HERE SDK to render the map with a map scheme.
        mapView1.getMapScene().loadScene(MapScheme.NORMAL_DAY, new MapScene.LoadSceneCallback() {
            @Override
            public void onLoadScene(@Nullable MapError mapError) {
                if (mapError == null) {
                    searchExample1 = new SearchExample1(MainActivity.this, mapView1);
                    routingExample1 = new RoutingExample1(MainActivity.this, mapView1);
                    //searchExample2 = new SearchExample2(MainActivity.this, mapView);
                } else {
                    Log.d(TAG, "onLoadScene failed: " + mapError.toString());
                }
            }
        });
    }
    private void loadMapScene2() {
        // Load a scene from the HERE SDK to render the map with a map scheme.
        mapView2.getMapScene().loadScene(MapScheme.NORMAL_DAY, new MapScene.LoadSceneCallback() {
            @Override
            public void onLoadScene(@Nullable MapError mapError) {
                if (mapError == null) {
                    searchExample2 = new SearchExample2(MainActivity.this, mapView2);
                    routingExample2 = new RoutingExample2(MainActivity.this, mapView2);
                    //searchExample2 = new SearchExample2(MainActivity.this, mapView);
                } else {
                    Log.d(TAG, "onLoadScene failed: " + mapError.toString());
                }
            }
        });
    }
    public void searchExampleButtonClicked(View view) {
        mapView1.setVisibility(View.GONE);
        mapView2.setVisibility(View.GONE);
        mapView.setVisibility(View.VISIBLE);


        searchExample.onSearchButtonClicked();
        routingExample.addRoute();
    }


    public void geocodeAnAddressButtonClicked(View view) {
        mapView2.setVisibility(View.GONE);
        mapView.setVisibility(View.GONE);
        mapView1.setVisibility(View.VISIBLE);
        searchExample1.onSearchButtonClicked1();
        routingExample1.addRoute();
    }

    public void geocodeAnAddressButtonClicked1(View view) {
        mapView1.setVisibility(View.GONE);
        mapView.setVisibility(View.GONE);
        mapView2.setVisibility(View.VISIBLE);
        searchExample2.onSearchButtonClicked();
        routingExample2.addRoute();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        mapView1.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        mapView1.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        mapView1.onDestroy();
        disposeHERESDK();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        mapView.onSaveInstanceState(outState);
        mapView1.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
    
    private void disposeHERESDK() {
        // Free HERE SDK resources before the application shuts down.
        // Usually, this should be called only on application termination.
        // Afterwards, the HERE SDK is no longer usable unless it is initialized again.
        SDKNativeEngine sdkNativeEngine = SDKNativeEngine.getSharedInstance();
        if (sdkNativeEngine != null) {
            sdkNativeEngine.dispose();
            // For safety reasons, we explicitly set the shared instance to null to avoid situations,
            // where a disposed instance is accidentally reused.
            SDKNativeEngine.setSharedInstance(null);
        }
    }
}
