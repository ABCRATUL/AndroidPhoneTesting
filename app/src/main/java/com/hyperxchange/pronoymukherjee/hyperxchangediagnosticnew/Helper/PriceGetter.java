package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class PriceGetter implements HTTPConnector.ResponseListener {
    private Context context;
    private String TAG_CLASS = PriceGetter.class.getSimpleName();
    private boolean isPhoneID = false;
    private int id;
    private HTTPConnector connector;

    public PriceGetter(Context context) {
        this.context = context;
    }

    /**
     * Method to get the Phone ID, which will in-turn get the Price.
     */
    public void getPhoneId() {
        String deviceName = Constants.DEVICE_NAME;
        connector = new HTTPConnector(TAG_CLASS, context, Constants.QUERY_URL,
                ParamsCreator.createParamsForPhoneID(deviceName), this);
        connector.makeQuery(TAG_CLASS);
        isPhoneID = true;
    }

    /**
     * Internal method to get the Phone Price After the ID.
     */
    private void getPhonePrice() {
        int ram = Integer.parseInt(Constants.DEVICE_RAM);
        int storage = Integer.parseInt(Constants.DEVICE_STORAGE);
        connector = new HTTPConnector(TAG_CLASS, context, Constants.QUERY_URL,
                ParamsCreator.createParamsForPhonePrice(id, ram, storage), this);
        connector.makeQuery(TAG_CLASS);
    }

    @Override
    public void onResponse(JSONObject response) {
        if (isPhoneID) {
            try {
                JSONObject result = response.getJSONArray(Constants.JSON_RESULT).getJSONObject(0);
                id = result.getInt(Constants.PHONE_ID);
                getPhonePrice();
                isPhoneID = false;
                Message.logMessage(TAG_CLASS, "PHONE ID: " + id);
            } catch (JSONException e) {
                Message.logMessage(TAG_CLASS, e.toString());
                isPhoneID = false;
            }
        } else {
            try {
                JSONObject result = response.getJSONArray(Constants.JSON_RESULT).getJSONObject(0);
                Constants.DEVICE_PRICE = String.valueOf(result.getInt(Constants.PHONE_PRICE));
                Message.logMessage(TAG_CLASS, "PHONE PRICE: " + Constants.DEVICE_PRICE);
            } catch (JSONException e) {
                Message.logMessage(TAG_CLASS, e.toString());
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Message.logMessage(TAG_CLASS, error.toString());
    }
}
