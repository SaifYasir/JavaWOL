package com.javafx;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WOL {
    String broadcastIP;
    String macAddress;
    String macAddressProfileName;

    StringProperty name;
    BooleanProperty on;

    public WOL(String broadcastIP, String macAddress, String macAddressProfileName)
    {
        this.name = new SimpleStringProperty();
        this.on = new SimpleBooleanProperty();

        this.broadcastIP = broadcastIP;
        this.macAddress = macAddress;
        this.macAddressProfileName = macAddressProfileName;
        setName(macAddressProfileName);
        setOn(false);

        this.onProperty().addListener((obs, wasOn, isOn) -> {
            System.out.println(this.getName() + " changed on state from " + wasOn + " to " + isOn);
        });
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

    public BooleanProperty onProperty() {
        return this.on;
    }

    public boolean isOn() {
        return this.onProperty().get();
    }

    public void setOn(boolean on) {
        onProperty().set(on);
    }

    public StringProperty nameProperty(){
        return this.name;
    }

    public void setName(String name){
        this.nameProperty().set(name);
    }

    public String getName(){
        return this.nameProperty().get();
    }
    @Override
    public String toString(){
        return getName();
    }

}
