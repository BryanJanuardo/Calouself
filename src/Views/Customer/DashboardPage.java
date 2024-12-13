package Views.Customer;

import Managers.ViewManager;
import Views.Page;
import javafx.scene.layout.StackPane;

public class DashboardPage implements Page{
	private ViewManager viewManager;
	private StackPane root;

	public DashboardPage(ViewManager viewManager) {
		this.viewManager = viewManager;
		this.root = new StackPane();
		init();
		setLayout();
		setEvent();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setLayout() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setEvent() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public StackPane getPage() {
		return root;
	}
}
