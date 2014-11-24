package jp.ac.it_college.std.s13012.shakebattle;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class DataTransferService extends IntentService{

    private static final int SOCKET_TIMEOUT = 5000;
    public static final String ACTION_SEND_DATA = "jp.ac.it_college.std.s13012.shakebattle.SEND_DATA";
    public static final String EXTRAS_GROUP_OWNER_ADDRESS = "go_host";
    public static final String EXTRAS_GROUP_OWNER_PORT = "go_port";
    public static final int EXTRAS_PORT_NUMBER = 8839;

    public DataTransferService(String name) {
        super(name);
    }

    public DataTransferService(){
        super("DataTransferService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.getAction().equals(ACTION_SEND_DATA)) {
//            String host = intent.getExtras().getString(EXTRAS_GROUP_OWNER_ADDRESS);
            Socket socket = new Socket();
            int port = intent.getExtras().getInt(EXTRAS_GROUP_OWNER_PORT);

            try {
                Log.v(WaitOpponentActivity.TAG, "Opening client socket - ");
                socket.bind(null);
                socket.connect((new InetSocketAddress(port)), SOCKET_TIMEOUT);
//                socket.connect((new InetSocketAddress(host, port)), SOCKET_TIMEOUT);

                Log.d(WaitOpponentActivity.TAG, "Client socket - " + socket.isConnected());

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream()
                ));
                writer.write("test");
                writer.flush();
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
