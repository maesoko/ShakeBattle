package jp.ac.it_college.std.s13012.shakebattle;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class DataTransferService extends IntentService {

    private static final int SOCKET_TIMEOUT = 5000;
    public static final String ACTION_SEND_DATA = "jp.ac.it_college.std.s13012.shakebattle.SEND_DATA";
    public static final String ACTION_GET_DATA = "jp.ac.it_college.std.s13012.shakebattle.GET_DATA";
    public static final String EXTRAS_GROUP_OWNER_ADDRESS = "go_host";
    public static final String EXTRAS_GROUP_OWNER_PORT = "go_port";
    public static final int EXTRAS_PORT_NUMBER = 12345;

    private Context context;
    private TextView textView;

    public DataTransferService(String name) {
        super(name);
    }

    public DataTransferService() {
        super("DataTransferService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            if (intent.getAction().equals(ACTION_SEND_DATA)) {
                String host = intent.getExtras().getString(EXTRAS_GROUP_OWNER_ADDRESS);
                int port = intent.getExtras().getInt(EXTRAS_GROUP_OWNER_PORT);
                Socket socket = new Socket();
                String nextActivity = intent.getStringExtra("game_mode");
                int goalValue = intent.getIntExtra("goal", -1);

                Log.v(WaitOpponentActivity.TAG, "Opening client socket - ");
                socket.bind(null);
                socket.connect((new InetSocketAddress(host, port)), SOCKET_TIMEOUT);

                Log.d(WaitOpponentActivity.TAG, "Client socket - " + socket.isConnected());

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream()
                ));

                writer.append(nextActivity);
                writer.newLine();
                writer.append((char) goalValue);

                writer.flush();
                writer.close();
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
