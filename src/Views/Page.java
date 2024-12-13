package Views;

import javafx.scene.layout.StackPane;

public interface Page {
	public void init();
	public void setLayout();
	public void setEvent();
	public StackPane getPage();
}
