package Views;

import Managers.ViewManager;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoginPage implements Page{
	private ViewManager viewManager;
	private StackPane root;

	private VBox vbox;
	private Button registerBtn;
	
	public LoginPage(ViewManager viewManager) {
		this.viewManager = viewManager;
		this.root = new StackPane();
		init();
		setLayout();
		setEvent();
	}

	@Override
	public void init() {
		vbox = new VBox(10);
		registerBtn = new Button("Register");

	}
	
	@Override
	public void setLayout() {
		vbox.getChildren().addAll(registerBtn);
		
		root.getChildren().add(vbox);
	}
	
	@Override
	public void setEvent() {
		registerBtn.setOnAction(e -> registerPage());
		
	}
	
	public void registerPage() {
		viewManager.changePage(new RegisterPage(viewManager).getPage());
	}

	@Override
	public StackPane getPage() {
		return root;
	}
	
}
