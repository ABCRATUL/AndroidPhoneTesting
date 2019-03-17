package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper;

import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Objects.Phone;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Objects.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ParamsCreator {
    private static String TAG_CLASS = ParamsCreator.class.getSimpleName();

    /**
     * Method to Create Params for Insert of Phones.
     *
     * @param phone: The Phone Object which is to be inserted.
     * @return params: For the insertion of the Phone.
     */
    public static Map<String, String> createParamsForInsertPhone(Phone phone) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put(Constants.JSON_TYPE, Constants.JSON_TYPE_INSERT);
            params.put(Constants.JSON_TABLE_NAME, Constants.PHONE_TABLE);
            String values = "'" + phone.getManufacturer() + "','" +
                    phone.getModel() + "','" +
                    phone.getSerial() + "','" +
                    phone.getImei() + "','" +
                    phone.getBssid() + "','" +
                    phone.getRegion() + "','" +
                    phone.getUuid() + "','" +
                    phone.getStorage() + "','" +
                    phone.getActualCapacity() + "','" +
                    "5','0'";
            params.put(Constants.JSON_INSERT_VALUES, values);
        } catch (Exception e) {
            Message.logMessage(TAG_CLASS, e.toString());
        }
        return params;
    }

    /**
     * This the method to create the Params for selecting the Phones.
     *
     * @param imei: The IMEI Number of the Phone to be selected.
     * @return params: The Params which contains the key and the value.
     */
    public static Map<String, String> createParamsForSelectPhone(String imei) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put(Constants.JSON_TYPE, Constants.JSON_TYPE_SELECT);
            params.put(Constants.JSON_TABLE_NAME, Constants.PHONE_TABLE);
            String where = Constants.IMEI_NUMBER + " LIKE '" + imei + "'";
            params.put(Constants.JSON_WHERE, where);
            Message.logMessage(TAG_CLASS, params.toString());
        } catch (Exception e) {
            Message.logMessage(TAG_CLASS, e.toString());
            return params;
        }
        return params;
    }

    /**
     * This is the method to create the Params for inserting the report.
     *
     * @param deviceInformation: The Device information from which the details of the phone are fetched.
     * @return params: For inserting the Report.
     */
    public static Map<String, String> createParamsForInsertReport(DeviceInformation deviceInformation) {
        Map<String, String> params = new HashMap<>();
        boolean overall = true;
        try {
            params.put(Constants.JSON_TYPE, Constants.JSON_TYPE_INSERT);
            params.put(Constants.JSON_TABLE_NAME, Constants.REPORT_TABLE);
            StringBuilder values = new StringBuilder();
            values.append("'").append(deviceInformation.getIMEINumber(true)).append("',");
            //Adding the Automated Test.
            for (Test test : Constants.automatedTestListBackUp) {
                if (ExcelCreator.isPresent(test, false)) {
                    values.append("'yes',");
                } else {
                    values.append("'no',");
                    overall = false;
                }
            }
            for (Test test : Constants.manualTestListBackUP) {
                if (ExcelCreator.isPresent(test, true))
                    values.append("'yes',");
                else {
                    values.append("'no',");
                    overall = false;
                }
            }
            if (overall)
                values.append("'successful',");
            else values.append("'unsuccessful',");

            values.append("'").append(Constants.REPORT_UUID_VALUE).append("',");
            String time = String.valueOf(Calendar.getInstance().getTimeInMillis());
            values.append("'").append(time).append("',");
            values.append("'").append(Constants.ENGINEER_EMAIL).append("'");
            params.put(Constants.JSON_INSERT_VALUES, values.toString());
            Message.logMessage(TAG_CLASS, params.toString());
        } catch (Exception e) {
            Message.logMessage(TAG_CLASS, e.toString());
            return params;
        }
        return params;
    }

    /**
     * Method to get the Params for the Phone Price.
     *
     * @return params: The Params to get the Phone Price.
     */
    public static Map<String, String> createParamsForPhoneID(String deviceName) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put(Constants.JSON_TYPE, Constants.JSON_TYPE_SELECT);
            params.put(Constants.JSON_TABLE_NAME, Constants.PHONE_MODEL_TABLE);
            String where = Constants.PHONE_MODEL_NAME + " LIKE '"
                    + deviceName + "'";
            params.put(Constants.JSON_WHERE, where);
            Message.logMessage(TAG_CLASS, params.toString());
        } catch (Exception e) {
            Message.logMessage(TAG_CLASS, e.toString());
        }
        return params;
    }

    /**
     * Method to create the params for the Phone Price.
     *
     * @param id:      The Phone ID.
     * @param ram:     The Ram of the Phone.
     * @param storage: The Storage of the Device.
     * @return params: Params with the following conditions.
     */
    public static Map<String, String> createParamsForPhonePrice(int id, int ram, int storage) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put(Constants.JSON_TYPE, Constants.JSON_TYPE_SELECT);
            params.put(Constants.JSON_TABLE_NAME, Constants.PHONE_PRICE_TABLE);
            String where = Constants.PRICE_PHONE_ID + " = " + id + " AND " +
                    Constants.PHONE_RAM + " = " + ram + " AND " +
                    Constants.PHONE_STORAGE + " = " + storage;
            params.put(Constants.JSON_WHERE, where);
        } catch (Exception e) {
            Message.logMessage(TAG_CLASS, e.toString());
        }
        return params;
    }

    /**
     * Method to create the params for selecting the Pin.
     *
     * @param pin: The Passcode entered by the engineer.
     * @return params: Params with the passcode select.
     */
    public static Map<String, String> createParamsForLoginPin(String pin) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put(Constants.JSON_TYPE, Constants.JSON_TYPE_SELECT);
            params.put(Constants.JSON_TABLE_NAME, Constants.LOGIN_PIN_TABLE);
            String where = Constants.LOGIN_PASSCODE + " LIKE '" + pin + "'";
            params.put(Constants.JSON_WHERE, where);
        } catch (Exception e) {
            Message.logMessage(TAG_CLASS, e.toString());
        }
        return params;
    }
}
