package Views.Seller;

import Managers.ViewManager;
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

    // UI Components
    private TextField itemNameField, itemCategoryField, itemSizeField, itemPriceField;
    private TableView<String> itemsTable;
    private Label errorMessage;

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
        TableColumn<String, String> categoryColumn = new TableColumn<>("Category");
        TableColumn<String, String> sizeColumn = new TableColumn<>("Size");
        TableColumn<String, String> priceColumn = new TableColumn<>("Price");

        itemsTable.getColumns().addAll(idColumn, nameColumn, categoryColumn, sizeColumn, priceColumn);

        // Initialize error message
        errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red;");
    }

    @Override
    public void setLayout() {
        VBox mainLayout = new VBox();
        mainLayout.setSpacing(10);
        mainLayout.setPadding(new Insets(10));

        // Upload Item Form
        Label titleLabel = new Label("Upload Item");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        GridPane uploadForm = new GridPane();
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
        Label tableLabel = new Label("Uploaded Items");
        tableLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button editButton = new Button("EDIT");
        Button deleteButton = new Button("DELETE");
        HBox actionButtons = new HBox(10, editButton, deleteButton);
        actionButtons.setAlignment(Pos.CENTER);

        // Main Layout
        mainLayout.getChildren().addAll(titleLabel, uploadForm, tableLabel, itemsTable, actionButtons);

        // Root
        root.getChildren().add(mainLayout);
        root.setStyle("-fx-background-color: #f4f4f4;");
    }

    @Override
    public void setEvent() {
        // Save Button Action
        Button saveButton = (Button) ((GridPane) ((VBox) root.getChildren().get(0)).getChildren().get(1)).getChildren().get(8);
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

            // Add new item to the table (you should replace this with saving to database logic)
            String newItem = String.join(", ", itemName, itemCategory, itemSize, itemPrice); // Placeholder
            itemsTable.getItems().add(newItem);

            errorMessage.setText("Item uploaded successfully!");
        });

        // Edit Button Action
        Button editButton = (Button) ((HBox) ((VBox) root.getChildren().get(0)).getChildren().get(4)).getChildren().get(0);
        editButton.setOnAction(e -> {
            String selectedItem = itemsTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                openEditModal(selectedItem);
            } else {
                showErrorAlert("No Item Selected", "Please select an item to edit.");
            }
        });

        // Delete Button Action
        Button deleteButton = (Button) ((HBox) ((VBox) root.getChildren().get(0)).getChildren().get(4)).getChildren().get(1);
        deleteButton.setOnAction(e -> {
            String selectedItem = itemsTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation");
                confirmationAlert.setHeaderText("Are you sure you want to delete this item?");
                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        // Remove the selected item from the table (replace with delete logic)
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

        // Split selected item to populate fields (assuming it's a comma-separated value)
        String[] itemDetails = selectedItem.split(", ");
        TextField editNameField = new TextField(itemDetails[0]);
        TextField editCategoryField = new TextField(itemDetails[1]);
        TextField editSizeField = new TextField(itemDetails[2]);
        TextField editPriceField = new TextField(itemDetails[3]);

        Button saveEditButton = new Button("SAVE");
        saveEditButton.setOnAction(event -> {
            // Validate and update the item
            String updatedItem = String.join(", ", 
                editNameField.getText(), 
                editCategoryField.getText(), 
                editSizeField.getText(), 
                editPriceField.getText()
            );

            int selectedIndex = itemsTable.getSelectionModel().getSelectedIndex();
            itemsTable.getItems().set(selectedIndex, updatedItem); // Update the item in the table

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

    @Override
    public StackPane getPage() {
        return root;
    }
}
