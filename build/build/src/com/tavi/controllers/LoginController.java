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

public class LoginController implements Initializable {
	
	@FXML
	private TextField usernameTextField;
	
	@FXML
	private TextField passwordTextField;
	
	@FXML
	private Button loginButton;

	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public void onClickLoginButton(ActionEvent e) {
		System.out.println("login");
		MainController.client.connect();
		MainController.client.login(new User(usernameTextField.getText(),passwordTextField.getText()));
	    Stage stage = (Stage) loginButton.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}

}
