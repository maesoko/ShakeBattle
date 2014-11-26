package jp.ac.it_college.std.s13012.shakebattle;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class DataServerAsyncTask extends AsyncTask<Void, Void, String[]> {

    private Context context;

    public DataServerAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String[] doInBackground(Void... voids) {
        try {
            ServerSocket serverSocket = new ServerSocket(DataTransferService.EXTRAS_PORT_NUMBER);
            Log.v(OpponentSearchActivity.TAG, "Server: Socket opened");
            Socket client = serverSocket.accept();
            Log.v(OpponentSearchActivity.TAG, "Server: connection done");

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));

            String[] result = new String[2];
            result[0] = br.readLine();
            result[1] = br.readLine();
            br.close();
            client.close();
            serverSocket.close();

            Log.v(OpponentSearchActivity.TAG, "reception exit");
            return result;
        } catch (IOException e) {
            Log.e(OpponentSearchActivity.TAG, e.getMessage());
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
    protected void onPostExecute(String[] result) {
        Log.v(OpponentSearchActivity.TAG, "result[0] = " + result[0]);
        Log.v(OpponentSearchActivity.TAG, "result[1] = " + result[1]);
        /*Intent intent = new Intent(context, nextActivity(result[0]))
                .putExtra(BaseFragment.GOAL_VALUE, result[1]);
        context.startActivity(intent);*/
    }
}
