package Views.Customer;

import Managers.ViewManager;
import Views.LoginPage;
import Views.Page;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class HistoryPage implements Page {
    private ViewManager viewManager;
    private StackPane root;

  
    private HBox navBar;
    private Button menuButton;
    private ContextMenu menuContext;
    private MenuItem homeMenu;
    private MenuItem wishlistMenu;
    private MenuItem signOutMenu;

    
    private VBox mainLayout;
    private TableView<Transaction> table;

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
        homeMenu = new MenuItem("Back to Homepage");
        wishlistMenu = new MenuItem("Wishlist");
        signOutMenu = new MenuItem("Sign Out");
        menuContext.getItems().addAll(homeMenu, wishlistMenu, signOutMenu);

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        navBar.getChildren().addAll(menuButton, spacer);

        mainLayout = new VBox();
        mainLayout.setSpacing(10);
        mainLayout.setPadding(new Insets(10));

        table = new TableView<>();
        table.setPlaceholder(new Label("No content in table"));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Transaction, String> transactionIdColumn = new TableColumn<>("Transaction ID");
        transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));

        TableColumn<Transaction, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Transaction, String> categoryColumn = new TableColumn<>("Item Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

        TableColumn<Transaction, String> sizeColumn = new TableColumn<>("Item Size");
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("itemSize"));

        TableColumn<Transaction, Double> priceColumn = new TableColumn<>("Item Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        table.getColumns().addAll(transactionIdColumn, itemNameColumn, categoryColumn, sizeColumn, priceColumn);


        List<Transaction> dummyData = generateDummyTransactions();
        System.out.println("Dummy Data Size: " + dummyData.size()); 
        table.getItems().addAll(dummyData);
        System.out.println("Table Data Size: " + table.getItems().size()); 

        table.setPrefHeight(400);

        mainLayout.getChildren().addAll(table);
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

        homeMenu.setOnAction(e -> viewManager.changePage(new DashboardPage(viewManager).getPage()));
        wishlistMenu.setOnAction(e -> viewManager.changePage(new WishlistPage(viewManager).getPage()));
        signOutMenu.setOnAction(e -> viewManager.changePage(new LoginPage(viewManager).getPage()));
    }

    @Override
    public StackPane getPage() {
        return root;
    }

    private List<Transaction> generateDummyTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("TR0001", "Item Name 1", "Category 1", "Size 1", 100.0));
        transactions.add(new Transaction("TR0002", "Item Name 2", "Category 2", "Size 2", 200.0));
        transactions.add(new Transaction("TR0003", "Item Name 3", "Category 3", "Size 3", 300.0));
        transactions.add(new Transaction("TR0004", "Item Name 4", "Category 4", "Size 4", 400.0));
        transactions.add(new Transaction("TR0005", "Item Name 5", "Category 5", "Size 5", 500.0));
        return transactions;
    }

    public static class Transaction {
        private final String transactionId;
        private final String itemName;
        private final String itemCategory;
        private final String itemSize;
        private final double itemPrice;

        public Transaction(String transactionId, String itemName, String itemCategory, String itemSize, double itemPrice) {
            this.transactionId = transactionId;
            this.itemName = itemName;
            this.itemCategory = itemCategory;
            this.itemSize = itemSize;
            this.itemPrice = itemPrice;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public String getItemName() {
            return itemName;
        }

        public String getItemCategory() {
            return itemCategory;
        }

        public String getItemSize() {
            return itemSize;
        }

        public double getItemPrice() {
            return itemPrice;
        }
    }
}
