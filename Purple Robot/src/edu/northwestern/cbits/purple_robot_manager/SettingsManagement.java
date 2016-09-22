package edu.northwestern.cbits.purple_robot_manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

/**
 * Created by stephen on 4/15/16.
 */
public class SettingsManagement {

    public static void manageSettings(Context ctx, SharedPreferences prefs) {

        // enable probes
        SharedPreferences.Editor e = prefs.edit();
        e.putBoolean("config_probes_enabled", true);
        e.putBoolean("config_enable_streaming_jackson_data_server", true);
        e.putString("config_streaming_jackson_upload_interval", ctx.getString(R.string.value_3600));
        e.putBoolean("config_mute_warnings", true);
        e.putBoolean("config_enable_log_server", true);
        e.putBoolean("config_restrict_log_wifi", false);

        e.putBoolean("config_streaming_jackson_enable_buffer", true); // enable
        e.commit();

    }
}
