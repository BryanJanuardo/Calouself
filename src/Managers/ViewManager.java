package Managers;

import Models.UserModel;
import Views.LoginPage;
import Views.Page;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ViewManager {
    private Stage stage;
    private Scene scene;
    private UserModel user = null;

    public ViewManager(Stage stage) {
    	this.stage = stage;
        scene = new Scene(new LoginPage(this).getPage(), 600, 400);        
        stage.setScene(scene);
        stage.show();
    }

    public void changePage(StackPane view) {
        if (stage.getScene() == null) {
            scene = new Scene(view, 400, 300);
            stage.setScene(scene);
        }
        stage.getScene().setRoot(view);
    }
    
    // Tambahkan method ini
    public void switchPage(Page page) {
        changePage(page.getPage());
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
