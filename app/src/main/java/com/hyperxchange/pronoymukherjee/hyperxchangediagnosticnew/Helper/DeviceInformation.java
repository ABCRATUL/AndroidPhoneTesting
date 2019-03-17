package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;

import java.util.Map;


public class DeviceInformation {
    private Context context;
    private String TAG_CLASS = DeviceInformation.class.getSimpleName();

    public DeviceInformation(Context context) {
        this.context = context;
    }

    /**
     * Method to get the Device Manufacturer.
     *
     * @return Device Manufacturer.
     */
    public String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * Get the Device Model Name.
     *
     * @return model Name.
     */
    public String getDeviceModelName() {
        return Build.MODEL;
    }

    /**
     * Method to get the Serial Number.
     *
     * @return Serial Number.
     */
    @SuppressLint("HardwareIds")
    public String getSerialNumber() {
        return Build.SERIAL;
    }

    /**
     * Method to get the IMEI NUMBER.
     *
     * @param isPermitted: true if all the permission is granted.
     * @return The IMEI Number if permission is granted.
     */
    @SuppressLint("HardwareIds")
    public String getIMEINumber(boolean isPermitted) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (isPermitted) {
            assert telephonyManager != null;
            @SuppressLint("MissingPermission")
            String imeiNumber = telephonyManager.getDeviceId();
            return imeiNumber;
        }
        return "N/A";
    }

    /**
     * Method to get the BSSID (equivalent of MAC Address)
     *
     * @return The String BSSID.
     */
    public String getBSSID() {
        WifiManager wifiManager = (WifiManager) context
                .getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert wifiManager != null;
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getBSSID();
    }

    /**
     * Method to get the Region of the Device.
     *
     * @return The Country Code.
     */
    public String getRegion() {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        assert telephonyManager != null;
        String region = telephonyManager.getSimCountryIso().toUpperCase();
        if (region.equals("")) {
            return "NA";
        }
        return region;
    }

    /**
     * Method to get the UUID Of the device.
     *
     * @return The String UUID of the Device.
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public String getUUID() {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        assert telephonyManager != null;
        return telephonyManager.getDeviceId();
    }

    @SuppressLint("PrivateApi")
    public double getBatteryActualCapacity() {
        Object powerProfile;
        double batteryCapacity;
        String POWER = "com.android.internal.os.PowerProfile";
        try {
            powerProfile = Class.forName(POWER)
                    .getConstructor(Context.class)
                    .newInstance(context.getApplicationContext());
            batteryCapacity = (double) Class
                    .forName(POWER)
                    .getMethod("getBatteryCapacity")
                    .invoke(powerProfile);
        } catch (Exception e) {
            Message.logMessage(TAG_CLASS, e.toString());
            return 0.0;
        }
        return batteryCapacity;
    }

    /**
     * Method to get the Wear level new Battery.
     *
     * @return The Wear Battery capacity.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public double getBatteryWearCapacity() {
        BatteryManager batteryManager = (BatteryManager) context
                .getSystemService(Context.BATTERY_SERVICE);
        assert batteryManager != null;
        return Math.floor(batteryManager
                .getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER) * 0.001);
    }

    /**
     * Method to get the OS Version. Android Version.
     *
     * @return OS Version.
     */
    public String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * Method to get the API Level.
     *
     * @return API Level.
     */
    public String getAPILevel() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    /**
     * Method to get the OS Name.
     *
     * @param osVersion: The OS Version Code.
     * @return OSName: The OS Name matching the OS Version code.
     */
    public String getOSName(String osVersion) {
        for (Map.Entry<String, String> item : Constants.OS_NAMES.entrySet()) {
            if (osVersion.startsWith(item.getKey())) {
                return item.getValue();
            }
        }
        return null;
    }
}
