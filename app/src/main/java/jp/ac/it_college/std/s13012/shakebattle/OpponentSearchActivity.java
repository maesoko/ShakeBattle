package jp.ac.it_college.std.s13012.shakebattle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;


public class OpponentSearchActivity extends Activity
        implements WifiP2pManager.ChannelListener, WiFiDirectBroadcastReceiver.OnReceiveListener
        ,DeviceListFragment.DeviceActionListener{

    private IntentFilter intentFilter;
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private WiFiDirectBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opponent_search);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new DeviceListFragment())
                    .commit();
        }

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Intent intent = new Intent(this, TimeAttackActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new WiFiDirectBroadcastReceiver(this);
        registerReceiver(receiver, intentFilter);
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d("WiFiDirectBroadcastReceiver", "discoverPeers success");
            }

            @Override
            public void onFailure(int i) {
                Log.d("WiFiDirectBroadcastReceiver", "discoverPeers failure");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    /* implemented ChannelListener */
    @Override
    public void onChannelDisconnected() {

    }

    /* implemented OnReceiveListener */
    @Override
    public void onStateChanged() {

    }

    @Override
    public void onPeersChanged() {

    }

    @Override
    public void onConnectionChanged() {

    }

    @Override
    public void onThisDeviceChanged() {

    }

    /* implemented DeviceActionListener */
    @Override
    public void connect(WifiP2pConfig config) {

    }
}
