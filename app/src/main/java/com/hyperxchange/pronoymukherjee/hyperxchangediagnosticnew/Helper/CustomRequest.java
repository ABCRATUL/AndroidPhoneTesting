package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class CustomRequest extends Request<JSONObject> {
    private String TAG_CLASS = CustomRequest.class.getSimpleName();
    private Response.Listener<JSONObject> listener;
    private Map<String, String> params;

    public CustomRequest(String queryUrl, Map<String, String> params, Response.Listener<JSONObject> responseListener,
                         Response.ErrorListener errorListener) {
        super(Method.POST, queryUrl, errorListener);
        this.listener = responseListener;
        this.params = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (JSONException | UnsupportedEncodingException e) {
            Message.logMessage(TAG_CLASS, e.toString());
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }
}
