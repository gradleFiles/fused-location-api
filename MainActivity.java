package proveb.gk.com.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity {
    private TextView register;
    private EditText name, pass;
    private Button login;
    DBHelper db;
    ArrayList<String> stringArrayList;
    LoginButton loginButton;
    TextView tv_profile_name;
    ImageView iv_profile_pic;
    CallbackManager callbackManager;
    LoginResult loginResult;
    AccessTokenTracker accessTokenTracker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.et_loginname);
        pass = (EditText) findViewById(R.id.et_loginpass);
        login = (Button) findViewById(R.id.btn_login);
        register = (TextView) findViewById(R.id.tv_register);
        tv_profile_name = (TextView) findViewById(R.id.tv_profile_name);
        iv_profile_pic = (ImageView) findViewById(R.id.iv_profile_pic);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        db = new DBHelper(this);
        stringArrayList = new ArrayList<>();
        ArrayList array_list = db.getAllregister();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringArrayList = db.getAllregister();
                String username_and_password = name.getText().toString() + "," + pass.getText().toString();
                String name_and_pass = name.getText().toString().length() + "," + pass.getText().toString();
//                    Log.e("value entered", username_and_password);
                if(name_and_pass.equals("")) {
                    for (int i = 0; i < stringArrayList.size(); i++) {
                        if (username_and_password.equals(stringArrayList.get(i).toString())) {
                            Intent intent = new Intent(MainActivity.this, DrawerActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Please Enter values", Toast.LENGTH_SHORT).show();

                }

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {


                                try {
                                    String email = object.getString("email");
                                    Log.e("email",email);
                                    String birthday = object.getString("birthday");
                                    String id = object.getString("id");
                                    Log.e("id=", id);
                                    String name = object.getString("name");
                                    Log.e("name",name);
                                    tv_profile_name.setText(name);
                                    String imageurl = "https://graph.facebook.com/" + id + "/picture?type=large";
                                    Picasso.with(MainActivity.this).load(imageurl).into(iv_profile_pic);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();


/**
 * AccessTokenTracker to manage logout
 */
                accessTokenTracker = new AccessTokenTracker() {
                    @Override
                    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                               AccessToken currentAccessToken) {
                        if (currentAccessToken == null) {
                            tv_profile_name.setText("");
//                            iv_profile_pic.setImageResource(R.drawable.maleicon);
                        }
                    }
                };
            }


            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
}




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
