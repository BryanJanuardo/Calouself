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

public class WishlistPage implements Page {
    private ViewManager viewManager;
    private StackPane root;

    private HBox navBar;
    private Button menuButton;
    private ContextMenu menuContext;
    private MenuItem homeMenu;
    private MenuItem transactionHistoryMenu;
    private MenuItem signOutMenu;

    private VBox mainLayout;
    private TableView<Item> table;

    public WishlistPage(ViewManager viewManager) {
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
        transactionHistoryMenu = new MenuItem("Transaction History");
        signOutMenu = new MenuItem("Sign Out");
        menuContext.getItems().addAll(homeMenu, transactionHistoryMenu, signOutMenu);

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
//        spacer.setHgrow(spacer, Priority.ALWAYS);

        navBar.getChildren().addAll(menuButton, spacer);

        mainLayout = new VBox();
        mainLayout.setSpacing(10);
        mainLayout.setPadding(new Insets(10));

        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 

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
            private final Button removeButton = new Button("Remove");

            {
                removeButton.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    showConfirmationDialog("Remove Confirmation",
                            "Are you sure you want to remove " + item.getName() + " from the wishlist?",
                            () -> table.getItems().remove(item));
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removeButton);
                }
            }
        });

        table.getColumns().addAll(idColumn, nameColumn, categoryColumn, sizeColumn, priceColumn, actionColumn);

        List<Item> dummyItems = generateDummyItems();
        table.getItems().addAll(dummyItems);

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
                    menuButton.localToScreen(0, menuButton.getHeight()).getY()
            );
        });

        homeMenu.setOnAction(e -> {
            viewManager.changePage(new DashboardPage(viewManager).getPage());
        });

        transactionHistoryMenu.setOnAction(e -> {
            viewManager.changePage(new HistoryPage(viewManager).getPage());
        });

        signOutMenu.setOnAction(e -> {
            System.out.println("Sign out");
            viewManager.changePage(new LoginPage(viewManager).getPage());
        });
    }

    @Override
    public StackPane getPage() {
        return root;
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

    public static class Item {
        private final int id;
        private final String name;
        private final String category;
        private final String size;
        private final double price;

        public Item(int id, String name, String category, String size, double price) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.size = size;
            this.price = price;
        }

        public int getId() {
            return id;
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
        for (int i = 1; i <= 10; i++) {
            items.add(new Item(i, "Wishlist Item " + i, "Category " + ((i % 3) + 1), "Size " + ((i % 5) + 1), 50.0 * i));
        }
        return items;
    }
}
