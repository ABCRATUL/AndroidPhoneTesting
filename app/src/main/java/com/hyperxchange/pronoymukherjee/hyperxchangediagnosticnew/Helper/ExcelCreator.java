package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper;

import android.content.Context;
import android.os.Environment;

import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Objects.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jxl.Workbook;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * This is the class to make the excel files for the test Report.
 */
public class ExcelCreator {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean createExcel(Context context) {
        DeviceInformation deviceInformation = new DeviceInformation(context);
        File path = new File(Environment.getExternalStorageDirectory() + File.separator +
                Constants.HX_FOLDER_NAME + File.separator + Constants.HX_REPORT_FOLDER_NAME);
        if (!path.exists())
            path.mkdirs();
        try {

            WritableWorkbook workbook = Workbook
                    .createWorkbook(new File(path, Constants.HX_REPORT_FILE_NAME));
            WritableSheet sheet = workbook.createSheet(Constants.HX_REPORT_SHEET, 0);
            WritableFont font15 = new WritableFont(WritableFont.ARIAL, 20, WritableFont.BOLD);
            WritableCellFormat cellFormat = new WritableCellFormat(font15);

            //Adding the Company Name.
            Label label = new Label(3, 2, Constants.LEGAL_COMPANY_NAME, cellFormat);
            sheet.addCell(label);
            WritableFont font17 = new WritableFont(WritableFont.ARIAL, 17, WritableFont.BOLD, true);
            cellFormat = new WritableCellFormat(font17);
            Label logoLabel = new Label(7, 4, Constants.COMPANY_NAME, cellFormat);
            sheet.addCell(logoLabel);
            logoLabel = new Label(2, 4, "Hardware Report", cellFormat);
            sheet.addCell(logoLabel);
            font17 = new WritableFont(WritableFont.TIMES, 15, WritableFont.BOLD);
            WritableCellFormat cellFormatMidHeading = new WritableCellFormat(font17);
            Label midHeadingLabel = new Label(2, 6, "Hardware Details",
                    cellFormatMidHeading);
            sheet.addCell(midHeadingLabel);
            WritableFont font13 = new WritableFont(WritableFont.TIMES, 13);
            cellFormat = new WritableCellFormat(font13);

            //Adding the Manufacturer.

            Label headingLabel = new Label(2, 7, "Manufacturer:", cellFormat);
            sheet.addCell(headingLabel);
            Label valueLabel = new Label(3, 7, deviceInformation.getDeviceManufacturer(),
                    cellFormat);
            sheet.addCell(valueLabel);

            //Adding the Model Name.
            headingLabel = new Label(2, 8, "Model:", cellFormat);
            valueLabel = new Label(3, 8, deviceInformation.getDeviceModelName(), cellFormat);
            sheet.addCell(headingLabel);
            sheet.addCell(valueLabel);

            //adding Serial Number
            headingLabel = new Label(2, 9, "Serial:", cellFormat);
            valueLabel = new Label(3, 9, deviceInformation.getSerialNumber(), cellFormat);
            sheet.addCell(headingLabel);
            sheet.addCell(valueLabel);

            //Adding the IMEI Number.
            headingLabel = new Label(2, 10, "IMEI:", cellFormat);
            valueLabel = new Label(3, 10, deviceInformation.getIMEINumber(true),
                    cellFormat);
            sheet.addCell(headingLabel);
            sheet.addCell(valueLabel);

            //Adding the BSSID.
            headingLabel = new Label(2, 11, "Wifi BSSID:", cellFormat);
            valueLabel = new Label(3, 11, deviceInformation.getBSSID(), cellFormat);
            sheet.addCell(headingLabel);
            sheet.addCell(valueLabel);

            //Adding the Region.
            headingLabel = new Label(2, 12, "Region:", cellFormat);
            valueLabel = new Label(3, 12, deviceInformation.getRegion(), cellFormat);
            sheet.addCell(headingLabel);
            sheet.addCell(valueLabel);

            //Adding the UUID.
            headingLabel = new Label(2, 13, "UUID:", cellFormat);
            valueLabel = new Label(3, 13, deviceInformation.getUUID(), cellFormat);
            sheet.addCell(headingLabel);
            sheet.addCell(valueLabel);

            //Adding the Battery Information Heading.
            midHeadingLabel = new Label(2, 15, "Battery Information", cellFormatMidHeading);
            sheet.addCell(midHeadingLabel);

            double actualCapacity = deviceInformation.getBatteryActualCapacity();
            double wearCapacity = 0.0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                wearCapacity = deviceInformation.getBatteryWearCapacity();
            }
            //Adding the Battery Actual Capacity.

            headingLabel = new Label(2, 17, "Actual Capacity:", cellFormat);
            valueLabel = new Label(3, 17, String.valueOf(actualCapacity) + " mAh", cellFormat);
            sheet.addCell(headingLabel);
            sheet.addCell(valueLabel);

            //Adding the Battery Wear Capacity.
            headingLabel = new Label(2, 18, "Current Capacity", cellFormat);
            valueLabel = new Label(3, 18, String.valueOf(wearCapacity) + " mAh", cellFormat);
            sheet.addCell(headingLabel);
            sheet.addCell(valueLabel);

            //Adding the Wear Level Percentage.
            double wearLevel = 100 - ((wearCapacity / actualCapacity) * 100);
            headingLabel = new Label(2, 19, "Wear Level:", cellFormat);
            valueLabel = new Label(3, 19, String.valueOf(Math.floor(wearLevel)) + "%",
                    cellFormat);
            sheet.addCell(headingLabel);
            sheet.addCell(valueLabel);

            //Adding the Software Information Heading.
            midHeadingLabel = new Label(2, 21, "Software Information", cellFormatMidHeading);
            sheet.addCell(midHeadingLabel);

            //Adding the OS Version Code.

            headingLabel = new Label(2, 23, "OS Version", cellFormat);
            valueLabel = new Label(3, 23, deviceInformation.getOSVersion(), cellFormat);
            sheet.addCell(headingLabel);
            sheet.addCell(valueLabel);

            //Adding the API Level.
            headingLabel = new Label(2, 24, "API Level:", cellFormat);
            valueLabel = new Label(3, 24, deviceInformation.getAPILevel(), cellFormat);
            sheet.addCell(headingLabel);
            sheet.addCell(valueLabel);

            //Adding the OS Name.
            headingLabel = new Label(2, 25, "OS Name:", cellFormat);
            valueLabel = new Label(3, 25,
                    deviceInformation.getOSName(deviceInformation.getOSVersion()), cellFormat);
            sheet.addCell(headingLabel);
            sheet.addCell(valueLabel);

            //Adding the HardWare check heading.
            midHeadingLabel = new Label(2, 27, "Hardware Check", cellFormatMidHeading);
            sheet.addCell(midHeadingLabel);
            int col = 2, row = 29;
            //Adding the Automated List.
            for (int i = 0; i < Constants.automatedTestListBackUp.size(); i++) {
                headingLabel = new Label(col, row,
                        Constants.automatedTestListBackUp.get(i).getTestName(), cellFormat);
                sheet.addCell(headingLabel);
                if (isPresent(Constants.automatedTestListBackUp.get(i), false)) {
                    valueLabel = new Label(3, row, "Successful", cellFormat);
                    sheet.addCell(valueLabel);
                } else {
                    valueLabel = new Label(3, row, "Unsuccessful", cellFormat);
                    sheet.addCell(valueLabel);
                }
                row++;
            }
            row = 29;
            //Adding the Manual Tests.
            for (int i = 0; i < Constants.manualTestListBackUP.size(); i++) {
                headingLabel = new Label(5, row,
                        Constants.manualTestListBackUP.get(i).getTestName(), cellFormat);
                sheet.addCell(headingLabel);
                if (isPresent(Constants.manualTestListBackUP.get(i), true)) {
                    valueLabel = new Label(6, row, "Successful", cellFormat);
                } else {
                    valueLabel = new Label(6, row, "Unsuccessful", cellFormat);
                }
                sheet.addCell(valueLabel);
                row++;
            }
            //Adding the overall test.
            row += 2;
            headingLabel = new Label(2, row, "Overall:", cellFormat);
            sheet.addCell(headingLabel);
            if (Constants.failedManualTestList.size() >= 0 || Constants.failedTestList.size() == 0) {
                valueLabel = new Label(3, row, "Unsuccessful", cellFormat);
            } else
                valueLabel = new Label(3, row, "Successful", cellFormat);
            sheet.addCell(valueLabel);

            //Report Details.
            row += 2;
            midHeadingLabel = new Label(2, row, "Report Details", cellFormatMidHeading);
            sheet.addCell(midHeadingLabel);
            row += 2;
            //Adding the Report UUID
            headingLabel = new Label(2, row, "Report UUID:", cellFormat);
            Constants.REPORT_UUID_VALUE = deviceInformation.getUUID() + Calendar
                    .getInstance().getTimeInMillis();
            valueLabel = new Label(3, row, Constants.REPORT_UUID_VALUE, cellFormat);
            sheet.addCell(headingLabel);
            sheet.addCell(valueLabel);

            //Adding the Current Date.
            row++;
            Date currentDate = Calendar.getInstance().getTime();
            DateFormat format = new DateFormat("dd MMM yyyy hh:mm:ss");
            WritableCellFormat dateFormat = new WritableCellFormat(format);
            headingLabel = new Label(2, row, "Report Date:", cellFormat);
            DateTime dateTime = new DateTime(3, row, currentDate, dateFormat);
            sheet.addCell(headingLabel);
            sheet.addCell(dateTime);
            deviceInformation = null;

            workbook.write();
            workbook.close();
            return true;
        } catch (IOException | WriteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This is the method to check whether the test is successful or not.
     *
     * @param test:     The Test Object which is to be checked.
     * @param isManual: if the Test is of Manual or not.
     * @return true: if its successful else false.
     */
    public static boolean isPresent(Test test, boolean isManual) {
        if (!isManual) {
            for (int i = 0; i < Constants.successTestList.size(); i++) {
                if (Constants.successTestList.get(i).getTestName().equals(test.getTestName()))
                    return true;
            }
            for (int i = 0; i < Constants.failedTestList.size(); i++) {
                if (Constants.failedTestList.get(i).getTestName().equals(test.getTestName()))
                    return false;
            }
        } else {
            for (int i = 0; i < Constants.successManualTestList.size(); i++) {
                if (Constants.successManualTestList.get(i).getTestName().equals(test.getTestName()))
                    return true;
            }
            for (int i = 0; i < Constants.failedManualTestList.size(); i++) {
                if (Constants.failedManualTestList.get(i).getTestName().equals(test.getTestName()))
                    return false;
            }
            return false;
        }
        return false;
    }
}