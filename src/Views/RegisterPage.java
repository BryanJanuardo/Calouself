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
    private Button linkLogin;

    private Label errorLabel;

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
        usernameField.setPrefWidth(200);
        usernameField.setPrefHeight(30);
        usernameField.setStyle("-fx-font-size: 18px;");

        phoneField = new TextField();
        phoneField.setPromptText("Phone");
        phoneField.setPrefWidth(200);
        phoneField.setPrefHeight(30);
        phoneField.setStyle("-fx-font-size: 18px;");

        addressField = new TextField();
        addressField.setPromptText("Address");
        addressField.setPrefWidth(200);
        addressField.setPrefHeight(30);
        addressField.setStyle("-fx-font-size: 18px;");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(300);
        passwordField.setPrefHeight(51);
        passwordField.setStyle("-fx-font-size: 18px;");
         
        buyerRadioButton = new RadioButton("Buyer");
        buyerRadioButton.setStyle("-fx-font-size: 14px;");
        sellerRadioButton = new RadioButton("Seller");
        sellerRadioButton.setStyle("-fx-font-size: 14px;");
     
        roleGroup = new ToggleGroup();
        buyerRadioButton.setToggleGroup(roleGroup);
        sellerRadioButton.setToggleGroup(roleGroup);
        buyerRadioButton.setUserData("customer");
        sellerRadioButton.setUserData("seller");

        regisButton = new Button("Register");
        regisButton.setPrefWidth(80);
        regisButton.setPrefHeight(40);
        regisButton.setStyle("-fx-font-size: 20px; -fx-padding: 10px 20px; -fx-border-width: 2px;");
        
        errorLabel = new Label("");
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        errorLabel.setVisible(false);
        linkLogin = new Button ("Go to login page");
    }
    
       
    @Override
    public void setLayout() {
        Label titleLabel = new Label("Register");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titleLabel.setAlignment(Pos.CENTER);
        
        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label phoneLabel = new Label("Phone");
        phoneLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label addressLabel = new Label("Address");
        addressLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label roleLabel = new Label("Role");
        roleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // GridPane untuk Form
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER); // Form di tengah
        gridPane.setHgap(8); // Kurangi jarak horizontal
        gridPane.setVgap(8); // Kurangi jarak vertikal
        gridPane.setPadding(new Insets(10, 20, 10, 20)); // Padding lebih kecil

        // Tambahkan elemen ke GridPane
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);

        gridPane.add(phoneLabel, 0, 1);
        gridPane.add(phoneField, 1, 1);

        gridPane.add(addressLabel, 0, 2);
        gridPane.add(addressField, 1, 2);

        HBox roleBox = new HBox(10, buyerRadioButton, sellerRadioButton);
        roleBox.setAlignment(Pos.CENTER_LEFT);
        gridPane.add(roleLabel, 0, 3);
        gridPane.add(roleBox, 1, 3);

        gridPane.add(passwordLabel, 0, 4);
        gridPane.add(passwordField, 1, 4);

     
        gridPane.add(errorLabel, 1, 5);
        gridPane.add(linkLogin, 1, 6);
        gridPane.add(regisButton, 1, 7);
        GridPane.setHalignment(regisButton, javafx.geometry.HPos.LEFT);

        // Tambahkan GridPane ke VBox
        vbox.getChildren().clear();
        vbox.getChildren().addAll(titleLabel, gridPane);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10); // Kurangi jarak antar elemen
        vbox.setPadding(new Insets(10)); // Kurangi padding

        // Tambahkan VBox ke StackPane
        root.getChildren().clear();
        root.getChildren().add(vbox);
        StackPane.setAlignment(vbox, Pos.CENTER);
    }




    @Override
    public void setEvent() {
        regisButton.setOnAction(e -> validateInputs());
        linkLogin.setOnAction(e -> loginPage());
    }

    

    private void validateInputs() {
    	RadioButton selectedRadio = (RadioButton) roleGroup.getSelectedToggle();
    	
    	if(selectedRadio == null) {
    		errorLabel.setText("Please select a role (Buyer/Seller).");
            errorLabel.setVisible(true);
    		return;
    	}
    	
    	Response<UserModel> res = UserController.Register(usernameField.getText(), passwordField.getText(), 
    			phoneField.getText(), addressField.getText(), selectedRadio.getUserData().toString());
        
    	if (!res.getIsSuccess()) {
        	errorLabel.setText(res.getMessages());
        	errorLabel.setVisible(true);
        	return;
        }
    	
    	loginPage();
    }
    
    
    public void loginPage() {
        viewManager.changePage(new LoginPage(viewManager).getPage());
    }
    


    @Override
    public StackPane getPage() {
        return root;
    }
}
