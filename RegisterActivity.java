package proveb.gk.com.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class RegisterActivity extends Activity {
    private DBHelper db;
    private EditText name, password;
    private Button submit, nowLogin;
    ArrayList<String> stringArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        name = (EditText) findViewById(R.id.et_registername);
        password = (EditText) findViewById(R.id.et_registerpass);
        submit = (Button) findViewById(R.id.btn_submit);
        nowLogin = (Button) findViewById(R.id.btn_nowLogin);
        db = new DBHelper(this);
        submit.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          stringArrayList = db.getAllregister();
                                          String name_and_password = name.getText().toString() + "," + password.getText().toString();
                                          if (stringArrayList.size() != 0) {
                                              for (int i = 0; i < stringArrayList.size(); i++) {

                                                  if (!name_and_password.equals(stringArrayList.get(i).toString())) {
                                                      if (!name.getText().toString().equals(""))
                                                          if (!password.getText().toString().equals(""))
                                                              run();
                                                          else
                                                              Toast.makeText(RegisterActivity.this, "Enter your password", Toast.LENGTH_LONG).show();
                                                      else
                                                          Toast.makeText(RegisterActivity.this, "Enter your name", Toast.LENGTH_LONG).show();
                                                  } else {
                                                      Toast.makeText(RegisterActivity.this, "Already exist", Toast.LENGTH_SHORT).show();
                                                  }
                                              }
                                          }
                                          else
                                          {
                                              if (!name.getText().toString().equals(""))
                                                  if (!password.getText().toString().equals(""))
                                                      run();
                                                  else
                                                      Toast.makeText(RegisterActivity.this, "Enter your password", Toast.LENGTH_LONG).show();
                                              else
                                                  Toast.makeText(RegisterActivity.this, "Enter your name", Toast.LENGTH_LONG).show();

                                          }

                                      }
                                  }

        );
        nowLogin.setOnClickListener(new View.OnClickListener()

                                    {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }

        );
    }

    public void run() {
        if (db.insertRegister(name.getText().toString(), password.getText().toString())) {
            stringArrayList = db.getAllregister();
            for (int i = 0; i < stringArrayList.size(); i++) {
                Toast.makeText(getApplicationContext(), "done" + stringArrayList.get(i).toString(), Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplication(), "not done", Toast.LENGTH_LONG).show();
        }

    }


}
