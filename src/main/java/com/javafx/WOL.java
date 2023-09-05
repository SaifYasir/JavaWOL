package com.javafx;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HexFormat;

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
    BooleanProperty delete;

    public WOL(String broadcastIP, String macAddress, String macAddressProfileName)
    {
        this.name = new SimpleStringProperty();
        this.on = new SimpleBooleanProperty();
        this.delete = new SimpleBooleanProperty();

        this.broadcastIP = broadcastIP;
        this.macAddress = macAddress;
        this.macAddressProfileName = macAddressProfileName;
        setName(macAddressProfileName);
        setOn(false);
        setDelete(false);
    }

    public void sendWolPacket() throws IOException
    {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName(broadcastIP);       

        //Create WOL packet
        byte[] wolPacket = new byte[102];
        for(int i = 0; i < 6; i++)
        {
            wolPacket[i] = (byte)0xff;
        }
        byte[] repeatedMacAddress = HexFormat.ofDelimiter(":").parseHex(this.macAddress);
        for(int i = 6; i < wolPacket.length; i+=repeatedMacAddress.length)
        {
            System.arraycopy(repeatedMacAddress, 0, wolPacket, i,repeatedMacAddress.length);
        }
        DatagramPacket packet = new DatagramPacket(wolPacket,wolPacket.length, address,9);
        socket.send(packet);
        socket.close();
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

    public BooleanProperty deleteProperty() {
        return this.delete;
    }

    public boolean isDelete() {
        return this.deleteProperty().get();
    }

    public void setDelete(boolean bool) {
        deleteProperty().set(bool);
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
