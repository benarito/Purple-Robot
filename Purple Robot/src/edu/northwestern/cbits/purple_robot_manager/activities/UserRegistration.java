package edu.northwestern.cbits.purple_robot_manager.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import edu.northwestern.cbits.purple_robot_manager.EncryptionManager;
import edu.northwestern.cbits.purple_robot_manager.R;

public class UserRegistration extends Activity {

    public static String PREF_USER_REG = "check_user_registration";

    private SharedPreferences prefs = null;

    Button btnSubmit;

    EditText txtName, txtAge, txtLocation, txtNationalID;

    Spinner spGender;

    ProgressDialog dialog;

    String name, age, location, nationalId, gender;

    private SharedPreferences getPreferences(Context context)
    {
        if (this.prefs == null)
            this.prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());

        return this.prefs;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        dialog = new ProgressDialog(this);

        txtName = (EditText) findViewById(R.id.txtName);
        txtAge = (EditText) findViewById(R.id.txtAge);
        txtLocation = (EditText) findViewById(R.id.txtLocation);
        spGender = (Spinner) findViewById(R.id.spGender);
        txtNationalID = (EditText) findViewById(R.id.txtNationalID);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate()) {
                    new SaveUserInformation().execute();
                }
            }
        });
    }

    /**
     * Validate user input
     */
    private boolean validate() {

        name = txtName.getText().toString().toString().trim();
        age = txtAge.getText().toString().trim();
        location = txtLocation.getText().toString().trim();
        gender = (String) spGender.getItemAtPosition(spGender.getSelectedItemPosition());
        nationalId = txtNationalID.getText().toString().trim();

        if(name.equals("") || age.equals("") || location.equals("") || nationalId.equals(""))  {

            txtName.setError(name.equals("") ? getString(R.string.error_required) : null);
            txtAge.setError(age.equals("") ? getString(R.string.error_required) : null);
            txtLocation.setError(location.equals("") ? getString(R.string.error_required) : null);
            txtNationalID.setError(nationalId.equals("") ? getString(R.string.error_required) : null);

            return false;

        } else {

            return true;

        }
    }

    private class SaveUserInformation extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setIndeterminate(true);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage(getString(R.string.saving_info));
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            save(name, age, location, gender, nationalId);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();
        }



        /**
         * Save validated information to Server
         * @param name
         * @param age
         * @param location
         * @param gender
         * @param nationalId
         */
        private void save(String name, String age, String location, String gender, String nationalId) {

            JSONObject params = new JSONObject();
            try {
                params.put("Name", name);
                params.put("Age", age);
                params.put("Location", location);
                params.put("Gender", gender);
                params.put("National_ID", nationalId);
                params.put("hashedUser", EncryptionManager.getInstance().getUserHash(UserRegistration.this));
                params.put("user", EncryptionManager.getInstance().getUserId(UserRegistration.this));
            } catch(JSONException e) {
                e.printStackTrace();
            }

            try {

                // http client
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpEntity httpEntity = null;
                HttpResponse httpResponse = null;

                HttpPost httpPost = new HttpPost(getString(R.string.user_registration_url));
                httpPost.setHeader("Content-Type", "application/json");
                httpPost.setEntity(new ByteArrayEntity(params.toString().getBytes("UTF-8")));

                httpResponse = httpClient.execute(httpPost);

                httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);

                JSONObject obj = new JSONObject(response);
                String status = obj.getString("status");
                final String message = obj.getString("message");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UserRegistration.this, message, Toast.LENGTH_LONG).show();
                    }
                });


                if(status.equals("ok")) {
                    SharedPreferences.Editor editor = UserRegistration.this.getPreferences(UserRegistration.this).edit();
                    editor.putBoolean(PREF_USER_REG, true);
                    editor.commit();
                    Intent i = new Intent(UserRegistration.this, StartActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UserRegistration.this, getString(R.string.error_connection_timed_out), Toast.LENGTH_LONG)
                                .show();
                    }
                });

            } catch(JSONException e) {
                e.printStackTrace();
            }

            dialog.dismiss();
        }
    }
}
