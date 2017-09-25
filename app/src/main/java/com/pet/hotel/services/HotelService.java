package com.pet.hotel.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.pet.hotel.activities.MainActivity;
import com.pet.hotel.dados.Hotel;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HotelService extends Service {
    public HotelService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private List<Hotel> getHoteisTeste() {
        List<Hotel> ret = new ArrayList<>();
        for (Hotel h : MainActivity.getHoteis()) {
            if (h.interesse)
                ret.add(h);
        }
        return ret;
    }

    private String verificarDisponibilidade(){
        List<Hotel> interessados = getHoteisTeste();

        ArrayList<String> str_ids = new ArrayList<>();

        for (Hotel h: interessados){
            str_ids.add(String.valueOf(h.getId()));
        }


        String str = "";
        String json = new Gson().toJson(str_ids);

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://maisuem.pe.hu/hotel.php");

        try {
            // Adicionar os hoteis como parâmetro
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("hoteis", json));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            byte[] result = EntityUtils.toByteArray(response.getEntity());


            //Str é um JSON de resposta
            str = new String(result, "UTF-8");
            Log.d("LogService", str);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return str;
    }
}
