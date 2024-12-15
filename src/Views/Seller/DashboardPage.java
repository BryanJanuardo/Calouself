package Views.Seller;

import java.math.BigDecimal;
import java.util.ArrayList;

import Controllers.ItemController;
import Managers.ViewManager;
import Models.ItemModel;
import Models.ProductModel;
import Utils.Response;
import Views.LoginPage;
import Views.Page;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DashboardPage implements Page {
    private ViewManager viewManager;
    private StackPane root;

    private HBox navBar;
    private Button menuButton;
    
    private ContextMenu menuContext; 
    private MenuItem offeredItemMenu; 
    private MenuItem signOutMenu;

    private VBox mainLayout;

    private TextField itemNameField, itemCategoryField, itemSizeField, itemPriceField;
    private TableView<ItemModel> itemsTable;
    private Label errorMessage;

    private Label titleLabel;
    private GridPane uploadForm;
    private Label tableLabel;
    private Button editButton;
    private Button deleteButton;
    private HBox actionButtons;

    public DashboardPage(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.root = new StackPane();
        init();
        setLayout();
        setEvent();
    }

    @Override
    public void init() {
        itemNameField = new TextField();
        itemCategoryField = new TextField();
        itemSizeField = new TextField();
        itemPriceField = new TextField();
        

        itemsTable = new TableView<ItemModel>();
        TableColumn<ItemModel, String> idColumn = new TableColumn<>("ID");
        TableColumn<ItemModel, String> nameColumn = new TableColumn<>("Item Name");
        TableColumn<ItemModel, String> categoryColumn = new TableColumn<>("Item Category");
        TableColumn<ItemModel, String> sizeColumn = new TableColumn<>("Item Size");
        TableColumn<ItemModel, BigDecimal> priceColumn = new TableColumn<>("Item Price");
        TableColumn<ItemModel, String> statusColumn = new TableColumn<>("Item Status");
        TableColumn<ItemModel, String> reasonColumn = new TableColumn<>("Reason");
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("item_category"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("item_size"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("item_price"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("item_status"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));

        itemsTable.getColumns().addAll(idColumn, nameColumn, categoryColumn, sizeColumn, priceColumn, statusColumn, reasonColumn);
        refreshTable();
        
        errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red;");

        titleLabel = new Label("Upload Item");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        uploadForm = new GridPane();
        uploadForm.setHgap(10);
        uploadForm.setVgap(10);

        Label itemNameLabel = new Label("Item Name:");
        Label itemCategoryLabel = new Label("Item Category:");
        Label itemSizeLabel = new Label("Item Size:");
        Label itemPriceLabel = new Label("Item Price:");
        Button saveButton = new Button("SAVE");

        uploadForm.add(itemNameLabel, 0, 0);
        uploadForm.add(itemNameField, 1, 0);
        uploadForm.add(itemCategoryLabel, 0, 1);
        uploadForm.add(itemCategoryField, 1, 1);
        uploadForm.add(itemSizeLabel, 0, 2);
        uploadForm.add(itemSizeField, 1, 2);
        uploadForm.add(itemPriceLabel, 0, 3);
        uploadForm.add(itemPriceField, 1, 3);
        uploadForm.add(saveButton, 1, 4);
        uploadForm.add(errorMessage, 1, 5);

        tableLabel = new Label("Uploaded Items");
        tableLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        editButton = new Button("EDIT");
        deleteButton = new Button("DELETE");
        actionButtons = new HBox(10, editButton, deleteButton);
        actionButtons.setAlignment(Pos.CENTER);

        navBar = new HBox();
        navBar.setPadding(new Insets(10));
        navBar.setSpacing(10);
        navBar.setStyle("-fx-background-color: #333;");

        menuButton = new Button("Menu");
        menuButton.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
        navBar.getChildren().add(menuButton);

        menuContext = new ContextMenu();
        offeredItemMenu = new MenuItem("Offered Item");
        signOutMenu = new MenuItem("Sign Out");
        menuContext.getItems().addAll(offeredItemMenu, signOutMenu);

        mainLayout = new VBox();
        mainLayout.setSpacing(10);
        mainLayout.setPadding(new Insets(10));

        mainLayout.getChildren().addAll(titleLabel, uploadForm, tableLabel, itemsTable, actionButtons);
    }

    @Override
    public void setLayout() {
        VBox wrapper = new VBox();
        wrapper.getChildren().addAll(navBar, mainLayout);

        root.getChildren().add(wrapper);
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

        offeredItemMenu.setOnAction(e -> {
            viewManager.switchPage(new OfferPage(viewManager));
        });
        
        signOutMenu.setOnAction(e -> {
            viewManager.logout();
            viewManager.changePage(new LoginPage(viewManager).getPage());
        });

        Button saveButton = (Button) uploadForm.getChildren().filtered(node -> node instanceof Button && ((Button) node).getText().equals("SAVE")).get(0);
        saveButton.setOnAction(e -> {
            String itemName = itemNameField.getText();
            String itemCategory = itemCategoryField.getText();
            String itemSize = itemSizeField.getText();
            String itemPrice = itemPriceField.getText();
            
            Response<ItemModel> res = ItemController.UploadItem(viewManager.getUser().getUser_id(), itemName, itemCategory, itemSize, itemPrice);
            if(res.getIsSuccess()) {
            	errorMessage.setText("Item uploaded successfully!");
            	refreshTable();
            }else {
            	errorMessage.setText(res.getMessages());
            }
        });

        editButton.setOnAction(e -> {
            ItemModel selectedItem = itemsTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                openEditModal(selectedItem);
            } else {
                showErrorAlert("No Item Selected", "Please select an item to edit.");
            }
        });

        deleteButton.setOnAction(e -> {
            ItemModel selectedItem = itemsTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation");
                confirmationAlert.setHeaderText("Are you sure you want to delete this item?");
                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                    	Response<ItemModel> res = ItemController.DeleteItem(selectedItem.getItem_id());
                    	if(!res.getIsSuccess()) {
                    		Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    		errorAlert.setTitle("Error Delete Item");
                    		errorAlert.setHeaderText(res.getMessages());
                    	}
                    	refreshTable();
                    }
                });
            } else {
                showErrorAlert("No Item Selected", "Please select an item to delete.");
            }
        });
    }
    
    private void refreshTable() {
    	ArrayList<ItemModel> products = ItemController.ViewSellerItem(viewManager.getUser().getUser_id()).getData();
    	ObservableList<ItemModel> observableItems = FXCollections.observableArrayList(products);
    	itemsTable.setItems(observableItems);
    }
    
    private void openEditModal(ItemModel selectedItem) {
        Stage editStage = new Stage();
        VBox editLayout = new VBox(10);
        editLayout.setPadding(new Insets(10));

        Label editTitle = new Label("Edit Item");
        Label errorEditMessage = new Label("");
        errorEditMessage.setStyle("-fx-text-fill: red;");
        editTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField editNameField = new TextField(selectedItem.getItem_name());
        TextField editCategoryField = new TextField(selectedItem.getItem_category());
        TextField editSizeField = new TextField(selectedItem.getItem_size());
        TextField editPriceField = new TextField(selectedItem.getItem_price().toString());

        Button saveEditButton = new Button("SAVE");
        saveEditButton.setOnAction(event -> {
        	Response<ItemModel> res = ItemController.EditItem(selectedItem.getItem_id(), editNameField.getText(), 
        			editCategoryField.getText(), editSizeField.getText(), editPriceField.getText());
        	if(res.getIsSuccess()) {
        		refreshTable();
        		errorEditMessage.setText("Item updated successfully!");
        		editStage.close();        		
        	}else {        		
        		errorEditMessage.setText(res.getMessages());
        	}
        });

        editLayout.getChildren().addAll(editTitle, editNameField, editCategoryField, editSizeField, editPriceField, errorEditMessage, saveEditButton);

        Scene editScene = new Scene(editLayout, 300, 200);
        editStage.setScene(editScene);
        editStage.setTitle("Edit Item");
        editStage.show();
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInfoAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public StackPane getPage() {
        return root;
    }
}
