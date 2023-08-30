package com.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.Callback;

public class WOLFXController implements Initializable{
    public TextField macAddressField;
    public TextField macAddressProfileNameField;
    public TextField broadcastIPField;
    public ListView<WOL> wolDeleteOptionsListView;
    public ListView<WOL> wolSelectionListView;
    public TextArea terminal;
    public Button addWOLButton;
    public Button deleteWOLButton;
    public Button activateAllDevicesButton;
    public Button WakeSelectedDevicesButton;

    ObservableList<WOL> wolProfiles;

    public void addWOL()
    {
        String macAddress = macAddressField.getText();
        String macAddressProfileName = macAddressProfileNameField.getText();
        String broadcastIP = broadcastIPField.getText();
        
        //TODO: Complete add WOL checks here
        terminal.appendText("Adding WOL! \n");

        wolProfiles.add(new WOL(broadcastIP, macAddress, macAddressProfileName));

        macAddressField.clear();
        macAddressProfileNameField.clear();
    }

    public void wakeSelectedDevices()
    {
        for (WOL wol : wolProfiles) {
            if(wol.isOn() == true)
            {
                try{
                    wol.sendWolPacket();
                }
                catch(IOException e)
                {
                    terminal.appendText(e.getMessage());
                }
            }
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wolSelectionListView.setCellFactory(CheckBoxListCell.forListView(new Callback<WOL,ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(WOL wol) {
                return wol.onProperty();
            }
        }));
        wolProfiles = wolSelectionListView.getItems();
        wolDeleteOptionsListView.setCellFactory(CheckBoxListCell.forListView(new Callback<WOL,ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(WOL wol) {
                return wol.deleteProperty();
            }
        }));
        wolDeleteOptionsListView.setItems(wolProfiles);
    }
}
