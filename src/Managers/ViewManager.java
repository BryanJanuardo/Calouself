package Managers;

import Models.UserModel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewManager {
	private Parent currentView;
	private Stage stage;
	private UserModel user = null;
	
	public ViewManager() {
		// TODO Auto-generated constructor stub
	}
	
	public ViewManager(Parent currentView, Stage stage) {
		super();
		this.currentView = currentView;
		this.stage = stage;
	}

	public Parent getCurrentView() {
		return currentView;
	}

	public void setCurrentView(Parent currentView) {
		this.currentView = currentView;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public void logout() {
		this.user = null;
	}
	
}
