package com.example.magisterka2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText plec, wiek, tygodnie;
    Button predict;
    TextView result, result2, result3;
    String url = "https://magisterskajr.herokuapp.com/predict";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        plec = findViewById(R.id.plec);
        wiek = findViewById(R.id.wiek);
        tygodnie = findViewById(R.id.tygodnie);
        predict = findViewById(R.id.predict);
        result = findViewById(R.id.result);
        result2 = findViewById(R.id.result2);
        result3 = findViewById(R.id.result3);
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener() {
                            @Override
                            public void onResponse(Object response) {
                                try {
                                    JSONObject jsonObject = new JSONObject((String) response);
                                    String data1 = jsonObject.getString("outcome");
                                    String data2 = jsonObject.getString("outcome2");
                                    String data3 = jsonObject.getString("outcome3");
                                    result.setText(data1);
                                    result2.setText(data2);
                                    result3.setText(data3);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map getParams(){
                        Map params = new HashMap();
                        params.put("plec",plec.getText().toString());
                        params.put("wiek",wiek.getText().toString());
                        params.put("tygodnie",tygodnie.getText().toString());
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(stringRequest);

            }
        });

    }
}