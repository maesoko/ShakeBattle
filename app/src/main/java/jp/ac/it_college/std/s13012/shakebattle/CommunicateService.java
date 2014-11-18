package jp.ac.it_college.std.s13012.shakebattle;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

public class CommunicateService extends IntentService{

    public CommunicateService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Context context = getApplicationContext();
    }
}
