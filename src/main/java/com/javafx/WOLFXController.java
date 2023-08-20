package com.javafx;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;

public class WOLFXController {
    public TextField macAddressField;
    public TextField macAddressProfileNameField;
    public TextField broadcastIPField;
    public ComboBox<String> wolOptionsComboBox;
    public ListView<CheckBox> wolSelectionListView;
    public TextArea terminal;

    public Button addWOLButton;
    public Button deleteWOLButton;
    public Button activateAllDevicesButton;

    public void addWOL()
    {
        String macAddress = macAddressField.getText();
        String macAddressProfileName = macAddressProfileNameField.getText();
        String broadcastIP = broadcastIPField.getText();
        
        //TODO: Complete add WOL checks here
        terminal.appendText("Adding WOL! \n");

        WOL wolProfile = new WOL(broadcastIP, macAddress, macAddressProfileName, new CheckBoxListCell<>(null));
    }
}
