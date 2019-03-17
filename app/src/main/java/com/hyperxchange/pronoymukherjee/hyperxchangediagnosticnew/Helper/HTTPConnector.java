package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Objects.SingleTon;

import org.json.JSONObject;

import java.util.Map;

public class HTTPConnector {
    private String TAG_CLASS = HTTPConnector.class.getSimpleName();
    private Context context;
    private String queryURL;
    private Map<String, String> params;
    private ResponseListener responseListener;

    public HTTPConnector(String TAG_CLASS, Context context, String queryURL, Map<String, String> params, ResponseListener responseListener) {
        this.TAG_CLASS = TAG_CLASS;
        this.context = context;
        this.queryURL = queryURL;
        this.params = params;
        this.responseListener = responseListener;
    }

    /**
     * Method to make the HTTP Query.
     *
     * @param TAG: The Tag for the Request.
     */
    public void makeQuery(String TAG) {
        CustomRequest customRequest = new CustomRequest(queryURL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (responseListener != null) {
                    responseListener.onResponse(response);
                }
                Message.logMessage(TAG_CLASS, "response");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (responseListener != null) {
                    responseListener.onErrorResponse(error);
                }
                Message.logMessage(TAG_CLASS, error.toString());
            }
        });
        customRequest.setTag(TAG);
        SingleTon.getInstance(context.getApplicationContext()).addToRequestQueue(customRequest);
    }

    public interface ResponseListener {
        void onResponse(JSONObject response);

        void onErrorResponse(VolleyError error);
    }
}
