package com.teddybear.actividad12;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import  java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ListView lv_clientes;
    private ArrayAdapter adapter;
    private String getAllClientesURL = "http://130.100.4.39:8080/api_clientes?user_hash=12345&action=get";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        lv_clientes = (ListView)findViewById(R.id.lv_client_list);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        lv_clientes.setAdapter(adapter);


        webServiceRest(getAllClientesURL);
    }

    private void webServiceRest(String requestURL){
        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult = "";
            while((line = bufferedReader.readLine())!=null){
                webServiceResult += line;
            }
            parseInformation(webServiceResult);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String id_cliente;
        String nombre_c;
        String apepat_c;
        String apemat_c;
        String telefono_c;
        String email;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            e.printStackTrace();
        }

        for(int i=0; i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_cliente = jsonObject.getString("id_cliente");
                nombre_c = jsonObject.getString("nombre_c");
                apepat_c = jsonObject.getString("apepat_c");
                apemat_c = jsonObject.getString("apemat_c");
                telefono_c = jsonObject.getString("telefono_c");
                email = jsonObject.getString("email");
                adapter.add(id_cliente + "\n " + nombre_c + " " + apepat_c + " " +
                        apemat_c + "\n " + email + "\n " + telefono_c);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}
