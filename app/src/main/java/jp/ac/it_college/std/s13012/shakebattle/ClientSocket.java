package jp.ac.it_college.std.s13012.shakebattle;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientSocket extends Thread {

    public static final String TAG = "ClientSocket";
    private InetAddress groupOwnerAddress;
    private SocketManager socketManager;

    public ClientSocket(InetAddress groupOwnerAddress) {
        this.groupOwnerAddress = groupOwnerAddress;
    }

    @Override
    public void run() {
        Socket socket = new Socket();
        try {
            socket.bind(null);
            socket.connect(new InetSocketAddress(groupOwnerAddress.getHostAddress(),
                    DataTransferService.EXTRAS_PORT_NUMBER), DataTransferService.SOCKET_TIMEOUT);
            socketManager = new SocketManager(socket);
            new Thread(socketManager).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
