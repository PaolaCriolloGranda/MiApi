package com.example.miapi;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView ListView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> datos= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView=findViewById(R.id.ListView);
        arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,datos);
        ListView.setAdapter(arrayAdapter);
        obtenerDatos();
    }
private void obtenerDatos(){
        String url="https://jsonplaceholder.typicode.com/todos";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//recibe inf
                //pasar a Json
                try {
                    pasarJson(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            //maneja el error
                Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    Volley.newRequestQueue(this).add(jsonArrayRequest);
}
    private void pasarJson( JSONArray array) throws JSONException {
        for(int i=0;i<array.length();i++){
            Publicacion pub= new Publicacion();
            JSONObject json=null;

            json=array.getJSONObject(i);
            pub.setId(json.getInt("id"));

            pub.setTitle(json.getString("title"));

            datos.add(pub.getTitle());//Agrego la informacion que voy a acargar en la lista.


        }
        arrayAdapter.notifyDataSetChanged();

    }
}