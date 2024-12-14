package Views;

import Managers.ViewManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class RegisterPage implements Page {
    private ViewManager viewManager;
    private StackPane root;

    private VBox vbox;
    private TextField usernameField;
    private TextField phoneField;
    private TextField addressField;
    private PasswordField passwordField;
    private RadioButton buyerRadioButton;
    private RadioButton sellerRadioButton;
    private ToggleGroup roleGroup;
    private Button regisButton;

    private Label usernameErrorLabel;
    private Label phoneErrorLabel;
    private Label addressErrorLabel;
    private Label roleErrorLabel;
    private Label passwordErrorLabel;

    public RegisterPage(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.root = new StackPane();
        init();
        setLayout();
        setEvent();
    }

    @Override
    public void init() {
        vbox = new VBox(10); 
        vbox.setAlignment(Pos.TOP_CENTER); 
        vbox.setPadding(new Insets(30, 50, 30, 50)); 

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefWidth(700);
        usernameField.setPrefHeight(51);
        usernameField.setStyle("-fx-font-size: 18px;");

        phoneField = new TextField();
        phoneField.setPromptText("Phone");
        phoneField.setPrefWidth(700);
        phoneField.setPrefHeight(51);
        phoneField.setStyle("-fx-font-size: 18px;");

        addressField = new TextField();
        addressField.setPromptText("Address");
        addressField.setPrefWidth(700);
        addressField.setPrefHeight(51);
        addressField.setStyle("-fx-font-size: 18px;");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(700);
        passwordField.setPrefHeight(51);
        passwordField.setStyle("-fx-font-size: 18px;");
         
        usernameErrorLabel = new Label("username is required");
        usernameErrorLabel.setTextFill(Color.RED);
        usernameErrorLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        usernameErrorLabel.setVisible(false);
    
        phoneErrorLabel = new Label("phone is required");
        phoneErrorLabel.setTextFill(Color.RED);
        phoneErrorLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        phoneErrorLabel.setVisible(false);

        addressErrorLabel = new Label("address is required");
        addressErrorLabel.setTextFill(Color.RED);
        addressErrorLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        addressErrorLabel.setVisible(false);
       
        roleErrorLabel = new Label("role is required");
        roleErrorLabel.setTextFill(Color.RED);
        roleErrorLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        roleErrorLabel.setVisible(false);

        passwordErrorLabel = new Label("password is required");
        passwordErrorLabel.setTextFill(Color.RED);
        passwordErrorLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        passwordErrorLabel.setVisible(false);

        buyerRadioButton = new RadioButton("Buyer");
        buyerRadioButton.setStyle("-fx-font-size: 18px;");
        sellerRadioButton = new RadioButton("Seller");
        sellerRadioButton.setStyle("-fx-font-size: 18px;");
     
        roleGroup = new ToggleGroup();
        buyerRadioButton.setToggleGroup(roleGroup);
        sellerRadioButton.setToggleGroup(roleGroup);

        regisButton = new Button("Register");
        regisButton.setPrefWidth(140);
        regisButton.setPrefHeight(51);
        regisButton.setStyle("-fx-font-size: 20px; -fx-padding: 10px 20px; -fx-border-width: 2px;");
    }
    
    
    public void setLayout() {
        
        Label titleLabel = new Label("Register");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Label phoneLabel = new Label("Phone");
        phoneLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Label addressLabel = new Label("Address");
        addressLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Label roleLabel = new Label("Role");
        roleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER); 
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 50, 20, 50));

        
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(usernameErrorLabel, 1, 1);

        gridPane.add(phoneLabel, 0, 2);
        gridPane.add(phoneField, 1, 2);
        gridPane.add(phoneErrorLabel, 1, 3);

        gridPane.add(addressLabel, 0, 4);
        gridPane.add(addressField, 1, 4);
        gridPane.add(addressErrorLabel, 1, 5);

        HBox roleBox = new HBox(10, buyerRadioButton, sellerRadioButton);
        roleBox.setAlignment(Pos.CENTER_LEFT);
        gridPane.add(roleLabel, 0, 6);
        gridPane.add(roleBox, 1, 6);
        gridPane.add(roleErrorLabel, 1, 7);

        gridPane.add(passwordLabel, 0, 8);
        gridPane.add(passwordField, 1, 8);
        gridPane.add(passwordErrorLabel, 1, 9);

     
        gridPane.add(regisButton, 1, 10);
        GridPane.setHalignment(regisButton, javafx.geometry.HPos.RIGHT);

        
        vbox.getChildren().clear(); 
        vbox.getChildren().addAll(titleLabel, gridPane);
        vbox.setAlignment(Pos.CENTER); 
        vbox.setSpacing(20);

      
        root.getChildren().clear(); 
        root.getChildren().add(vbox);

       
        StackPane.setAlignment(vbox, Pos.CENTER);
    }



    @Override
    public void setEvent() {
        regisButton.setOnAction(e -> validateInputs());
    }

    

    private void validateInputs() {
        boolean hasError = false;

        
        if (usernameField.getText().trim().isEmpty()) {
            usernameErrorLabel.setVisible(true);
            hasError = true;
        } else {
            usernameErrorLabel.setVisible(false);
        }

     
        if (phoneField.getText().trim().isEmpty()) {
            phoneErrorLabel.setVisible(true);
            hasError = true;
        } else {
            phoneErrorLabel.setVisible(false);
        }

      
        if (addressField.getText().trim().isEmpty()) {
            addressErrorLabel.setVisible(true);
            hasError = true;
        } else {
            addressErrorLabel.setVisible(false);
        }

      
        if (roleGroup.getSelectedToggle() == null) {
            roleErrorLabel.setVisible(true);
            hasError = true;
        } else {
            roleErrorLabel.setVisible(false);
        }

      
        if (passwordField.getText().trim().isEmpty()) {
            passwordErrorLabel.setVisible(true);
            hasError = true;
        } else {
            passwordErrorLabel.setVisible(false);
        }

        
        if (!hasError) {
            System.out.println("Registration successful " + usernameField.getText() + "!");
            
            
            
            loginPage();
        }
    }
    
    
    public void loginPage() {
        viewManager.changePage(new LoginPage(viewManager).getPage());
    }
    


    @Override
    public StackPane getPage() {
        return root;
    }
}
