package cherish.com.coolweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cherish.com.coolweather.service.AutoUpdateService;

public class AutoUpdateReceicer extends BroadcastReceiver {
    public AutoUpdateReceicer() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent i = new Intent(context, AutoUpdateService.class);
        context.startService(i);
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
