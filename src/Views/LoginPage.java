package Views;


import Controllers.UserController;
import Managers.ViewManager;
import Models.UserModel;
import Utils.Response;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LoginPage implements Page{
	private ViewManager viewManager;
	private StackPane root;

    private VBox vbox;
    private TextField usernameField;
    private PasswordField passwordField;
    private Label errorLabel;
    private Button loginButton;
    private Button linkRegister;

    public LoginPage(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.root = new StackPane();
        init();
        setLayout();
        setEvent();
    }

    public void init() {
    	linkRegister = new Button("Go to register page");
		
        vbox = new VBox(15); 
        vbox.setAlignment(Pos.CENTER); 
        vbox.setPadding(new Insets(30, 50, 30, 50)); 

        usernameField = new TextField();
        usernameField.setPrefWidth(200);
        usernameField.setPrefHeight(51);
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-font-size: 18px;");

        passwordField = new PasswordField();
        passwordField.setPrefWidth(200);
        passwordField.setPrefHeight(51);
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-font-size: 18px;");
   
        errorLabel = new Label("password is required");
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        errorLabel.setVisible(false);
        
        loginButton = new Button("Login");
        loginButton.setPrefWidth(140);
        loginButton.setPrefHeight(40);
        loginButton.setStyle("-fx-font-size: 20px; -fx-padding: 10px 20px; -fx-border-width: 2px;");
    }

    public void setLayout() {
       
        Label titleLabel = new Label("Login");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        titleLabel.setAlignment(Pos.CENTER);

       
        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 50, 20, 50));

    
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(errorLabel, 1, 2);
        gridPane.add(linkRegister, 1, 3);
        gridPane.add(loginButton, 1, 4);
        GridPane.setHalignment(loginButton, javafx.geometry.HPos.LEFT);

        
        vbox.getChildren().addAll(titleLabel, gridPane);
        root.getChildren().add(vbox);
    }

    public void setEvent() {
        loginButton.setOnAction(e -> validateInputs());
        linkRegister.setOnAction(e -> registerPage());
    }

    private void validateInputs() {
    	if(usernameField.getText().equals("admin") && passwordField.getText().equals("admin")) {
    		adminPage();
    	}
    	
        Response<UserModel> res = UserController.Login(usernameField.getText(), passwordField.getText());
       
        if (!res.getIsSuccess()) {
        	errorLabel.setText(res.getMessages());
        	errorLabel.setVisible(true);
        	return;
        }    
        
        viewManager.setUser(res.getData());
    	if(res.getData().getRole().equals("customer")) {
    		customerPage();
    	}else if(res.getData().getRole().equals("seller")) {
    		sellerPage();
    	}
    }
    
    public void sellerPage() {
		viewManager.changePage(new Views.Seller.DashboardPage(viewManager).getPage());
	}

	public void adminPage() {
		viewManager.changePage(new Views.Admin.DashboardPage(viewManager).getPage());
	}

    public void customerPage() {
    	viewManager.changePage(new Views.Customer.DashboardPage(viewManager).getPage());
    }

    public void registerPage() {
    	viewManager.changePage(new RegisterPage(viewManager).getPage());    	
    }
    
    @Override
    public StackPane getPage() {
        return root;
    }
}
