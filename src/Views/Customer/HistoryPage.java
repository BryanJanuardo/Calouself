package Views.Customer;

import Managers.ViewManager;
import Models.TransactionModel;
import Views.LoginPage;
import Views.Page;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.math.BigDecimal;
import java.util.ArrayList;

public class HistoryPage implements Page {
    private ViewManager viewManager;
    private StackPane root;

  
    private HBox navBar;
    private Button menuButton;
    private ContextMenu menuContext;
    private MenuItem homeMenu;
    private MenuItem offerMenu;
    private MenuItem wishlistMenu;
    private MenuItem signOutMenu;

    
    private VBox mainLayout;
    private TableView<TransactionModel> transactionTable;

    public HistoryPage(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.root = new StackPane();
        init();
        setLayout();
        setEvent();
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
        homeMenu = new MenuItem("Homepage");
        wishlistMenu = new MenuItem("Wishlist");
        offerMenu = new MenuItem("Offer");
        signOutMenu = new MenuItem("Sign Out");
        menuContext.getItems().addAll(homeMenu, wishlistMenu, offerMenu, signOutMenu);

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        navBar.getChildren().addAll(menuButton, spacer);

        mainLayout = new VBox();
        mainLayout.setSpacing(10);
        mainLayout.setPadding(new Insets(10));

        transactionTable = new TableView<TransactionModel>();
        transactionTable.setPlaceholder(new Label("No content in table"));
        transactionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<TransactionModel, String> idColumn = new TableColumn<>("Transaction ID");
        TableColumn<TransactionModel, String> nameColumn = new TableColumn<>("Item Name");
        TableColumn<TransactionModel, String> categoryColumn = new TableColumn<>("Item Category");
        TableColumn<TransactionModel, String> sizeColumn = new TableColumn<>("Item Size");
        TableColumn<TransactionModel, BigDecimal> priceColumn = new TableColumn<>("Item Price");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("transaction_id"));
        
        nameColumn.setCellValueFactory(cellData -> {
            TransactionModel transaction = cellData.getValue();
            String itemName = transaction.product().item().getItem_name();
            return new SimpleStringProperty(itemName);
        });
        
        categoryColumn.setCellValueFactory(cellData -> {
            TransactionModel transaction = cellData.getValue();
            String itemCategory = transaction.product().item().getItem_category();
            return new SimpleStringProperty(itemCategory);
        });
        
        sizeColumn.setCellValueFactory(cellData -> {
            TransactionModel transaction = cellData.getValue();
            String itemSize = transaction.product().item().getItem_size();
            return new SimpleStringProperty(itemSize);
        });
        
        priceColumn.setCellValueFactory(cellData -> {
        	TransactionModel transaction = cellData.getValue();
            BigDecimal itemPrice = transaction.product().item().getItem_price();
            return new SimpleObjectProperty<>(itemPrice);
        });

        transactionTable.setPrefHeight(400);
        transactionTable.getColumns().addAll(idColumn, nameColumn, categoryColumn, sizeColumn, priceColumn);
        refreshTable();
        mainLayout.getChildren().addAll(transactionTable);
    }

    @Override
    public void setLayout() {
        VBox layoutWrapper = new VBox();
        layoutWrapper.getChildren().addAll(navBar, mainLayout);

        root.getChildren().add(layoutWrapper);
        root.setStyle("-fx-background-color: #f4f4f4;");
    }

    @Override
    public void setEvent() {
        menuButton.setOnAction(e -> {
            menuContext.show(menuButton,
                    menuButton.localToScreen(0, menuButton.getHeight()).getX(),
                    menuButton.localToScreen(0, menuButton.getHeight()).getY());
        });

        offerMenu.setOnAction(e -> viewManager.changePage(new OfferPage(viewManager).getPage()));
        homeMenu.setOnAction(e -> viewManager.changePage(new DashboardPage(viewManager).getPage()));
        wishlistMenu.setOnAction(e -> viewManager.changePage(new WishlistPage(viewManager).getPage()));
        signOutMenu.setOnAction(e -> viewManager.changePage(new LoginPage(viewManager).getPage()));
    }
    
    private void refreshTable() {
    	ArrayList<TransactionModel> transactions = TransactionModel.ViewHistory(viewManager.getUser().getUser_id()).getData();
    	ObservableList<TransactionModel> observableItems = FXCollections.observableArrayList(transactions);
    	transactionTable.setItems(observableItems);
    }

    @Override
    public StackPane getPage() {
        return root;
    }
}
