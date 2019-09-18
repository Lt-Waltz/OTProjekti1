package Ryhma7.ULI_9000;

import java.io.IOException;

import Ryhma7.ULI_9000.controller.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class App extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	public App() {
		
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Uli-9000");

		initRootLayout();
		
		showStorageLayout();
		
	}
	
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/Rootlayout.fxml"));
		
			this.rootLayout =(BorderPane) loader.load();
			Scene scene = new Scene(this.rootLayout);
			scene.getStylesheets().add("StoragePalette.css");
			
			this.primaryStage.setScene(scene);
			
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);
			
			this.primaryStage.show();
				
			
		}catch(IOException e) {
			System.out.println("Noworks");
		}
	}
	
	public void showStorageLayout() {
		try {
			int height = 300;
			int width = 600;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/StorageLayout.fxml"));
			BorderPane page = (BorderPane) loader.load();
			GridPane grid = new GridPane();
			grid.getStyleClass().add("storage-grid");
			for (int i = 0;i<15;i++) {
				ColumnConstraints column = new ColumnConstraints(30);
				grid.getColumnConstraints().add(column);
			}
			for (int i = 0; i<10; i++) {
				RowConstraints row = new RowConstraints(30);
				grid.getRowConstraints().add(row);
			}
			for (int i = 0; i<15;i++) {
				for(int j = 0; j<10; j++) {
					/*Rectangle rectangle = new Rectangle();
					Color paint = Color.BLACK;
					rectangle.setFill(paint);
					grid.add(rectangle, i, j);
					System.out.println("Rectangle added");*/
					Pane pane = new Pane();
					pane.getStyleClass().add("storage-grid-cell");
					grid.add(pane, i, j);
				}
			}
			//Stage storageStage = new Stage();
			//storageStage.setTitle("Storage Layout");
			//storageStage.initModality(Modality.WINDOW_MODAL);
			//storageStage.initOwner(primaryStage);
			//Scene scene = new Scene(page);
			//storageStage.setScene(scene);
			
			//storageStage.show();
			page.setCenter(grid);
			this.rootLayout.setCenter(page);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[]args) {
		launch();
	}
}