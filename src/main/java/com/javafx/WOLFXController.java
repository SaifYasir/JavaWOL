package com.javafx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.FileChooser;
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
    public Button wakeAllDevicesButton;
    public Button WakeSelectedDevicesButton;

    public Label selectedFileLabel;
    public MenuItem loadFileMenuItem;
    public MenuItem saveFileMenuItem;
    public MenuItem exportFileMenuItem;

    ObservableList<WOL> wolProfiles;
    String LOAD_FILE_PATH = this.getClass().getResource("/wol_profiles.txt").getPath(); //.getPath()+"/loadFileLocation.txt";
    String CURRENT_LOADED_FILE;

    public void addWOL()
    {
        String macAddress = macAddressField.getText();
        String macAddressProfileName = macAddressProfileNameField.getText();
        String broadcastIP = broadcastIPField.getText();
        
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

    public void wakeAllDevices()
    {
        for (WOL wol : wolProfiles) {
            try {
                wol.sendWolPacket();
            } catch (IOException e) {
                terminal.appendText(e.getMessage());
            }
        }
    }

    public void saveFile()
    {
        AlertBox warning = new AlertBox();
        try{
            warning.display("Are you sure you want to save this file to the current file loaded (overwrites any previous data)?", "save file");
        }
        catch(IOException e){
            terminal.appendText("Error saving file: " + e.getMessage() + "\n");
            return;
        }

        if(!warning.getReturnBoolean()){
            terminal.appendText("Cancelled saving file! \n");
            return;
        }

        try (FileWriter writer = new FileWriter(new File(CURRENT_LOADED_FILE),false)) {
            for (WOL wol : wolProfiles) {
                writer.write(wol.getMacAddress() + ";" + wol.getMacAddressProfileName() + ";" + wol.getBroadcastIP());
                writer.write(System.lineSeparator());
            }
            writer.flush();
            terminal.appendText("File saved! \n");
        } catch (Exception e) {
            terminal.appendText("Error saving file " + e.getMessage() + "\n");
        }
    }

    public void exportFile()
    {
        //TODO: Actually export the files
    }

    public void loadFile()
    {
        FileChooser chooseFile = new FileChooser();

        //Gets the window from a given node within the scene
        File fileToLoad = chooseFile.showOpenDialog(macAddressField.getScene().getWindow());

        if(fileToLoad == null)
        {
            terminal.appendText("No file was selected to load!" + "\n");
            return;
        }

        try(FileWriter fileWriter = new FileWriter(LOAD_FILE_PATH,false)){
            fileWriter.write(fileToLoad.getAbsolutePath());
            fileWriter.flush();
        } catch(IOException e)
        {
            terminal.appendText(e.getMessage() + "\n");
        }

        loadCSVFileContents(fileToLoad);
    }

    public void deleteSelectedDevices()
    {
        try{
            AlertBox warning = new AlertBox();
            warning.display("Are you sure you want to delete these WOL presets?", "Delete WOL profiles");
            if(!warning.getReturnBoolean()){
                terminal.appendText("Cancelled removing WOL profiles!" + "\n");
                return;
            }
        }
        catch(IOException e)
        {
            terminal.appendText("Failure removing WOL profile: " + e.getMessage() + "\n");
            return;
        }

        for(Iterator<WOL> iterator = wolProfiles.iterator(); iterator.hasNext();)
        {
            try {
                WOL nextWOL = iterator.next();
                if(nextWOL.isDelete())
                {
                    iterator.remove();
                    terminal.appendText("Deleted WOL profile: " + nextWOL.getMacAddressProfileName() + "\n");
                }
            } catch (Exception e) {
                terminal.appendText("Failure removing WOL profile: " + e.getMessage() + "\n");
            }
        }
    }

    public void loadCSVFileContents(File fileToLoad)
    {
        if(wolProfiles.size() > 0)
        {
            try{
                AlertBox warning = new AlertBox();
                warning.display("Are you sure you want to Load a new file?", "load new file");
                if(!warning.getReturnBoolean()){
                    terminal.appendText("Cancelled loading WOL profiles!" + "\n");
                    return;
                }
            }
            catch(IOException e)
            {
                terminal.appendText("Failure loading WOL profile: " + e.getMessage() + "\n");
            } 
        }

        wolProfiles.clear();

        CSVReader csvReader;
        terminal.appendText("Attempting to load File: " + fileToLoad.getName() + "\n");
        try {
            csvReader = new CSVReaderBuilder(new FileReader(fileToLoad)).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).build();
        } catch (FileNotFoundException e) {
            terminal.appendText("Could not load file: " + e.getMessage() + "\n");
            return;
        }

        selectedFileLabel.setText("Selected File: " + fileToLoad.getName());
        CURRENT_LOADED_FILE = fileToLoad.getAbsolutePath();

        String[] nextLine;

        try {
            while((nextLine = csvReader.readNext()) != null)
            {
                try {
                    wolProfiles.add(new WOL(nextLine[2], nextLine[0], nextLine[1]));
                } catch (IndexOutOfBoundsException e) {
                    terminal.appendText("Could not add WOL profile, incorrect line with properties: ");
                    for (String property : nextLine) {
                        terminal.appendText(property);
                    }
                    terminal.appendText("\n");
                }
            }
            terminal.appendText("Loaded File " + fileToLoad.getName() + "\n");
        } catch (CsvValidationException | IOException e) {
            terminal.appendText("error occurred: " + e.getMessage());
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

        try(Scanner scanner = new Scanner(new File(LOAD_FILE_PATH))){
            String fileLocation = scanner.nextLine();
            File fileToLoad = new File(fileLocation);
            loadCSVFileContents(fileToLoad);
        } catch (FileNotFoundException e) {
            terminal.appendText("Could not load previous file on startup: " + e.getMessage() + "\n");
        }
    }
}
