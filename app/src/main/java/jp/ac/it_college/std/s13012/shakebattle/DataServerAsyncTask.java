package jp.ac.it_college.std.s13012.shakebattle;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DataServerAsyncTask extends AsyncTask<Void, Void, String> {

    private Context context;
    private int goalValue;
    private String gameMode;

    public DataServerAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            ServerSocket serverSocket = new ServerSocket(DataTransferService.EXTRAS_PORT_NUMBER);
            Log.v(OpponentSearchActivity.TAG, "Server: Socket opened");
            Socket client = serverSocket.accept();
            Log.v(OpponentSearchActivity.TAG, "Server: connection done");

            DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
            goalValue = dataInputStream.readInt();
            gameMode = dataInputStream.readUTF();

            dataInputStream.close();
            client.close();
            serverSocket.close();

            Log.v(OpponentSearchActivity.TAG, "reception exit");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Class nextActivity(String name) {
        if (name.equals(WaitOpponentActivity.TIME_ATTACK_MODE)) {
            return TimeAttackActivity.class;
        }

        if (name.equals(WaitOpponentActivity.COUNT_ATTACK_MODE)) {
            return CountAttackActivity.class;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.v(OpponentSearchActivity.TAG, "goalValue = " + goalValue);
        Log.v(OpponentSearchActivity.TAG, "gameMode = " + gameMode);

        Intent intent = new Intent(context, nextActivity(gameMode))
                .putExtra(BaseFragment.GOAL_VALUE, goalValue);
        context.startActivity(intent);
    }
}
