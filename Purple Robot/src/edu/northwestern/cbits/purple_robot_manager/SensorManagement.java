package edu.northwestern.cbits.purple_robot_manager;

import android.content.SharedPreferences;

/**
 * Created by stephen on 4/12/16.
 */
public class SensorManagement {

    public static boolean probeEnabled = true;

    public static void manageProbes(SharedPreferences prefs) {

        /** Hardware sensors */
        // Accelerometer
        toggleProbe(prefs, "accelerometer_built_in", true);

        // Gyroscope
        toggleProbe(prefs, "gyroscope_built_in", false);

        // Light
        toggleProbe(prefs, "light_built_in", false);

        // Location
        toggleProbe(prefs, "built_in_location", false);

        // Magnetic Field
        toggleProbe(prefs, "magnetic_built_in", false);

        // Pressure
        toggleProbe(prefs, "pressure_built_in", false);

        // Proximity
        toggleProbe(prefs, "proximity_built_in", false);

        // Temperature
        toggleProbe(prefs, "temperature_built_in", false);

        // Audio features
        toggleProbe(prefs, "built_in_audio_features", false);

        // Accelerometer statistics
        toggleProbe(prefs, "features_accelerometer_statistics", false);

        // Gyroscope statistics
        toggleProbe(prefs, "features_gyroscope_statistics", false);

        // Magnetic field statistics
        toggleProbe(prefs, "features_magnetometer_statistics", false);

        // Light sensor statistics
        toggleProbe(prefs, "features_light_statistics", false);

        // Pressure sensor statistics
        toggleProbe(prefs, "features_pressure_statistics", false);

        // Proximity sensor statistics
        toggleProbe(prefs, "features_proximity_statistics", false);

        // Temperature sensor statistics
        toggleProbe(prefs, "features_temperature_statistics", false);

        // NFC
        toggleProbe(prefs, "config_probe_nfc_enabled", false);

        // Accelerometer Frequencies
        toggleProbe(prefs, "features_accelerometer_frequency", false);

        // Gravity
        toggleProbe(prefs, "gravity_built_in", false);

        // Step counter
        toggleProbe(prefs, "built_in_step_counter", false);

        // Linear Acceleration
        toggleProbe(prefs, "linear_acceleration_built_in", false);

        // Rotation Vector
        toggleProbe(prefs, "rotation_built_in", false);

        // Geomagnetic Rotation vector
        toggleProbe(prefs, "geomagnetic_rotation_built_in", false);

        // Ambient Humidity
        toggleProbe(prefs, "humidity_built_in", false);

        // Significant Motion
        toggleProbe(prefs, "built_in_significant_motion", false);

        // Fused Location
        toggleProbe(prefs, "built_in_fused_location", false);

        // Accelerometer Sensor
        toggleProbe(prefs, "accelerometer_sensor", false);

        // Light Sensor
        toggleProbe(prefs, "light_sensor", false);

        /** Device information and configuration */
        // Battery
        toggleProbe(prefs, "built_in_battery", false);

        // Current call state
        toggleProbe(prefs, "built_in_call_state", false);

        // H/W information
        toggleProbe(prefs, "built_in_hardware", false);

        // Network configuration
        toggleProbe(prefs, "built_in_network", false);

        // Purple Robot Health
        //toggleProbe(prefs, "built_in_network", true);

        // Running software
        toggleProbe(prefs, "built_in_running_software", false);

        // Screen Probe
        toggleProbe(prefs, "built_in_screen", false);

        // Software information
        toggleProbe(prefs, "built_in_software", false);

        // Telephone
        toggleProbe(prefs, "built_in_telephony", false);

        // Application Launch events
        toggleProbe(prefs, "built_in_application_launch", false);

        // Device in use
        toggleProbe(prefs, "features_device_use", false);

        // Wake loc information
        toggleProbe(prefs, "built_in_wakelock", false);

    }

    private static void toggleProbe(SharedPreferences prefs, String key, boolean isActive) {

        SharedPreferences.Editor e = prefs.edit();

        e.putBoolean("config_probe_" + key + "_enabled", isActive);

        e.commit();
    }
}
