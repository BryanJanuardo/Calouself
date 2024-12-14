package Views.Seller;

import Managers.ViewManager;
import Views.LoginPage;
import Views.Page;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class OfferPage implements Page {
    private ViewManager viewManager;
    private StackPane root;

    // Navbar
    private HBox navBar;
    private Button menuButton;
    private ContextMenu menuContext;
    private MenuItem homeMenu;
    private MenuItem signOutMenu;

    // Main layout
    private VBox mainLayout;
    private TableView<OfferItem> offerTable;

    public OfferPage(ViewManager viewManager) {
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

        // Navbar
        navBar = new HBox();
        navBar.setPadding(new Insets(10));
        navBar.setSpacing(10);
        navBar.setStyle("-fx-background-color: #333;");

        menuButton = new Button("Menu");
        menuButton.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
        navBar.getChildren().add(menuButton);

        menuContext = new ContextMenu();
        homeMenu = new MenuItem("Home");
        signOutMenu = new MenuItem("Sign Out");
        menuContext.getItems().addAll(homeMenu, signOutMenu);

        mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));

        Label titleLabel = new Label("Offered Items");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        offerTable = new TableView<>();
        offerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

   
        TableColumn<OfferItem, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> data.getValue().idProperty());

        TableColumn<OfferItem, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setCellValueFactory(data -> data.getValue().nameProperty());

        TableColumn<OfferItem, String> categoryCol = new TableColumn<>("Item Category");
        categoryCol.setCellValueFactory(data -> data.getValue().categoryProperty());

        TableColumn<OfferItem, String> sizeCol = new TableColumn<>("Item Size");
        sizeCol.setCellValueFactory(data -> data.getValue().sizeProperty());

        TableColumn<OfferItem, String> priceCol = new TableColumn<>("Item Price");
        priceCol.setCellValueFactory(data -> data.getValue().priceProperty());

        TableColumn<OfferItem, String> offeredPriceCol = new TableColumn<>("Offered Price");
        offeredPriceCol.setCellValueFactory(data -> data.getValue().offeredPriceProperty());

//    ACC And Decline
        TableColumn<OfferItem, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(col -> {
            TableCell<OfferItem, Void> cell = new TableCell<>() {
                private final Button acceptButton = new Button("ACCEPT");
                private final Button declineButton = new Button("DECLINE");
                private final HBox hb = new HBox(5, acceptButton, declineButton);

                {
                    acceptButton.setOnAction(e -> {
                        OfferItem item = getTableView().getItems().get(getIndex());
                        handleAccept(item);
                    });

                    declineButton.setOnAction(e -> {
                        OfferItem item = getTableView().getItems().get(getIndex());
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

        offerTable.getColumns().addAll(idCol, nameCol, categoryCol, sizeCol, priceCol, offeredPriceCol, actionCol);

        // Dummy data
        offerTable.getItems().addAll(
            new OfferItem("ID001", "Item Name", "Item Category", "M", "1000", "900")
        );

        mainLayout.getChildren().addAll(titleLabel, offerTable);
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

        homeMenu.setOnAction(e -> {
            viewManager.switchPage(new DashboardPage(viewManager));
        });
        
        signOutMenu.setOnAction(e -> {
            viewManager.logout();
            viewManager.changePage(new LoginPage(viewManager).getPage());
        });
    }

    private void handleAccept(OfferItem item) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Accept Offer");
        confirm.setContentText("Are you sure you want to accept the offer for " + item.getName() + "?");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                showInfoAlert("Success", "Offer accepted!");
                // Tambahkan logika accept disini buat update database
            }
        });
    }

    private void handleDecline(OfferItem item) {
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
            if (reason.isEmpty()) {
                errorLabel.setText("Reason cannot be empty!");
            } else {
                showInfoAlert("Info", "Offer declined with reason: " + reason);
                reasonStage.close();
                // Tambah logika decline  buat update database
            }
        });

        reasonLayout.getChildren().addAll(reasonTitle, reasonField, errorLabel, submitButton);
        Scene reasonScene = new Scene(reasonLayout, 300, 200);
        reasonStage.setScene(reasonScene);
        reasonStage.setTitle("Decline Offer");
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


class OfferItem {
    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty category;
    private SimpleStringProperty size;
    private SimpleStringProperty price;
    private SimpleStringProperty offeredPrice;

    public OfferItem(String id, String name, String category, String size, String price, String offeredPrice) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleStringProperty(category);
        this.size = new SimpleStringProperty(size);
        this.price = new SimpleStringProperty(price);
        this.offeredPrice = new SimpleStringProperty(offeredPrice);
    }

    public String getId() { return id.get(); }
    public SimpleStringProperty idProperty() { return id; }

    public String getName() { return name.get(); }
    public SimpleStringProperty nameProperty() { return name; }

    public String getCategory() { return category.get(); }
    public SimpleStringProperty categoryProperty() { return category; }

    public String getSize() { return size.get(); }
    public SimpleStringProperty sizeProperty() { return size; }

    public String getPrice() { return price.get(); }
    public SimpleStringProperty priceProperty() { return price; }

    public String getOfferedPrice() { return offeredPrice.get(); }
    public SimpleStringProperty offeredPriceProperty() { return offeredPrice; }
}
