package Views.Customer;

import java.math.BigDecimal;
import java.util.ArrayList;

import Controllers.ItemController;
import Managers.ViewManager;
import Models.OfferModel;
import Models.ProductModel;
import Models.TransactionModel;
import Models.WishlistModel;
import Utils.Response;
import Views.LoginPage;
import Views.Page;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardPage implements Page {
    private ViewManager viewManager;
    private StackPane root;

    private HBox navBar;
    private Button menuButton;
    private ContextMenu menuContext;
    private MenuItem offerMenu;
    private MenuItem wishlistMenu;
    private MenuItem purchaseHistoryMenu;
    private MenuItem signOutMenu;

    private HBox searchBar;
    private TextField searchField;
    private Button searchButton;
    private Button resetButton;

    private VBox mainLayout;
    private TableView<ProductModel> itemTable;

    public DashboardPage(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.root = new StackPane();
        init();
        setLayout();
        setEvent();
    }
    
    public void offer() {
    	viewManager.changePage(new OfferPage(viewManager).getPage());
    }

    public void wishlist() {
        viewManager.changePage(new WishlistPage(viewManager).getPage());
    }

    public void purchaseHistory() {
        viewManager.changePage(new HistoryPage(viewManager).getPage());
    }

    @SuppressWarnings("unchecked")
	@Override
    public void init() {
        navBar = new HBox();
        navBar.setPadding(new Insets(10));
        navBar.setSpacing(10);
        navBar.setStyle("-fx-background-color: #333;");

        menuButton = new Button("Menu");
        menuButton.setStyle("-fx-text-fill: white; -fx-background-color: #555;");

        menuContext = new ContextMenu();
        wishlistMenu = new MenuItem("Wishlist");
        purchaseHistoryMenu = new MenuItem("Purchase History");
        offerMenu = new MenuItem("Offer");
        signOutMenu = new MenuItem("Sign Out");
        menuContext.getItems().addAll(wishlistMenu, purchaseHistoryMenu, offerMenu, signOutMenu);

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        navBar.getChildren().addAll(menuButton, spacer);

        searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setPadding(new Insets(10));
        searchField = new TextField();
        searchField.setPromptText("Search items...");
        searchField.setPrefWidth(300);
        searchButton = new Button("Search");
        resetButton = new Button("Reset");
        searchBar.getChildren().addAll(searchField, searchButton, resetButton);

        mainLayout = new VBox();
        mainLayout.setSpacing(10);
        mainLayout.setPadding(new Insets(10));

        itemTable = new TableView<>();
        itemTable.setPlaceholder(new Label("No content in table"));
        
        TableColumn<ProductModel, String> idColumn = new TableColumn<>("ID");
        TableColumn<ProductModel, String> nameColumn = new TableColumn<>("Item Name");
        TableColumn<ProductModel, String> categoryColumn = new TableColumn<>("Item Category");
        TableColumn<ProductModel, String> sizeColumn = new TableColumn<>("Item Size");
        TableColumn<ProductModel, BigDecimal> priceColumn = new TableColumn<>("Item Price");
        TableColumn<ProductModel, Void> actionColumn = new TableColumn<>("Action");
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        nameColumn.setCellValueFactory(cellData -> {
            ProductModel product = cellData.getValue();
            String itemName = product.item().getItem_name();
            return new SimpleStringProperty(itemName);
        });

        categoryColumn.setCellValueFactory(cellData -> {
        	ProductModel product = cellData.getValue();
        	String category = product.item().getItem_category();
        	return new SimpleStringProperty(category);
        });
        
        sizeColumn.setCellValueFactory(cellData -> {
        	ProductModel product = cellData.getValue();
            String size = product.item().getItem_size();
            return new SimpleStringProperty(size);
        });
        
        priceColumn.setCellValueFactory(cellData -> {
        	ProductModel product = cellData.getValue();
            BigDecimal itemPrice = product.item().getItem_price();
            return new SimpleObjectProperty<>(itemPrice);
        });
        
        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button purchaseButton = new Button("Purchase");
            private final Button offerButton = new Button("Offer");
            private final Button wishlistButton = new Button("Add to Wishlist");

            {
                purchaseButton.setOnAction(event -> {
                    ProductModel product = getTableView().getItems().get(getIndex());
                    String itemName = product.item().getItem_name();
                    showConfirmationPurchase("Purchase Confirmation", 
                        "Are you sure you want to purchase " + itemName + "?", 
                        product);
                });

                offerButton.setOnAction(event -> {
                    ProductModel product = getTableView().getItems().get(getIndex());
                    String itemName = product.item().getItem_name();
                    showOfferInputDialog("Offer Input", 
                        "Enter your offer price for " + itemName + ":", 
                        product);
                });

                wishlistButton.setOnAction(event -> {
                    ProductModel product = getTableView().getItems().get(getIndex());
                    String itemName = product.item().getItem_name();
                    showConfirmationWishlist("Wishlist Confirmation", 
                        "Do you want to add " + itemName + " to your wishlist?", 
                        product);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(5, purchaseButton, offerButton, wishlistButton);
                    setGraphic(buttons);
                }
            }
        });


        itemTable.getColumns().addAll(idColumn, nameColumn, categoryColumn, sizeColumn, priceColumn, actionColumn);
        itemTable.setPrefHeight(400);
        refreshTable();
        mainLayout.getChildren().addAll(itemTable);
        
    }

    @Override
    public void setLayout() {
        VBox layoutWrapper = new VBox();
        layoutWrapper.getChildren().addAll(navBar, searchBar, mainLayout);

        root.getChildren().add(layoutWrapper);
        root.setStyle("-fx-background-color: #f4f4f4;");
    }

    @Override
    public void setEvent() {
        menuButton.setOnAction(e -> {
            menuContext.show(menuButton,
                    menuButton.localToScreen(0, menuButton.getHeight()).getX(),
                    menuButton.localToScreen(0, menuButton.getHeight()).getY()
            );
        });

        wishlistMenu.setOnAction(e -> wishlist());

        purchaseHistoryMenu.setOnAction(e -> purchaseHistory());
        
        offerMenu.setOnAction(e -> offer());

        signOutMenu.setOnAction(e -> {
        	viewManager.logout();
            viewManager.changePage(new LoginPage(viewManager).getPage());
        });

        searchButton.setOnAction(e -> {
            String query = searchField.getText().toLowerCase();
            searchItem(query);
        });
        
        resetButton.setOnAction(e -> {
        	refreshTable();
        	searchField.setText("");
        });
    }
    
    private void showConfirmationPurchase(String title, String content, ProductModel product) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle(title);
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText(content);

        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
            	Response<TransactionModel> res = TransactionModel.PurchaseItem(viewManager.getUser().getUser_id(), product.getProduct_id());
            	
            	if(res.getIsSuccess()) {
            		showInfoAlert("Info", res.getMessages());
            	} else {                    		
            		showInfoAlert("Failed", res.getMessages());
            	}
            }
        });
    }
    
    private void showConfirmationWishlist(String title, String content, ProductModel product) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle(title);
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText(content);

        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
            	Response<WishlistModel> res = WishlistModel.AddWishlist(product.getProduct_id(), viewManager.getUser().getUser_id());
            	
            	if(res.getIsSuccess()) {
            		showInfoAlert("Info", res.getMessages());
            	} else {                    		
            		showInfoAlert("Failed", res.getMessages());
            	}
            }
        });
    }
    
    private void showInfoAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showOfferInputDialog(String title, String content, ProductModel product) {
    	Stage offerStage = new Stage();
        VBox offerLayout = new VBox(10);
        offerLayout.setPadding(new Insets(10));
        
        Label offerTitleLabel = new Label(title);
    	TextArea offerField = new TextArea();
    	offerField.setPromptText("Enter offer value...");
    	
    	Label errorLabel = new Label("");
    	errorLabel.setStyle("-fx-text-fill: red;");
        
    	Button submitButton = new Button("SUBMIT");
    	
    	submitButton.setOnAction(e -> {
            String offer = offerField.getText().trim();
            Response<OfferModel> res = ItemController.OfferPrice(product.getProduct_id(), 
            		viewManager.getUser().getUser_id(), offer);
            
            if(res.getIsSuccess()) {            	
            	showInfoAlert("Info", res.getMessages() + "Product Offered: " + offer);
            	offerStage.close();
            }else {
            	errorLabel.setText(res.getMessages());            	
            }
        });
    	
    	offerLayout.getChildren().addAll(offerTitleLabel, offerField, errorLabel, submitButton);
    	Scene offerScene = new Scene(offerLayout, 300, 200);
    	offerStage.setScene(offerScene);
    	offerStage.setTitle("Offer Product");
    	offerStage.show();
    }
    
    private void searchItem(String query) {
    	ArrayList<ProductModel> products = ItemController.BrowseItem(query).getData();
    	ObservableList<ProductModel> observableItems = FXCollections.observableArrayList(products);
    	itemTable.setItems(observableItems);
    }
    
    private void refreshTable() {
    	ArrayList<ProductModel> products = ItemController.ViewItem().getData();
    	ObservableList<ProductModel> observableItems = FXCollections.observableArrayList(products);
    	itemTable.setItems(observableItems);
    }	

    @Override
    public StackPane getPage() {
        return root;
    }
}