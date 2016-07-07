package proveb.gk.com.sqlite;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import proveb.gk.com.sqlite.adapter.Customadapter;
import proveb.gk.com.sqlite.modelclass.Jsonmodel;

/**
 * Created by Nehru on 02-07-2016.
 */
public class JsonActivity extends Activity {
    private TextView doctor;
    private ListView doctor_list;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayList<Jsonmodel> jsonmodelArrayList;
    private Customadapter customadapter;
    public DBHelper dbHelper;
    private boolean isOffline = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsonmain);
        doctor = (TextView) findViewById(R.id.tv_doctor);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        doctor_list = (ListView) findViewById(R.id.lv_doctor_list);
        dbHelper = new DBHelper(JsonActivity.this);

        if (isOffline)
            getdoctorlist();
        else
            getList();

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text [" + s + "]");

                customadapter.getFilter().filter(s.toString().trim());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void getdoctorlist() {

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        final String url = "http://www.elitedoctorsonline.com/api_mob/browser/search/search.aspx?cou_id=211&lang=en";
        asyncHttpClient.get(url, null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.e("success", "method called");
                try {

                    dbHelper.DBCOUNT();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("doctor_data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        if (dbHelper.insertDoctorDetails(object.getString("doc_id"), object.getString("fullname"), object.getString("doc_image"))) {
                            Log.e("success", "done" + i);
//                            Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("success", "not done" + i);
//                            Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                        }
                    }
                    dbHelper.DBCOUNT();

                    getList();

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    public void getList() {

        jsonmodelArrayList = dbHelper.getAllDoctor();
        if(jsonmodelArrayList.size()==0){
            getdoctorlist();
        }else {
            customadapter = new Customadapter(JsonActivity.this, jsonmodelArrayList);
            doctor_list.setAdapter(customadapter);
        }

    }
}
