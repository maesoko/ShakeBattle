package jp.ac.it_college.std.s13012.shakebattle;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;

public class GroupOwnerSocket extends Thread {

    private ServerSocket serverSocket = null;
    public static final String TAG = "GroupOwnerSocket";
    private Context context;
    private SocketManager socketManager;

    public GroupOwnerSocket(Context context) {
        this.context = context;
        try {
            serverSocket = new ServerSocket(DataTransferService.EXTRAS_PORT_NUMBER);
            Log.d(TAG, "Socket Started");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                socketManager = new SocketManager(serverSocket.accept());
            } catch (IOException e) {
                try {
                    serverSocket.close();
                } catch (IOException e1) {

                }
                e.printStackTrace();
                break;
            }
        }
    }
}
