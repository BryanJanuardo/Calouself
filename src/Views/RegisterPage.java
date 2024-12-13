package Views;

import Managers.ViewManager;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class RegisterPage implements Page{
	private ViewManager viewManager;
	private StackPane root;

	private VBox vbox;
	private Button loginBtn;
	
	public RegisterPage(ViewManager viewManager) {
		this.viewManager = viewManager;
		this.root = new StackPane();
		init();
		setLayout();
		setEvent();
	}

	@Override
	public void init() {
		vbox = new VBox(10);
		loginBtn = new Button("Login");
		
	}
	
	@Override
	public void setLayout() {
		vbox.getChildren().addAll(loginBtn);
		
		root.getChildren().add(vbox);
	}
	
	@Override
	public void setEvent() {
		loginBtn.setOnAction(e -> loginPage());
		
	}
	
	public void loginPage() {
		viewManager.changePage(new LoginPage(viewManager).getPage());
	}
	
	@Override
	public StackPane getPage() {
		return root;
	}
}
