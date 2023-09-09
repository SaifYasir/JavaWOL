package com.javafx;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AlertBoxController {
    
    public Label messageLabel;
    
    public Button declineButton;
    public Button acceptButton;

    public boolean returnBoolean;

    public void returnDecline()
    {
        returnBoolean = false;
        Stage stage = (Stage)messageLabel.getScene().getWindow();
        stage.close();
    }

    public void returnAccept()
    {
        returnBoolean = true;
        Stage stage = (Stage)messageLabel.getScene().getWindow();
        stage.close();
    }
}
