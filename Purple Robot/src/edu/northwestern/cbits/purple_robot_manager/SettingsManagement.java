package edu.northwestern.cbits.purple_robot_manager;

import android.content.Context;
import android.content.SharedPreferences;

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
        e.putBoolean("config_restrict_data_charging", false);

        e.putBoolean("config_streaming_jackson_enable_buffer", true); // enable
        e.commit();

    }
}
