package edu.northwestern.cbits.purple_robot_manager.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import edu.northwestern.cbits.purple_robot_manager.EncryptionManager;
import edu.northwestern.cbits.purple_robot_manager.R;

public class UserRegistration extends Activity {

    public static String PREF_USER_REG = "check_user_registration";

    private SharedPreferences prefs = null;

    Button btnSubmit;

    EditText txtName, txtAge, txtLocation;

    Spinner spGender;

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


        txtName = (EditText) findViewById(R.id.txtName);
        txtAge = (EditText) findViewById(R.id.txtAge);
        txtLocation = (EditText) findViewById(R.id.txtAge);
        spGender = (Spinner) findViewById(R.id.spGender);

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

    /**
     * Validate user input
     */
    private void validate() {

        String name = txtName.getText().toString().toString().trim();
        String age = txtAge.getText().toString().trim();
        String location = txtLocation.getText().toString().trim();
        String gender = (String) spGender.getItemAtPosition(spGender.getSelectedItemPosition());

        if(name.equals("") || age.equals("") || location.equals(""))  {

            txtName.setError(name.equals("") ? getString(R.string.error_required) : null);
            txtAge.setError(age.equals("") ? getString(R.string.error_required) : null);
            txtLocation.setError(location.equals("") ? getString(R.string.error_required) : null);

        } else {
            // Proceed with saving information to Server
            save(name, age, location, gender);
        }
    }

    /**
     * Save validated information to Server
     * @param name
     * @param age
     * @param location
     * @param gender
     */
    private void save(String name, String age, String location, String gender) {

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.saving_info));
        dialog.show();


        String response = "";

        // Build params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("age", age));
        params.add(new BasicNameValuePair("location", location));
        params.add(new BasicNameValuePair("gender", gender));
        params.add(new BasicNameValuePair("username", EncryptionManager.getInstance().getUserId(this)));
        params.add(new BasicNameValuePair("hashedUsername", EncryptionManager.getInstance().getUserHash(this)));
        
        try {

            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            HttpPost httpPost = new HttpPost(getString(R.string.user_registration_url));
            // adding post params
            if (params != null) {
                httpPost.setEntity(new UrlEncodedFormEntity(params));
            }

            httpResponse = httpClient.execute(httpPost);

            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);

            Toast.makeText(this, response, Toast.LENGTH_LONG)
                    .show();


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.error_connection_timed_out), Toast.LENGTH_LONG)
                    .show();
        }

        dialog.dismiss();
    }
}
