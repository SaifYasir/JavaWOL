package com.javafx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
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
    public Button activateAllDevicesButton;
    public Button WakeSelectedDevicesButton;

    public Label selectedFileLabel;
    public MenuItem loadFileMenuItem;

    ObservableList<WOL> wolProfiles;
    String LOAD_FILE_PATH = this.getClass().getResource("/").getPath()+"/loadFileLocation.txt";

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

    public void loadFile()
    {
        FileChooser chooseFile = new FileChooser();

        //Gets the window from a given node within the scene
        File fileToLoad = chooseFile.showOpenDialog(macAddressField.getScene().getWindow());

        try(FileWriter fileWriter = new FileWriter(LOAD_FILE_PATH,false)){
            fileWriter.write(fileToLoad.getAbsolutePath());
            fileWriter.flush();
        } catch(IOException e)
        {
            //TODO: Print out correct IO Exception message
        }

        loadCSVFileContents(fileToLoad);

        // try(FileWriter fileWriter = new FileWriter(LOAD_FILE_PATH,false)){
        //     fileWriter.write(chosenFile.getAbsolutePath());
        //     fileWriter.flush();
        //     selectedFileLabel.setText("Selected File: " + chosenFile.getName());

        //     CSVReader csvReader = new CSVReaderBuilder(new FileReader(chosenFile)).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).build();
        //     String[] nextLine;
        //     while((nextLine = csvReader.readNext()) != null)
        //     {
        //         wolProfiles.add(new WOL(nextLine[2], nextLine[0], nextLine[1]));
        //     }
        // }
        // catch(IOException e)
        // {
        //     // TODO: handle not loading file exception
        //     System.out.println(e);
        // }
        // catch(CsvValidationException e)
        // {
        //     System.out.println(e);
        //     //TODO: sort out csv validation exception
        // }
    }

    public void loadCSVFileContents(File fileToLoad)
    {
        CSVReader csvReader;

        try {
            csvReader = new CSVReaderBuilder(new FileReader(fileToLoad)).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).build();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }

        String[] nextLine;
        selectedFileLabel.setText("Selected File: " + fileToLoad.getName());

        try {
            while((nextLine = csvReader.readNext()) != null)
            {
                try {
                    wolProfiles.add(new WOL(nextLine[2], nextLine[0], nextLine[1]));
                } catch (IndexOutOfBoundsException e) {
                    // TODO: handle Index out of bounds exception
                }
            }
        } catch (CsvValidationException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
            loadCSVFileContents(new File(fileLocation));
        } catch (FileNotFoundException e) {
            // TODO: handle file not found exception
        }
    }
}
