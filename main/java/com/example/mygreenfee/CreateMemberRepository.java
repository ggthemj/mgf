package com.example.mygreenfee;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CreateMemberRepository {
    //L'activité parente, appelée pour déclencher certaines méthodes selon les retours des WS
    CreateMemberActivity context;

    UserData userData ;

    //Les paramètres de la requête http
    Map<String, String> mHeaders;
    Map<String, String> mParams;

    public CreateMemberRepository(CreateMemberActivity c){
        this.context = c ;
    }

    public void update(final String civilite, final String nom, final String prenom, final String email, final String dob, final String mdp, final String country, final int region_id, final String phone){
        Log.d("DEBUG", "Debut de la requete de création de compte");

        //Préparation de la requête
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = context.getResources().getString(R.string.URL_createMember);
        mHeaders = new HashMap<String, String>();
        mHeaders.put("X-API-KEY", context.getResources().getString(R.string.API_KEY));
        mHeaders.put("CONTENT-LANGUAGE", context.getResources().getString(R.string.CONTENT_LANGUAGE));
        mParams = new HashMap<String, String>();
        mParams.put("data[title]", civilite);
        mParams.put("data[fname]", nom);
        mParams.put("data[lname]", prenom);
        mParams.put("data[dob]", dob);
        mParams.put("data[email]", email);
        mParams.put("data[pwd]", mdp);
        mParams.put("data[country]", country);
        mParams.put("data[region_id]", ""+region_id);
        mParams.put("data[phone]", phone);
        mParams.put("data[auto_activate]", "1");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("DEBUG", "response : "+response);

                    /**
                    try {
                        clubsData = new ClubsData(new JSONObject(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                     **/

                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("DEBUG","That didn't work! "+error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                return mParams;
            }
            public Map<String, String> getHeaders() {
                return mHeaders;
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError){
                if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
                    VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                    volleyError = error;
                }

                return volleyError;
            }
        };
        queue.add(stringRequest);
    }


}
