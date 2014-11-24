package jp.ac.it_college.std.s13012.shakebattle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;


public class WaitOpponentActivity extends Activity
        implements WifiP2pManager.ChannelListener, WiFiDirectBroadcastReceiver.OnReceiveListener
        , DeviceListFragment.DeviceActionListener, WifiP2pManager.ConnectionInfoListener{

    private Class destination;

    private final IntentFilter intentFilter = new IntentFilter();
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private WiFiDirectBroadcastReceiver receiver = null;
    public static String TAG = "WaitOpponentActivity";
    private DeviceListFragment deviceListFragment;
    private WifiP2pInfo wifiP2pInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_opponent);

        destination = (Class) getIntent().getSerializableExtra(BaseFragment.DESTINATION_CLASS);

        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);


        deviceListFragment = new DeviceListFragment();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Intent intent = new Intent(this, destination);
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
                Log.v(TAG, "discoverPeers success");
            }

            @Override
            public void onFailure(int i) {
                Log.v(TAG, "discoverPeers failure");
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
        Log.v(TAG, "onStateChanged");
    }

    @Override
    public void onPeersChanged() {
        Log.v(TAG, "onPeersChanged");
    }

    @Override
    public void onConnectionChanged() {
        Log.v(TAG, "onConnectionChanged");
        if (deviceListFragment.getDevice() != null) {
            if (deviceListFragment.getDevice().status == WifiP2pDevice.CONNECTED) {
                Toast.makeText(this, "接続が完了しました", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onThisDeviceChanged() {
        Log.v(TAG, "onThisDeviceChanged");
        deviceListFragment.updateThisDevice((WifiP2pDevice) receiver.getIntent().getParcelableExtra(
                WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));
    }

    /* implemented DeviceActionListener */
    @Override
    public void connect(WifiP2pConfig config) {

    }

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
        Log.v(TAG, "onConnectionInfoAvailable");
        this.wifiP2pInfo = wifiP2pInfo;
    }
}
