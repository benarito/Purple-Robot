package edu.northwestern.cbits.purple_robot_manager.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import edu.northwestern.cbits.purple_robot_manager.R;

public class UserRegistration extends Activity {

    public static String PREF_USER_REG = "check_user_registration";

    private SharedPreferences prefs = null;

    Button btnSubmit;

    EditText txtName, txtAge, txtLocation;

    Spinner gender;

    private SharedPreferences getPreferences(Context context)
    {
        if (this.prefs == null)
            this.prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());

        return this.prefs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        final SharedPreferences sharedPrefs = this.getPreferences(this);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPrefs.edit();

                editor.putBoolean(PREF_USER_REG, true);

                editor.commit();

                Intent i = new Intent(UserRegistration.this, StartActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
    }
}
