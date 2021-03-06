package Ryhma7.ULI_9000;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import Ryhma7.ULI_9000.controller.RootLayoutController;
import Ryhma7.ULI_9000.controller.NewItemDialogController;
import Ryhma7.ULI_9000.controller.NewStorageDialogController;
import Ryhma7.ULI_9000.controller.PopupController;
import Ryhma7.ULI_9000.controller.StorageController;
import Ryhma7.ULI_9000.model.Item;
import Ryhma7.ULI_9000.model.Shelf;
import Ryhma7.ULI_9000.model.Storage;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**Main application
 *
 */
public class App extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private ArrayList<Storage> storages;
	private ResourceBundle bundle;
	
	public App() {
	}

	/**Initial setup for the program
	 * 
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("ULI-9000");
		this.primaryStage.getIcons().add(new Image("file:src/main/resources/ULI9000Logo.png"));

		this.storages = new ArrayList<Storage>();
		
		selectLanguage();
		
		initRootLayout();
	}
	
	/**Brings the Storage Layout of a specific storage to center of primaryStage
	 * @param storage is the storage to be displayed
	 */
	public void showStorageLayout(Storage storage) {
		StorageController controller = null;
		AnchorPane page = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/StorageLayout.fxml"));
			loader.setResources(bundle);
			
			page = (AnchorPane) loader.load();
			this.rootLayout.setCenter(page);
			controller = loader.getController();
			
			controller.setMainApp(this);
			controller.setStorage(storage);
			controller.setPane(page);
			controller.loadStorageLayout();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**Opens ShelfPopup Modal window
	 * 
	 * @param shelf 
	 * @param coordinateX
	 * @param coordinateY
	 * @return false
	 */
	public boolean showInfoBox(Shelf shelf, double coordinateX, double coordinateY) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/ShelfPopup.fxml"));
			loader.setResources(bundle);
			
			BorderPane popup = (BorderPane) loader.load();
			Stage popupStage = new Stage();
			
			
			popupStage.setX(coordinateX);
			popupStage.setY(coordinateY);
			popupStage.initStyle(StageStyle.TRANSPARENT);
			popupStage.initModality(Modality.NONE);
			popupStage.initOwner(primaryStage);
			popupStage.focusedProperty().addListener(new ChangeListener<Object>() {

				@Override
				public void changed(ObservableValue<?> obs, Object oldValue, Object newValue) {
					if(!((boolean)newValue)) {
						popupStage.close();
					}				
				}				
			});
			
			Scene scene = new Scene(popup);
			scene.getStylesheets().add("StoragePalette.css");
			scene.setFill(Color.TRANSPARENT);
			popupStage.setScene(scene);
			PopupController  controller = loader.getController();
			controller.setPopupStage(popupStage);
			controller.setShelf(shelf);
			popupStage.show();
			popup.getStyleClass().add("info-box");
		}catch(IOException e) {
			System.out.println(e);
		}
		return false;
	}
	
	/**Opens NewStorage Modal window
	 * @param storage
	 * @return true if new storage is created, false if not
	 */
	public boolean showNewStorageDialog(Storage storage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/NewStorageDialog.fxml"));
			loader.setResources(bundle);
			
			AnchorPane page = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle(bundle.getString("windowtitle.storage"));
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			NewStorageDialogController controller = loader.getController();
			controller.setMainApp(this);
			controller.setStorage(storage);
			controller.setDialogStage(dialogStage);
			
			dialogStage.showAndWait();
			
			return controller.getIsOkClicked();			
		}catch(IOException e) {
			System.out.println(e);
			return false;
		}
	}
	
	/**Opens new Item modal window
	 * @param item
	 * @return true if new item is created, false if not
	 */
	public boolean showNewItemDialog(Item item) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/NewItemDialog.fxml"));
			loader.setResources(bundle);
			
			AnchorPane page = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle(bundle.getString("windowtitle.item"));
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			NewItemDialogController controller = loader.getController();
			controller.setMainApp(this);
			controller.setItem(item);
			controller.setDialogStage(dialogStage);
			
			dialogStage.showAndWait();
			
			return controller.getIsOkClicked();			
		}catch(IOException e) {
			System.out.println(e);
			return false;
		}
	}
	
	/** Initializes the root layout
	 * 
	 */
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/RootLayout.fxml"));
			loader.setResources(bundle);
			
			this.rootLayout =(BorderPane) loader.load();
			Scene scene = new Scene(this.rootLayout);
			VBox vbox =(VBox) this.rootLayout.getChildren().get(0);
			scene.getStylesheets().add("StoragePalette.css");
			
			this.primaryStage.setScene(scene);
			
			RootLayoutController controller = loader.getController();
			
			controller.setMainApp(this);
			controller.setStorages(this.storages);
			controller.setVBox(vbox);
			
			controller.loadStorages(vbox, bundle);
			
			this.primaryStage.show();
				
			
		}catch(IOException e) {
			System.out.println(e);
		}
	}
	
	/**clears the center of border pane
	 * 
	 */
	public void clearCenterPane() {
		BorderPane page = new BorderPane();
		this.rootLayout.setCenter(page);	
	}
	
	/**Selects the language based on the language properties files, and creates a resource bundle based on it.
	 * 
	 */
	public void selectLanguage() {
		Locale curLocale;
		String appConfigPath = "src/main/resources/language.properties";
		Properties appProps = new Properties();
		try {
			appProps.load(new FileInputStream(appConfigPath));
			String language = appProps.getProperty("language");
			String country  = appProps.getProperty("country");
			curLocale = new Locale(language, country);
		} catch (Exception e) {
			curLocale = new Locale("en", "GB");
		}
		try {
			bundle = ResourceBundle.getBundle("TextResources", curLocale);
		} catch (Exception e) {
			System.err.println("Can't find TextResources.properties file");
			System.exit(0);
		}
	}
	
	/**Returns storages-List
	 * @return storages
	 */
	public ArrayList<Storage> getStorages() {
		return storages;
	}
	
	/**
	 * Returns the BorderPane rootLayout
	 * @return rootLayout
	 */
	public BorderPane getRootLayout() {
		return this.rootLayout;
	}
	
	/**
	 * Returns the Stage primaryStage
	 * @return primaryStage
	 */
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
	
	/**
	 * Returns the ResourceBundle bundle
	 * @return bundle
	 */
	public ResourceBundle getResourceBundle() {
		return bundle;
	}
	
	/**Launches the program
	 * @param args
	 */
	public static void main(String[]args) {
		launch();
	}
}
