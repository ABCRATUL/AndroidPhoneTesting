package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Objects;

public class Phone {
    int storage;
    private String manufacturer, model, serial, imei, bssid, region, uuid, actualCapacity;

    public Phone(String manufacturer, String model, String serial, String imei, String bssid, String region, String uuid, int storage, String actualCapacity) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.serial = serial;
        this.imei = imei;
        this.bssid = bssid;
        this.region = region;
        this.uuid = uuid;
        this.storage = storage;
        this.actualCapacity = actualCapacity;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public String getActualCapacity() {
        return actualCapacity;
    }

    public void setActualCapacity(String actualCapacity) {
        this.actualCapacity = actualCapacity;
    }
}
