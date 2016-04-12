package edu.northwestern.cbits.purple_robot_manager;

import android.content.SharedPreferences;

/**
 * Created by stephen on 4/12/16.
 */
public class SensorManagement {



    public void enableProbes(SharedPreferences prefs) {

        // Accelerometer
        toggleProbe(prefs, "accelerometer_built_in", true);

        // Gyroscope
        toggleProbe(prefs, "gyroscope_built_in", true);

        // Light
        toggleProbe(prefs, "light_built_in", true);

        // Location
        toggleProbe(prefs, "built_in_location", true);

        // Magnetic Field
        toggleProbe(prefs, "magnetic_built_in", true);

        // Pressure
        toggleProbe(prefs, "pressure_built_in", true);

        // Proximity
        toggleProbe(prefs, "proximity_built_in", true);

        // Temperature
        toggleProbe(prefs, "temperature_built_in", true);

        // Audio features
        toggleProbe(prefs, "built_in_audio_features", true);

        // Accelerometer statistics
        toggleProbe(prefs, "features_accelerometer_statistics", true);

        // Gyroscope statistics
        toggleProbe(prefs, "features_gyroscope_statistics", true);

        // Magnetic field statistics
        toggleProbe(prefs, "features_magnetometer_statistics", true);

        // Light sensor statistics
        toggleProbe(prefs, "features_light_statistics", true);

        // Pressure sensor statistics
        toggleProbe(prefs, "features_pressure_statistics", true);

        // Proximity sensor statistics
        toggleProbe(prefs, "features_proximity_statistics", true);

        // Temperature sensor statistics
        toggleProbe(prefs, "features_temperature_statistics", true);

        // NFC
        toggleProbe(prefs, "config_probe_nfc_enabled", true);

        // Accelerometer Frequencies
        toggleProbe(prefs, "features_accelerometer_frequency", true);

        // Gravity
        toggleProbe(prefs, "gravity_built_in", true);

        // Step counter
        toggleProbe(prefs, "built_in_step_counter", true);

        // Linear Acceleration
        toggleProbe(prefs, "linear_acceleration_built_in", true);

        // Rotation Vector
        toggleProbe(prefs, "rotation_built_in", true);

        // Geomagnetic Rotation vector
        toggleProbe(prefs, "geomagnetic_rotation_built_in", true);

        // Ambient Humidity
        toggleProbe(prefs, "humidity_built_in", true);

        // Significant Motion
        toggleProbe(prefs, "built_in_significant_motion", true);

        // Fused Location
        toggleProbe(prefs, "built_in_fused_location", true);

        // Accelerometer Sensor
        toggleProbe(prefs, "accelerometer_sensor", true);

        // Light Sensor
        toggleProbe(prefs, "light_sensor", true);
    }

    private void toggleProbe(SharedPreferences prefs, String key, boolean isActive) {

        SharedPreferences.Editor e = prefs.edit();

        e.putBoolean("config_probe_" + key + "_enabled", isActive);

        e.commit();
    }
}
