package Views.Admin;

import java.util.ArrayList;

import Controllers.ItemController;
import Managers.ViewManager;
import Models.ItemModel;
import Utils.Response;
import Views.LoginPage;
import Views.Page;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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
    private MenuItem signOutMenu;

    private VBox mainLayout;
    private TableView<ItemModel> itemTable;

    public DashboardPage(ViewManager viewManager) {
        this.viewManager = viewManager;
        init();
        setLayout();
        setEvent();
    }

    @Override
    public StackPane getPage() {
        return root;
    }

    public void init() {
        root = new StackPane();

        navBar = new HBox();
        navBar.setPadding(new Insets(10));
        navBar.setSpacing(10);
        navBar.setStyle("-fx-background-color: #333;");

        menuButton = new Button("Menu");
        menuButton.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
        navBar.getChildren().add(menuButton);

        menuContext = new ContextMenu();
        signOutMenu = new MenuItem("Sign Out");

        menuContext.getItems().addAll(signOutMenu);

        mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));

        Label titleLabel = new Label("Requested Items");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        itemTable = new TableView<>();
        itemTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<ItemModel, String> idColumn = new TableColumn<>("ID");
        TableColumn<ItemModel, String> nameColumn = new TableColumn<>("Item Name");
        TableColumn<ItemModel, String> categoryColumn = new TableColumn<>("Item Category");
        TableColumn<ItemModel, String> sizeColumn = new TableColumn<>("Item Size");
        TableColumn<ItemModel, String> priceColumn = new TableColumn<>("Item Price");
        TableColumn<ItemModel, String> statusColumn = new TableColumn<>("Item Status");
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("item_category"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("item_size"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("item_price"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("item_status"));
        
        TableColumn<ItemModel, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(col -> {
            TableCell<ItemModel, Void> cell = new TableCell<>() {
                private final Button approveButton = new Button("APPROVE");
                private final Button declineButton = new Button("DECLINE");
                private final HBox hb = new HBox(5, approveButton, declineButton);

                {
                    approveButton.setOnAction(e -> {
                    	ItemModel item = getTableView().getItems().get(getIndex());
                        handleApprove(item);
                    });

                    declineButton.setOnAction(e -> {
                    	ItemModel item = getTableView().getItems().get(getIndex());
                        handleDecline(item);
                    });
                }

                @Override
                protected void updateItem(Void v, boolean empty) {
                    super.updateItem(v, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(hb);
                    }
                }
            };
            return cell;
        });

        itemTable.getColumns().addAll(idColumn, nameColumn, categoryColumn, sizeColumn, priceColumn, statusColumn, actionColumn);
        refreshTable();
        mainLayout.getChildren().addAll(titleLabel, itemTable);
    }

    public void setLayout() {
        VBox wrapper = new VBox(navBar, mainLayout);
        root.getChildren().add(wrapper);
        root.setStyle("-fx-background-color: #f4f4f4;");
    }

    public void setEvent() {
        menuButton.setOnAction(e -> {
            menuContext.show(menuButton,
                    menuButton.localToScreen(0, menuButton.getHeight()).getX(),
                    menuButton.localToScreen(0, menuButton.getHeight()).getY()
            );
        });
        
        signOutMenu.setOnAction(e -> {
            viewManager.logout();
            viewManager.changePage(new LoginPage(viewManager).getPage());
        });
    }
    
    private void refreshTable() {
    	ArrayList<ItemModel> products = ItemController.ViewRequestItem("Pending").getData();
    	ObservableList<ItemModel> observableItems = FXCollections.observableArrayList(products);
    	itemTable.setItems(observableItems);
    }

    private void handleApprove(ItemModel item) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Approve Request");
        confirm.setContentText("Are you sure you want to approve the request for " + item.getItem_name() + "?");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Response<ItemModel> res = ItemController.ApproveItem(item.getItem_id());
                
                if(res.getIsSuccess()) {
                	showInfoAlert("Success", res.getMessages());
                	refreshTable();
                }else {
                	showInfoAlert("Failed", res.getMessages());
                }
            }
        });
    }

    private void handleDecline(ItemModel item) {
        Stage reasonStage = new Stage();
        VBox reasonLayout = new VBox(10);
        reasonLayout.setPadding(new Insets(10));

        Label reasonTitle = new Label("REASON");
        TextArea reasonField = new TextArea();
        reasonField.setPromptText("Enter decline reason...");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button submitButton = new Button("SUBMIT");
        submitButton.setOnAction(e -> {
            String reason = reasonField.getText().trim();
            Response<ItemModel> res = ItemController.DeclineItem(item.getItem_id(), reason);
            
            if(res.getIsSuccess()) {
            	refreshTable();
            	showInfoAlert("Info", res.getMessages() + " Item declined with reason: " + reason);    
            	reasonStage.close();
            }else {            	
            	errorLabel.setText(res.getMessages());            	
            }
        });

        reasonLayout.getChildren().addAll(reasonTitle, reasonField, errorLabel, submitButton);
        Scene reasonScene = new Scene(reasonLayout, 300, 200);
        reasonStage.setScene(reasonScene);
        reasonStage.setTitle("Decline Item");
        reasonStage.show();
    }

    private void showInfoAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}