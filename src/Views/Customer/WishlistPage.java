package Views.Customer;

import Managers.ViewManager;
import Models.WishlistModel;
import Utils.Response;
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

public class WishlistPage implements Page {
    private ViewManager viewManager;
    private StackPane root;

    private HBox navBar;
    private Button menuButton;
    private ContextMenu menuContext;
    private MenuItem homeMenu;
    private MenuItem offerMenu;
    private MenuItem transactionHistoryMenu;
    private MenuItem signOutMenu;

    private VBox mainLayout;
    private TableView<WishlistModel> wishlistTable;

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
        homeMenu = new MenuItem("Homepage");
        transactionHistoryMenu = new MenuItem("Purchase History");
        offerMenu = new MenuItem("Offer");
        signOutMenu = new MenuItem("Sign Out");
        menuContext.getItems().addAll(homeMenu, transactionHistoryMenu, offerMenu, signOutMenu);

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        navBar.getChildren().addAll(menuButton, spacer);

        mainLayout = new VBox();
        mainLayout.setSpacing(10);
        mainLayout.setPadding(new Insets(10));

        wishlistTable = new TableView<WishlistModel>();
        wishlistTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 

        TableColumn<WishlistModel, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<WishlistModel, String> nameColumn = new TableColumn<>("Item Name");
        TableColumn<WishlistModel, String> categoryColumn = new TableColumn<>("Item Category");
        TableColumn<WishlistModel, String> sizeColumn = new TableColumn<>("Item Size");
        TableColumn<WishlistModel, BigDecimal> priceColumn = new TableColumn<>("Item Price");
        TableColumn<WishlistModel, Void> actionColumn = new TableColumn<>("Action");
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("wishlist_id"));
        
        nameColumn.setCellValueFactory(cellData -> {
        	WishlistModel wishlist = cellData.getValue();
            String itemName = wishlist.product().item().getItem_name();
            return new SimpleStringProperty(itemName);
        });
        
        categoryColumn.setCellValueFactory(cellData -> {
        	WishlistModel wishlist = cellData.getValue();
            String itemCategory = wishlist.product().item().getItem_category();
            return new SimpleStringProperty(itemCategory);
        });
        
        sizeColumn.setCellValueFactory(cellData -> {
        	WishlistModel wishlist = cellData.getValue();
            String itemSize = wishlist.product().item().getItem_size();
            return new SimpleStringProperty(itemSize);
        });
        
        priceColumn.setCellValueFactory(cellData -> {
        	WishlistModel wishlist = cellData.getValue();
            BigDecimal itemPrice = wishlist.product().item().getItem_price();
            return new SimpleObjectProperty<>(itemPrice);
        });
        
        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button removeButton = new Button("Remove");

            {
                removeButton.setOnAction(event -> {
                    WishlistModel wishlist = getTableView().getItems().get(getIndex());
                    showConfirmationWishlist("Remove Confirmation",
                            "Are you sure you want to remove " + wishlist.product().item().getItem_name() + " from the wishlist?",
                            wishlist);
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

        wishlistTable.getColumns().addAll(idColumn, nameColumn, categoryColumn, sizeColumn, priceColumn, actionColumn);
        wishlistTable.setPrefHeight(400);
        refreshTable();
        mainLayout.getChildren().addAll(wishlistTable);
    }
    
    private void refreshTable() {
    	ArrayList<WishlistModel> wishlists = WishlistModel.ViewWishlist(viewManager.getUser().getUser_id()).getData();
    	ObservableList<WishlistModel> observableItems = FXCollections.observableArrayList(wishlists);
    	wishlistTable.setItems(observableItems);
    }	
    
    private void showConfirmationWishlist(String title, String content, WishlistModel wishlist) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle(title);
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText(content);

        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
            	Response<WishlistModel> res = WishlistModel.RemoveWishlist(wishlist.getWishlist_id());
            	refreshTable();
            	
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

        offerMenu.setOnAction(e -> {
        	viewManager.changePage(new OfferPage(viewManager).getPage());
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
}
