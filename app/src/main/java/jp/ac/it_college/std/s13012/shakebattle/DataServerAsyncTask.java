package jp.ac.it_college.std.s13012.shakebattle;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class DataServerAsyncTask extends AsyncTask<Void, Void, String> {

    private TextView textView;

    public DataServerAsyncTask(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            ServerSocket serverSocket = new ServerSocket(DataTransferService.EXTRAS_PORT_NUMBER);
            Log.v(OpponentSearchActivity.TAG, "Server: Socket opened");
            Socket client = serverSocket.accept();
            Log.v(OpponentSearchActivity.TAG, "Server: connection done");

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));

            String result = br.readLine();
            br.close();

            serverSocket.close();
            return result;
        } catch (IOException e) {
            Log.e(OpponentSearchActivity.TAG, e.getMessage());
            return "error";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        textView.setText(result);
        Log.v(WaitOpponentActivity.TAG, "onPostExecute:" + textView.getText());
    }
}
