package edu.northwestern.cbits.purple_robot_manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import edu.northwestern.cbits.purple_robot_manager.config.LegacyJSONConfigFile;
import edu.northwestern.cbits.purple_robot_manager.logging.LogManager;
import edu.northwestern.cbits.purple_robot_manager.scripting.BaseScriptEngine;
import edu.northwestern.cbits.purple_robot_manager.scripting.JavaScriptEngine;
import edu.northwestern.cbits.purple_robot_manager.triggers.TriggerManager;

public class WidgetReceiver extends BroadcastReceiver
{
    public void onReceive(Context context, Intent intent)
    {

        String action = intent.getStringExtra("widget_action");

        String script = null;

        if ("tap".equals(action))
            script = intent.getStringExtra("action");
        else if ("tap_one".equals(action))
            script = intent.getStringExtra("action_one");
        else if ("tap_two".equals(action))
            script = intent.getStringExtra("action_two");
        else if ("tap_three".equals(action))
            script = intent.getStringExtra("action_three");
        else if ("tap_four".equals(action))
            script = intent.getStringExtra("action_four");
        else if ("tap_five".equals(action))
            script = intent.getStringExtra("action_five");

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("widget_action", action);
        LogManager.getInstance(context).log("pr_widget_tapped", payload);

        if (script != null)
        {
            try
            {
                BaseScriptEngine.runScript(context, script);
            }
            catch (Exception e)
            {
                LogManager.getInstance(context).logException(e);
            }
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        Log.i("Status", "Broadcast send from " + intent.getAction());

        if ("edu.northwestern.cbits.purple.WIDGET_ACTION".equals(intent.getAction()))
        {
            Log.i("Status:", "Boot action detected");
            long now = System.currentTimeMillis();

            SharedPreferences.Editor e = prefs.edit();

            e.putLong(BootUpReceiver.BOOT_KEY, now);

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
