package Views.Customer;

import Managers.ViewManager;
import Utils.ConnectionDB;
import Views.LoginPage;
import Views.Page;
import Views.Customer.WishlistPage.Item;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.util.stream.Collectors;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardPage implements Page {
    private ViewManager viewManager;
    private StackPane root;

    private HBox navBar;
    private Button menuButton;
    private ContextMenu menuContext;
    private MenuItem wishlistMenu;
    private MenuItem purchaseHistoryMenu;
    private MenuItem signOutMenu;

    private HBox searchBar;
    private TextField searchField;
    private Button searchButton;

    private VBox mainLayout;
    private TableView<Item> table;

    public DashboardPage(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.root = new StackPane();
        init();
        setLayout();
        setEvent();
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
        signOutMenu = new MenuItem("Sign Out");
        menuContext.getItems().addAll(wishlistMenu, purchaseHistoryMenu, signOutMenu);

        HBox spacer = new HBox();
//        spacer.setHgrow(spacer, Priority.ALWAYS);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        navBar.getChildren().addAll(menuButton, spacer);

        searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setPadding(new Insets(10));
        searchField = new TextField();
        searchField.setPromptText("Search items...");
        searchField.setPrefWidth(300);
        searchButton = new Button("Search");
        searchBar.getChildren().addAll(searchField, searchButton);

        mainLayout = new VBox();
        mainLayout.setSpacing(10);
        mainLayout.setPadding(new Insets(10));

        table = new TableView<>();
        table.setPlaceholder(new Label("No content in table"));
        
        TableColumn<Item, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Item, String> nameColumn = new TableColumn<>("Item Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Item, String> categoryColumn = new TableColumn<>("Item Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Item, String> sizeColumn = new TableColumn<>("Item Size");
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));

        TableColumn<Item, Double> priceColumn = new TableColumn<>("Item Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Item, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button purchaseButton = new Button("Purchase");
            private final Button offerButton = new Button("Offer");
            private final Button wishlistButton = new Button("Add to Wishlist");

            {
                purchaseButton.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    showConfirmationDialog("Purchase Confirmation", 
                        "Are you sure you want to purchase " + item.getName() + "?", 
                        () -> System.out.println("Purchased: " + item.getName()));
                });

                offerButton.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    showOfferInputDialog("Offer Input", 
                        "Enter your offer price for " + item.getName() + ":", 
                        offer -> System.out.println("Offer for " + item.getName() + ": " + offer));
                });

                wishlistButton.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    showConfirmationDialog("Wishlist Confirmation", 
                        "Do you want to add " + item.getName() + " to your wishlist?", 
                        () -> System.out.println("Added to wishlist: " + item.getName()));
                });
            }
            
            
            private void showConfirmationDialog(String title, String content, Runnable onConfirm) {
                Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationDialog.setTitle(title);
                confirmationDialog.setHeaderText(null);
                confirmationDialog.setContentText(content);

                confirmationDialog.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        onConfirm.run();
                    }
                });
            }

            private void showOfferInputDialog(String title, String content, java.util.function.Consumer<Double> onOffer) {
                // Create dialog
                Dialog<Double> offerDialog = new Dialog<>();
                offerDialog.setTitle(title);

                ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
                offerDialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

                TextField offerInput = new TextField();
                offerInput.setPromptText("Enter your offer price");

                VBox layout = new VBox(10);
                layout.setPadding(new Insets(10));
                layout.getChildren().addAll(new Label(content), offerInput);

                offerDialog.getDialogPane().setContent(layout);

                offerDialog.setResultConverter(dialogButton -> {
                    if (dialogButton == submitButtonType) {
                        try {
                            return Double.parseDouble(offerInput.getText());
                        } catch (NumberFormatException e) {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setHeaderText(null);
                            errorAlert.setContentText("Please enter a valid number.");
                            errorAlert.showAndWait();
                            return null;
                        }
                    }
                    return null;
                });

                offerDialog.showAndWait().ifPresent(onOffer);
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


        table.getColumns().addAll(idColumn, nameColumn, categoryColumn, sizeColumn, priceColumn, actionColumn);

        
        List<Item> itemsFromDB = generateDummyItems();
        table.getItems().addAll(itemsFromDB);

       
        table.setPrefHeight(400);

        mainLayout.getChildren().addAll(table);
        
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

        signOutMenu.setOnAction(e -> {
            System.out.println("Sign out");
            viewManager.changePage(new LoginPage(viewManager).getPage());
        });

        searchButton.setOnAction(e -> {
            String query = searchField.getText().toLowerCase();
            table.getItems().clear();
            generateDummyItems().stream()
                    .filter(item -> item.getName().toLowerCase().contains(query)
                            || item.getCategory().toLowerCase().contains(query)
                            || item.getSize().toLowerCase().contains(query))
                    .forEach(filteredItem -> table.getItems().add(filteredItem));
        });
    }

    @Override
    public StackPane getPage() {
        return root;
    }

    public static class Item {
        private final String name;
        private final String category;
        private final String size;
        private final double price;

        public Item(String name, String category, String size, double price) {
            this.name = name;
            this.category = category;
            this.size = size;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }

        public String getSize() {
            return size;
        }

        public double getPrice() {
            return price;
        }
    }

    private List<Item> generateDummyItems() {
        List<Item> items = new ArrayList<>();
        try {
            ConnectionDB db = ConnectionDB.getInstance();
            PreparedStatement ps = db.prepareStatement("SELECT * FROM items WHERE item_status = 'Approved'");
            ResultSet rs = db.execQuery(ps);

            while (rs.next()) {
                String name = rs.getString("Item_name");
                String category = rs.getString("Item_category");
                String size = rs.getString("Item_size");
                double price = rs.getDouble("Item_price");
                items.add(new Item(name, category, size, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    
   
    
    


}





