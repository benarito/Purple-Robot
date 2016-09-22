package edu.northwestern.cbits.purple_robot_manager;

import android.content.SharedPreferences;

/**
 * Created by stephen on 4/12/16.
 */
public class SensorManagement {

    public static void manageProbes(SharedPreferences prefs) {

        toggleProbe(prefs, "accelerometer_built_in", true);

        toggleProbe(prefs, "gyroscope_built_in", true);

        toggleProbe(prefs, "light_built_in", true);

        toggleProbe(prefs, "built_in_location", true);

        toggleProbe(prefs, "magnetic_built_in", true);

        toggleProbe(prefs, "linear_acceleration_built_in", true);

        toggleProbe(prefs, "accelerometer_sensor", true);

        toggleProbe(prefs, "light_sensor", true);

        toggleProbe(prefs, "built_in_call_state", true);

        toggleProbe(prefs, "built_in_telephony", true);

        toggleProbe(prefs, "built_in_screen", true);

        toggleProbe(prefs, "built_in_software", true);

        toggleProbe(prefs, "features_device_use", true);
    }

    private static void toggleProbe(SharedPreferences prefs, String key, boolean isActive) {

        SharedPreferences.Editor e = prefs.edit();

        e.putBoolean("config_probe_" + key + "_enabled", isActive);

        e.commit();
    }
}
