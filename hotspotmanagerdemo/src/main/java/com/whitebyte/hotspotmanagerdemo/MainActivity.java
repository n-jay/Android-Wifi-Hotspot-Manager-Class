/*
 * Copyright 2013 WhiteByte (Nick Russler, Ahmet Yueksektepe).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.whitebyte.hotspotmanagerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.njay.hotshot.Client;
import com.njay.hotshot.HotShotController;


import java.util.ArrayList;

import info.whitebyte.hotspotmanager.ClientScanResult;
import info.whitebyte.hotspotmanager.FinishScanListener;
import info.whitebyte.hotspotmanager.WifiApManager;


public class MainActivity extends Activity {
    TextView textView1;
    WifiApManager wifiApManager;
    HotShotController hotShotController;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        textView1 = (TextView) findViewById(R.id.textView1);

        wifiApManager = new WifiApManager(this);
        hotShotController = new HotShotController(this);

        // force to show the settings page for demonstration purpose of this method
        //wifiApManager.showWritePermissionSettings(true);
        hotShotController.showWritePermissionSettings(false);

        scanAll();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //wifiApManager.showWritePermissionSettings(false);
        hotShotController.showWritePermissionSettings(false);
    }

    private void scanAll() {
        hotShotController.getClientList(false, new com.njay.hotshot.FinishScanListener() {
            @Override
            public void onFinishScan(ArrayList<Client> clients) {
                textView1.setText("WifiApState: " + wifiApManager.getWifiApState() + "\n\n");
                textView1.append("Clients: \n");
                for (Client clientlist : clients) {
                    textView1.append("####################\n");
                    textView1.append("IpAddr: " + clientlist.getIpAddress() + "\n");
                    textView1.append("Device: " + clientlist.getDevice() + "\n");
                    textView1.append("HWAddr: " + clientlist.getHwAddress() + "\n");
                    textView1.append("isReachable: " + clientlist.isReachable() + "\n");
                }
            }
        });
    }

    private void scanReachable() {
        hotShotController.getClientList(true, new com.njay.hotshot.FinishScanListener() {
            @Override
            public void onFinishScan(ArrayList<Client> clients) {
                textView1.setText("WifiApState: " + wifiApManager.getWifiApState() + "\n\n");
                textView1.append("Clients: \n");
                for (Client clientlist : clients) {
                    textView1.append("####################\n");
                    textView1.append("IpAddr: " + clientlist.getIpAddress() + "\n");
                    textView1.append("Device: " + clientlist.getDevice() + "\n");
                    textView1.append("HWAddr: " + clientlist.getHwAddress() + "\n");
                    textView1.append("isReachable: " + clientlist.isReachable() + "\n");
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "Get All Clients");
        menu.add(0,1,0,"Get reachable Clients");
        menu.add(0, 2, 0, "Open AP");
        menu.add(0, 3, 0, "Close AP");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                scanAll();
                break;
            case 2:
                hotShotController.setWifiApEnabled(null, true);
                break;
            case 3:
                hotShotController.setWifiApEnabled(null, false);
                break;
            case 1:
                scanReachable();
                break;
        }

        return super.onMenuItemSelected(featureId, item);
    }
}
