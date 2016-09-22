package edu.northwestern.cbits.purple_robot_manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import edu.northwestern.cbits.purple_robot_manager.config.LegacyJSONConfigFile;
import edu.northwestern.cbits.purple_robot_manager.logging.LogManager;
import edu.northwestern.cbits.purple_robot_manager.scripting.BaseScriptEngine;
import edu.northwestern.cbits.purple_robot_manager.scripting.JavaScriptEngine;
import edu.northwestern.cbits.purple_robot_manager.triggers.TriggerManager;

public class NetworkChangeReceiver extends BroadcastReceiver
{

    public void onReceive(Context context, Intent intent)
    {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        boolean hasBooted = prefs.getBoolean(BootUpReceiver.BOOT_STATUS, false);

        if(hasBooted) return;

        Log.i("Status", "Broadcast send from " + intent.getAction());

        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction()))
        {
            long now = System.currentTimeMillis();

            Editor e = prefs.edit();

            e.putLong(BootUpReceiver.BOOT_KEY, now);
            e.putBoolean(BootUpReceiver.BOOT_STATUS, true);

            e.commit();

            LegacyJSONConfigFile.getSharedFile(context.getApplicationContext());

            TriggerManager.getInstance(context).fireMissedTriggers(context, now);

            if (prefs.contains(BaseScriptEngine.STICKY_NOTIFICATION_PARAMS)) {
                try {
                    JSONObject json = new JSONObject(prefs.getString(BaseScriptEngine.STICKY_NOTIFICATION_PARAMS, "{}"));

                    JavaScriptEngine engine = new JavaScriptEngine(context);

                    engine.showScriptNotification(json.getString("title"), json.getString("message"),
                            json.getBoolean("persistent"), json.getBoolean("sticky"), json.getString("script"));
                } catch (JSONException ex) {
                    LogManager.getInstance(context).logException(ex);
                }
            }
        }

        ManagerService.setupPeriodicCheck(context);
    }
}
