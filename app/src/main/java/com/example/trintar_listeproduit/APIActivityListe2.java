package com.example.trintar_listeproduit;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class APIActivityListe2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit);

        Button btnApiAll = (Button) findViewById(R.id.btn_api_all2);

        btnApiAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(APIActivityListe2.this);
                String url = "http://192.168.1.28:8082/webMarchandSymfony/public/produit/apiall";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                ListView lst;
                                lst = (ListView) findViewById(R.id.list_api_all2);
                                ArrayList<String> lesClients = new ArrayList<String>();
                                JSONArray jsonArray = null;
                                try {
                                    jsonArray = new JSONArray(response);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject item = jsonArray.getJSONObject(i);
                                        String nom = item.getString("libelle");
                                        lesClients.add(nom);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(APIActivityListe2.this, android.R.layout.simple_list_item_1, lesClients);
                                lst.setAdapter(arrayadapter);
                                lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        TextView tv = (TextView) view;
                                        Toast.makeText(APIActivityListe2.this, tv.getText() + "  " + position, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        TextView textApiAll = (TextView) findViewById(R.id.text_api_all2);
                        textApiAll.setText("Erreur, " + error.getMessage());
                    }
                });
                queue.add(stringRequest);
            }
        });
    }}
