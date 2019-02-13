package com.tavi.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.tavi.client.ClientMain;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController extends Application implements Initializable  {

	public static ClientMain client=new ClientMain();
	
	@FXML
	private ListView<String> userListView;
	
	@FXML
	private TextField inputTextField;
	
	@FXML
	private TextArea chatTextArea;
	
	@FXML
	public Button loginButton;
	
	@FXML
	public Button registerButton;
	
	@FXML
	public Button sendButton;
	
	@FXML
	public Button disconnectButton;
	
	public MainController() {
		client.mainController=this;
	}
	
	public ArrayList<String> userNames;

	public void populateUserListView() {
		ObservableList<String> users=getUserName(this.userNames);
		userListView.setItems(users);
		userListView.refresh();
	}

	public ObservableList<String> getUserName(ArrayList<String> users) {
		ObservableList<String> names = FXCollections.observableArrayList();
		for (String user : users) {
			names.add(user);
		}

		return names;
	}
	
	@FXML
	public void disconnect() {
		if(client.isLoggedIn) {
			try {
				client.socket.close();
				this.loginButton.setDisable(false);
				this.registerButton.setDisable(false);
				this.chatTextArea.clear();
				this.userListView.setItems(null);
				this.userListView.refresh();
				client.readHandler.interrupt();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	public void onLoginButtonClick(ActionEvent e){
		 Pane root;
	        try {
	            root = (Pane)FXMLLoader.load(getClass().getClassLoader().getResource("com/tavi/controllers/LoginView.fxml"));
	            Stage stage = new Stage();
	            stage.setTitle("Login");
	            stage.setScene(new Scene(root, 450, 450));
	            stage.show();
	        }
	        catch (IOException ex) {
	            ex.printStackTrace();
	        }
	}
	
	@FXML
	public void onRegisterButtonClick(ActionEvent e){
		 Pane root;
	        try {
	            root = (Pane)FXMLLoader.load(getClass().getClassLoader().getResource("com/tavi/controllers/RegisterViews.fxml"));
	            Stage stage = new Stage();
	            stage.setTitle("Register");
	            stage.setScene(new Scene(root, 450, 450));
	            stage.show();
	        }
	        catch (IOException ex) {
	            ex.printStackTrace();
	        }
	}
	
	@FXML
	public void onSendButtonClick(ActionEvent e){
		if(client.isLoggedIn) {
			System.out.println("send");
			client.sendString(this.inputTextField.getText().toString());
			this.inputTextField.setText("");
		}
	}
	
	public void recieveText(String message) {
		if(client.isLoggedIn) {
			this.chatTextArea.appendText(message+"\n");
		}
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.chatTextArea.setEditable(false);
		this.disconnectButton.setDisable(true);
			
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		try {
			BorderPane root=(BorderPane) FXMLLoader.load(getClass().getResource("/com/tavi/controllers/MainView.fxml"));		
			Scene scene=new Scene(root,800,800);
			stage.setScene(scene);
			stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
