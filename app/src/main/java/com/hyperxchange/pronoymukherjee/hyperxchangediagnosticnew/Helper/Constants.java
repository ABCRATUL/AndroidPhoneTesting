package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper;


import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Objects.Test;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static String DEVICE_NAME = "";
    public static String DEVICE_STORAGE = "";
    public static String DEVICE_RAM = "";
    public static String DEVICE_PRICE = "";
    public static String DEVICE_RAM_USAGE = "";
    public static String DEVICE_BATTERY_PERCENTAGE = "";
    public static String ENGINEER_EMAIL = "";
    public static String QUERY_URL = "http://ec2-35-154-219-101.ap-south-1.compute.amazonaws.com/dig";
    public static int PERMISSION_REQUEST_CODE = 69;
    public static String DIALOG_MSG = "dialogMsg";
    public static int DIALOG_INTERNET_CODE = 169;
    //Automated Test List.
    public static ArrayList<Test> automatedTestList = new ArrayList<>();
    public static ArrayList<Test> automatedTestListBackUp = new ArrayList<>();
    public static ArrayList<Test> successTestList = new ArrayList<>();
    public static ArrayList<Test> failedTestList = new ArrayList<>();
    /**
     * OS Name List.
     */
    public static Map<String, String> OS_NAMES = new HashMap<>();
    //Manual Test List.
    public static ArrayList<Test> manualTestList = new ArrayList<>();
    public static ArrayList<Test> manualTestListBackUP = new ArrayList<>();
    public static ArrayList<Test> successManualTestList = new ArrayList<>();
    public static ArrayList<Test> failedManualTestList = new ArrayList<>();
    public static String TEST_STATUS_DIALOG_KEY = "testStatus";
    public static String TEST_IS_MANUAL = "isManual";
    public static String TEST_STATUS_KEY = "statusTestKey";
    public static int TEST_TIMER = 15000;
    public static int VOICE_DELAY = 900;
    public static int MICROPHONE_SPEAKER_CODE = 566;
    public static String SPEAKER_VOLUME_MSG = "Please make sure that your media volume is audible.";
    public static int WRITE_SETTINGS_CODE = 99;
    public static int SAD_CODE = 3001;
    public static int HAPPY_CODE = 3002;
    public static int OKAY_CODE = 3003;
    public static int TOUCH_SCREEN_CODE = 1;
    public static int SPEAKER_CODE = 2;
    public static int VOLUME_BUTTON_UP_CODE = 3;
    public static int VOLUME_BUTTON_DOWN_CODE = 4;
    public static int PROXIMITY_CODE = 5;
    public static int REAR_CAMERA_CODE = 6;
    public static int FRONT_CAMERA_CODE = 7;
    public static int BACK_BUTTON_CODE = 8;
    public static int HOME_BUTTON_CODE = 9;
    public static int POWER_BUTTON_CODE = 10;
    public static int VIBRATION_CODE = 11;
    public static int CHARGER_CODE = 12;
    public static int HEADPHONE_CODE = 13;
    public static int RGB_CODE = 14;
    public static int MICROPHONE_CODE = 15;
    public static int SCREEN_BRIGHTNESS_CODE = 16;
    public static int FINGER_PRINT_CODE = 17;
    /**
     * Report Constants.
     */
    public static String HX_REPORT_FILE_NAME = "HXDiagnosticReport.xls";
    public static String HX_REPORT_SHEET = "DiagnosticReport";
    public static String HX_FOLDER_NAME = "HyperXchange";
    public static String HX_REPORT_FOLDER_NAME = "HxReport";
    public static String HX_CAMERA_FOLDER = "HxCamera";
    public static String HX_CAMERA_FILE_NAME = "Rear.jpg";
    public static String LEGAL_COMPANY_NAME = "YIBEAL TRADEX PVT. LTD. PUNE";
    public static String COMPANY_NAME = "HyperXchange";
    /**
     * JSON Constants
     */
    public static String JSON_TYPE = "type";
    public static String JSON_TABLE_NAME = "tableName";
    public static String REPORT_TABLE = "report_details";
    public static String JSON_TYPE_INSERT = "INSERT";
    public static String JSON_INSERT_VALUES = "values";
    public static String JSON_TYPE_SELECT = "SELECT";
    public static String JSON_TYPE_UPDATE = "UPDATE";
    public static String JSON_WHERE = "where";
    public static String JSON_UPDATE_COL = "col";
    public static String JSON_UPDATE_VALUE = "update";
    public static String JSON_RESULT = "res";
    public static String JSON_RESULT_STATUS = "status";
    public static String JSON_AFFECT_ROW = "affectedRows";
    /**
     * Column Constants
     */
    public static String IMEI_NUMBER = "imei";
    public static String REPORT_UUID_VALUE;
    public static String PHONE_MODEL_NAME = "phoneModelName";
    public static String PHONE_ID = "id";
    public static String PHONE_STORAGE = "storage";
    public static String PHONE_RAM = "ram";
    public static String PRICE_PHONE_ID = "phoneId";
    public static String PHONE_PRICE = "price";
    public static String LOGIN_PASSCODE = "passcode";
    public static String LOGIN_EMAIL = "email";
    public static String PHONE_TABLE = "phone_details";
    public static String PHONE_MODEL_TABLE = "buy_back_phone";
    public static String PHONE_PRICE_TABLE = "buy_back_phone_price";
    public static String LOGIN_PIN_TABLE = "login_pin";
    private static String TAG_CLASS = Constants.class.getSimpleName();

    /**
     * This is the method to fill the automated test list.
     */
    public static void fillAutomatedTestList() {
        automatedTestList.add(new Test("Ram", 0, R.drawable.ic_ram));
        automatedTestList.add(new Test("Battery", 0, R.drawable.ic_battery));
        automatedTestList.add(new Test("Wifi", 0, R.drawable.ic_wifi));
        automatedTestList.add(new Test("Bluetooth", 0, R.drawable.ic_bluetooth));
        automatedTestList.add(new Test("NFC", 0, R.drawable.ic_nfc));
        automatedTestList.add(new Test("Flash", 0, R.drawable.ic_flash));
        automatedTestList.add(new Test("Accelerometer", 0, R.drawable.ic_accelerometer));
        automatedTestList.add(new Test("Gyroscope", 0, R.drawable.ic_gyroscope));
        automatedTestList.add(new Test("External Storage", 0, R.drawable.ic_external_storage));
        // Keeping a copy of Automated List.
        automatedTestListBackUp = new ArrayList<>(Constants.automatedTestList);
    }

    /**
     * This is the method to fill the manual test list.
     */
    public static void fillManualTestList() {
        manualTestList.add(new Test("Touch Screen", 0, R.drawable.ic_touch_screen));
        manualTestList.add(new Test("Speaker", 0, R.drawable.ic_speaker));
        manualTestList.add(new Test("Volume Up", 0, R.drawable.ic_volume_up));
        manualTestList.add(new Test("Volume Down", 0, R.drawable.ic_volume_down));
        manualTestList.add(new Test("Proximity", 0, R.drawable.ic_proximity));
        manualTestList.add(new Test("Rear Camera", 0, R.drawable.ic_back_camera));
        manualTestList.add(new Test("Front Camera", 0, R.drawable.ic_front_camera));
        manualTestList.add(new Test("Back Button", 0, R.drawable.ic_back_button));
        manualTestList.add(new Test("Home Button", 0, R.drawable._ic_home));
        manualTestList.add(new Test("Power Button", 0, R.drawable.ic_power_button));
        manualTestList.add(new Test("Vibration", 0, R.drawable.ic_vibration_test));
        manualTestList.add(new Test("Charger", 0, R.drawable.ic_charger));
        manualTestList.add(new Test("Headphone", 0, R.drawable.ic_headphone_jack));
        manualTestList.add(new Test("RGB", 0, R.drawable.ic_rgb));
        manualTestList.add(new Test("MicroPhone", 0, R.drawable.ic_microphone));
        manualTestList.add(new Test("Brightness", 0, R.drawable.ic_screen_brightness));
        manualTestList.add(new Test("Fingerprint", 0, R.drawable.ic_finger_print));
        //Keeping a copy of Manual Test List.
        manualTestListBackUP = new ArrayList<>(Constants.manualTestList);
    }

    /**
     * Method to fill the Android OS Names.
     */
    public static void fillOSNames() {
        OS_NAMES.put("2.3", "GingerBread");
        OS_NAMES.put("3.0", "HoneyComb");
        OS_NAMES.put("4.0", "IceCream SandWich");
        OS_NAMES.put("4.1", "JellyBean");
        OS_NAMES.put("4.2", "JellyBean");
        OS_NAMES.put("4.3", "JellyBean");
        OS_NAMES.put("4.4", "KitKat");
        OS_NAMES.put("5.0", "Lollipop");
        OS_NAMES.put("5.1", "Lollipop");
        OS_NAMES.put("6.0", "Marshmallow");
        OS_NAMES.put("7.0", "Nougat");
        OS_NAMES.put("7.1", "Nougat");
        OS_NAMES.put("8.0", "Oreo");
        OS_NAMES.put("8.1", "Oreo");
        OS_NAMES.put("9.0", "Android P");
    }
}


