package Views;


import Managers.ViewManager;
import Views.Customer.DashboardPage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LoginPage implements Page {
    private ViewManager viewManager;
    private StackPane root;

    private VBox vbox;
    private TextField usernameField;
    private PasswordField passwordField;
    private Label usernameErrorLabel;
    private Label passwordErrorLabel;
    private Label successMessageLabel;
    private Button loginButton;

    public LoginPage(ViewManager viewManager) {
        this(viewManager, ""); 
    }

    public LoginPage(ViewManager viewManager, String successMessage) {
        this.viewManager = viewManager;
        this.root = new StackPane();
        init(successMessage);
        setLayout();
        setEvent();
    }

    public void init(String successMessage) {
        vbox = new VBox(15); 
        vbox.setAlignment(Pos.CENTER); 
        vbox.setPadding(new Insets(30, 50, 30, 50)); 

        usernameField = new TextField();
        usernameField.setPrefWidth(300);
        usernameField.setPrefHeight(51);
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-font-size: 18px;");

        passwordField = new PasswordField();
        passwordField.setPrefWidth(300);
        passwordField.setPrefHeight(51);
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-font-size: 18px;");
   
        usernameErrorLabel = new Label("username is required");
        usernameErrorLabel.setTextFill(Color.RED);
        usernameErrorLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        usernameErrorLabel.setVisible(false);

        passwordErrorLabel = new Label("password is required");
        passwordErrorLabel.setTextFill(Color.RED);
        passwordErrorLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        passwordErrorLabel.setVisible(false);
       
        successMessageLabel = new Label(successMessage);
        successMessageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
        successMessageLabel.setVisible(!successMessage.isEmpty()); 
     
        loginButton = new Button("Login");
        loginButton.setPrefWidth(140);
        loginButton.setPrefHeight(51);
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
        gridPane.add(usernameErrorLabel, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(passwordErrorLabel, 1, 3);
        gridPane.add(loginButton, 1, 4);
        GridPane.setHalignment(loginButton, javafx.geometry.HPos.RIGHT);

        
        vbox.getChildren().addAll(successMessageLabel, titleLabel, gridPane);
        root.getChildren().add(vbox);
    }

    public void setEvent() {
        loginButton.setOnAction(e -> validateInputs());
    }

    private void validateInputs() {
        boolean hasError = false;

   
        if (usernameField.getText().trim().isEmpty()) {
            usernameErrorLabel.setVisible(true);
            hasError = true;
        } else {
            usernameErrorLabel.setVisible(false);
        }

      
        if (passwordField.getText().trim().isEmpty()) {
            passwordErrorLabel.setVisible(true);
            hasError = true;
        } else {
            passwordErrorLabel.setVisible(false);
        }

       
        if (!hasError) {
        	System.out.println("Login successful " + usernameField.getText() + "!");
            homePage();
        }
    }

    public void homePage() {
    	viewManager.changePage(new DashboardPage(viewManager).getPage());
    }

    @Override
    public StackPane getPage() {
        return root;
    }

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}


}
