package com.javafx;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    AlertBoxController controller;

    public void display(String message, String title) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/warning.fxml"));
        Parent root = loader.load();
        controller = loader.getController();


        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        stage.setScene(new Scene(root));
        controller.messageLabel.setText(message);
        stage.showAndWait();
    }

    public Boolean getReturnBoolean()
    {
        return controller.returnBoolean;
    }
}
