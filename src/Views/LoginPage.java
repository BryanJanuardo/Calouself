package Views;

import Managers.ViewManager;
import Views.Seller.DashboardPage;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoginPage implements Page{
	private ViewManager viewManager;
	private StackPane root;
	private Button testBtn;
	private Button testBtn2;

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
		testBtn = new Button("test");
		testBtn2 = new Button("test2");

	}
	
	@Override
	public void setLayout() {
		vbox.getChildren().addAll(registerBtn, testBtn, testBtn2);
		
		root.getChildren().add(vbox);
		
		
	}
	
	@Override
	public void setEvent() {
		registerBtn.setOnAction(e -> registerPage());
		testBtn.setOnAction(e -> testPage());
		testBtn2.setOnAction(e -> testPage2());
		
	}
	
	public void registerPage() {
		viewManager.changePage(new RegisterPage(viewManager).getPage());
	}
	public void testPage() {
		viewManager.changePage(new Views.Seller.DashboardPage(viewManager).getPage());
	}

	public void testPage2() {
		viewManager.changePage(new Views.Admin.DashboardPage(viewManager).getPage());
	}

	@Override
	public StackPane getPage() {
		return root;
	}
	
}
