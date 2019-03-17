package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Objects;

public class Report {
    private String imei, reportUuid, reportDate;

    public Report(String imei, String reportUuid, String reportDate) {
        this.imei = imei;
        this.reportUuid = reportUuid;
        this.reportDate = reportDate;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getReportUuid() {
        return reportUuid;
    }

    public void setReportUuid(String reportUuid) {
        this.reportUuid = reportUuid;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }
}
