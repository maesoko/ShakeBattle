package jp.ac.it_college.std.s13012.shakebattle;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketManager implements Runnable {

    private Socket socket = null;
    private InputStream inputStream;
    private OutputStream outputStream;

    public static final String TAG = "SocketManager";

    public SocketManager(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                bytes = inputStream.read(buffer);
                if (bytes == -1) {
                    break;
                }

                Log.v(TAG, "Rec:" + String.valueOf(buffer));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(byte[] buffer) {
        try {
            outputStream.write(buffer);
        } catch (IOException e) {
            Log.e(TAG, "Exception during write", e);
        }
    }
}