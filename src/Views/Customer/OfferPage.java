package Views.Customer;

import java.math.BigDecimal;
import java.util.ArrayList;

import Controllers.ItemController;
import Managers.ViewManager;
import Models.OfferModel;
import Utils.Response;
import Views.LoginPage;
import Views.Page;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class OfferPage implements Page {
	private ViewManager viewManager;
	private StackPane root;

	private HBox navBar;
	private Button menuButton;
	private ContextMenu menuContext;
	private MenuItem homeMenu;
	private MenuItem transactionHistoryMenu;
	private MenuItem wishlistMenu;
	private MenuItem signOutMenu;

	private VBox mainLayout;
	private TableView<OfferModel> offerTable;

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

		navBar = new HBox();
		navBar.setPadding(new Insets(10));
		navBar.setSpacing(10);
		navBar.setStyle("-fx-background-color: #333;");

		menuButton = new Button("Menu");
		menuButton.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
		navBar.getChildren().add(menuButton);

		menuContext = new ContextMenu();
		homeMenu = new MenuItem("Home");
		wishlistMenu = new MenuItem("Wishlist");
		transactionHistoryMenu = new MenuItem("Purchase History");
		signOutMenu = new MenuItem("Sign Out");
		menuContext.getItems().addAll(homeMenu, wishlistMenu, transactionHistoryMenu, signOutMenu);

		mainLayout = new VBox(10);
		mainLayout.setPadding(new Insets(10));

		Label titleLabel = new Label("Offered Items");
		titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

		offerTable = new TableView<>();
		offerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<OfferModel, String> idColumn = new TableColumn<>("ID");
		TableColumn<OfferModel, String> nameColumn = new TableColumn<>("Item Name");
		TableColumn<OfferModel, String> categoryColumn = new TableColumn<>("Item Category");
		TableColumn<OfferModel, String> sizeColumn = new TableColumn<>("Item Size");
		TableColumn<OfferModel, BigDecimal> priceColumn = new TableColumn<>("Item Price");
		TableColumn<OfferModel, String> offeredPriceColumn = new TableColumn<>("Offered Price");
		TableColumn<OfferModel, Void> actionColumn = new TableColumn<>("Action");

		idColumn.setCellValueFactory(new PropertyValueFactory<>("offer_id"));
		nameColumn.setCellValueFactory(cellData -> {
			OfferModel offer = cellData.getValue();
			String itemName = offer.product().item().getItem_name();
			return new SimpleStringProperty(itemName);
		});

		priceColumn.setCellValueFactory(cellData -> {
			OfferModel offer = cellData.getValue();
			BigDecimal itemPrice = offer.product().item().getItem_price();
			return new SimpleObjectProperty<>(itemPrice);
		});

		categoryColumn.setCellValueFactory(cellData -> {
			OfferModel offer = cellData.getValue();
			String category = offer.product().item().getItem_category();
			return new SimpleStringProperty(category);
		});

		sizeColumn.setCellValueFactory(cellData -> {
			OfferModel offer = cellData.getValue();
			String size = offer.product().item().getItem_size();
			return new SimpleStringProperty(size);
		});
		offeredPriceColumn.setCellValueFactory(new PropertyValueFactory<>("item_offer_price"));

		actionColumn.setCellFactory(col -> {
			TableCell<OfferModel, Void> cell = new TableCell<>() {
				private final Button offerButton = new Button("Offer");
				private final HBox hb = new HBox(offerButton);
				{
					offerButton.setOnAction(event -> {
						OfferModel offer = getTableView().getItems().get(getIndex());
						String itemName = offer.product().item().getItem_name();
						showOfferInputDialog("Offer Input", "Enter your offer price for " + itemName + ":", offer);
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
		offerTable.getColumns().addAll(idColumn, nameColumn, categoryColumn, sizeColumn, priceColumn,
				offeredPriceColumn, actionColumn);
		refreshTable();
		mainLayout.getChildren().addAll(titleLabel, offerTable);
	}

	public void setLayout() {
		VBox wrapper = new VBox(navBar, mainLayout);
		root.getChildren().add(wrapper);
		root.setStyle("-fx-background-color: #f4f4f4;");
	}

	public void setEvent() {
		menuButton.setOnAction(e -> {
			menuContext.show(menuButton, menuButton.localToScreen(0, menuButton.getHeight()).getX(),
					menuButton.localToScreen(0, menuButton.getHeight()).getY());
		});

		homeMenu.setOnAction(e -> {
			viewManager.changePage(new DashboardPage(viewManager).getPage());
		});

		wishlistMenu.setOnAction(e -> {
			viewManager.changePage(new WishlistPage(viewManager).getPage());
		});

		transactionHistoryMenu.setOnAction(e -> {
			viewManager.changePage(new HistoryPage(viewManager).getPage());
		});

		signOutMenu.setOnAction(e -> {
			viewManager.logout();
			viewManager.changePage(new LoginPage(viewManager).getPage());
		});
	}

	private void refreshTable() {
		ArrayList<OfferModel> products = ItemController.ViewOfferedItem(viewManager.getUser().getUser_id()).getData();
		ObservableList<OfferModel> observableItems = FXCollections.observableArrayList(products);
		offerTable.setItems(observableItems);
	}

	private void showOfferInputDialog(String title, String content, OfferModel offer) {
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
			String offers = offerField.getText().trim();
			Response<OfferModel> res = ItemController.OfferPrice(offer.product().getProduct_id(),
					viewManager.getUser().getUser_id(), offers);

			if (res.getIsSuccess()) {
				showInfoAlert("Info", res.getMessages() + "Product Offered: " + offerField.getText());
				refreshTable();
				offerStage.close();
			} else {
				errorLabel.setText(res.getMessages());
			}
		});

		offerLayout.getChildren().addAll(offerTitleLabel, offerField, errorLabel, submitButton);
		Scene offerScene = new Scene(offerLayout, 300, 200);
		offerStage.setScene(offerScene);
		offerStage.setTitle("Offer Product");
		offerStage.show();
	}

	private void showInfoAlert(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
