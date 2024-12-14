package Views.Seller;

import Managers.ViewManager;
import Views.LoginPage;
import Views.Page;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DashboardPage implements Page {
    private ViewManager viewManager;
    private StackPane root;

    // Navbar
    private HBox navBar;
    private Button menuButton;
    
    private ContextMenu menuContext; 
    private MenuItem offeredItemMenu; 
    private MenuItem signOutMenu;

    // Main layout (isi konten utama)
    private VBox mainLayout;

    // UI Components (untuk upload item)
    private TextField itemNameField, itemCategoryField, itemSizeField, itemPriceField;
    private TableView<String> itemsTable;
    private Label errorMessage;

    // Komponen untuk upload form & tabel
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
        // Initialize form inputs
        itemNameField = new TextField();
        itemCategoryField = new TextField();
        itemSizeField = new TextField();
        itemPriceField = new TextField();
        

        // Initialize table
        itemsTable = new TableView<>();
        TableColumn<String, String> idColumn = new TableColumn<>("ID");
        TableColumn<String, String> nameColumn = new TableColumn<>("Item Name");
        TableColumn<String, String> categoryColumn = new TableColumn<>("Item Category");
        TableColumn<String, String> sizeColumn = new TableColumn<>("Item Size");
        TableColumn<String, String> priceColumn = new TableColumn<>("Item Price");

        itemsTable.getColumns().addAll(idColumn, nameColumn, categoryColumn, sizeColumn, priceColumn);

        // Initialize error message
        errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red;");

        // Buat komponen upload form
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

        // Table View for Items
        tableLabel = new Label("Uploaded Items");
        tableLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        editButton = new Button("EDIT");
        deleteButton = new Button("DELETE");
        actionButtons = new HBox(10, editButton, deleteButton);
        actionButtons.setAlignment(Pos.CENTER);

        // Navbar
        navBar = new HBox();
        navBar.setPadding(new Insets(10));
        navBar.setSpacing(10);
        navBar.setStyle("-fx-background-color: #333;");

        menuButton = new Button("Menu");
        menuButton.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
        navBar.getChildren().add(menuButton);

        // ContextMenu untuk menu
        menuContext = new ContextMenu();
        offeredItemMenu = new MenuItem("Offered Item");
        signOutMenu = new MenuItem("Sign Out");
        menuContext.getItems().addAll(offeredItemMenu, signOutMenu);

        // Main layout (konten utama)
        mainLayout = new VBox();
        mainLayout.setSpacing(10);
        mainLayout.setPadding(new Insets(10));

        // Awal tampilkan form upload item dan tabel di halaman utama
        mainLayout.getChildren().addAll(titleLabel, uploadForm, tableLabel, itemsTable, actionButtons);
    }

    @Override
    public void setLayout() {
        // Bungkus navbar dan mainLayout dalam satu VBox
        VBox wrapper = new VBox();
        wrapper.getChildren().addAll(navBar, mainLayout);

        root.getChildren().add(wrapper);
        root.setStyle("-fx-background-color: #f4f4f4;");
    }

    @Override
    public void setEvent() {
        // Saat tombol Menu di-klik, tampilkan context menu di bawah tombol menu
        menuButton.setOnAction(e -> {
            menuContext.show(menuButton, 
                menuButton.localToScreen(0, menuButton.getHeight()).getX(),
                menuButton.localToScreen(0, menuButton.getHeight()).getY()
            );
        });

        // Saat offered item di klik, saat ini hanya menampilkan alert "belum dibuat"
        offeredItemMenu.setOnAction(e -> {
            viewManager.switchPage(new OfferPage(viewManager));
        });
        
        signOutMenu.setOnAction(e -> {
            viewManager.logout();
            viewManager.changePage(new LoginPage(viewManager).getPage());
        });

        // Save Button Action
        Button saveButton = (Button) uploadForm.getChildren().filtered(node -> node instanceof Button && ((Button) node).getText().equals("SAVE")).get(0);
        saveButton.setOnAction(e -> {
            String itemName = itemNameField.getText();
            String itemCategory = itemCategoryField.getText();
            String itemSize = itemSizeField.getText();
            String itemPrice = itemPriceField.getText();

            // Validation
            if (itemName.isEmpty() || itemCategory.isEmpty() || itemSize.isEmpty() || itemPrice.isEmpty()) {
                errorMessage.setText("All fields must be filled!");
                return;
            }

            if (!itemPrice.matches("\\d+")) {
                errorMessage.setText("Price must be a valid number!");
                return;
            }

            // Add new item to the table (dummy logic)
            String newItem = String.join(", ", itemName, itemCategory, itemSize, itemPrice);
            itemsTable.getItems().add(newItem);

            errorMessage.setText("Item uploaded successfully!");
        });

        // Edit Button Action
        editButton.setOnAction(e -> {
            String selectedItem = itemsTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                openEditModal(selectedItem);
            } else {
                showErrorAlert("No Item Selected", "Please select an item to edit.");
            }
        });

        // Delete Button Action
        deleteButton.setOnAction(e -> {
            String selectedItem = itemsTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation");
                confirmationAlert.setHeaderText("Are you sure you want to delete this item?");
                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        itemsTable.getItems().remove(selectedItem);
                        errorMessage.setText("Item deleted successfully!");
                    }
                });
            } else {
                showErrorAlert("No Item Selected", "Please select an item to delete.");
            }
        });
    }

    private void openEditModal(String selectedItem) {
        Stage editStage = new Stage();
        VBox editLayout = new VBox(10);
        editLayout.setPadding(new Insets(10));

        Label editTitle = new Label("Edit Item");
        editTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Split selected item
        String[] itemDetails = selectedItem.split(", ");
        TextField editNameField = new TextField(itemDetails[0]);
        TextField editCategoryField = new TextField(itemDetails[1]);
        TextField editSizeField = new TextField(itemDetails[2]);
        TextField editPriceField = new TextField(itemDetails[3]);

        Button saveEditButton = new Button("SAVE");
        saveEditButton.setOnAction(event -> {
            String updatedItem = String.join(", ", 
                editNameField.getText(), 
                editCategoryField.getText(), 
                editSizeField.getText(), 
                editPriceField.getText()
            );

            int selectedIndex = itemsTable.getSelectionModel().getSelectedIndex();
            itemsTable.getItems().set(selectedIndex, updatedItem);
            errorMessage.setText("Item updated successfully!");
            editStage.close();
        });

        editLayout.getChildren().addAll(editTitle, editNameField, editCategoryField, editSizeField, editPriceField, saveEditButton);

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
