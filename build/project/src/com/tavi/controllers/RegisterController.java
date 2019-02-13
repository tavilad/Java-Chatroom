package com.tavi.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class RegisterController implements Initializable {
	
	@FXML
	private TextField usernameTextField;
	
	@FXML
	private TextField passwordTextField;
	
	@FXML
	private Button registerButton;
	
	@FXML
	public void registerUser(ActionEvent e) {
		MainController.client.connect();
		MainController.client.register(new User(usernameTextField.getText(),passwordTextField.getText()));
	    Stage stage = (Stage) registerButton.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
