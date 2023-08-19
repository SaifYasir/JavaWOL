package com.javafx;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class WOLFXController {
    public TextField macAddressField;
    public TextField macAddressProfileNameField;
    public TextField broadcastIPField;
    public ComboBox<String> wolOptionsComboBox;
    public ListView<CheckBox> WOLSelectionListView;
    public TextArea terminal;

    public Button addWOLButton;
    public Button deleteWOLButton;
    public Button activateAllDevicesButton;

    public void addWOL()
    {
        //Put checks here
        terminal.appendText("Adding WOL!");
    }
}
