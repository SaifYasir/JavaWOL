package com.javafx;

import javafx.scene.control.cell.CheckBoxListCell;

public class WOL {
    String broadcastIP;
    String macAddress;
    String macAddressProfileName;

    CheckBoxListCell<String> profileCheckBox;

    public WOL(String broadcastIP, String macAddress, String macAddressProfileName, CheckBoxListCell<String> profileCheckBox)
    {
        this.broadcastIP = broadcastIP;
        this.macAddress = macAddress;
        this.macAddressProfileName = macAddressProfileName;
        this.profileCheckBox = profileCheckBox;
    }

    public String getBroadcastIP() {
        return broadcastIP;
    }
    public void setBroadcastIP(String broadcastIP) {
        this.broadcastIP = broadcastIP;
    }
    public String getMacAddress() {
        return macAddress;
    }
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
    public String getMacAddressProfileName() {
        return macAddressProfileName;
    }
    public void setMacAddressProfileName(String macAddressProfileName) {
        this.macAddressProfileName = macAddressProfileName;
    }

    public CheckBoxListCell<String> getProfileCheckBox() {
        return profileCheckBox;
    }

    public void setProfileCheckBox(CheckBoxListCell<String> profileCheckBox) {
        this.profileCheckBox = profileCheckBox;
    }

}
