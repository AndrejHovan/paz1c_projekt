package sk.upjs.ed;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;


/**
 * Hello world!
 *
 */
public class App extends Application
{
	public void start(Stage primaryStage) throws Exception {
		HomeController mainController	= new HomeController();            
		FXMLLoader fxmlLoader = new	FXMLLoader(getClass().getResource("Home.fxml"));
		fxmlLoader.setController(mainController);
		Parent rootPane	= fxmlLoader.load();
		
		Scene scene	= new Scene(rootPane);
		primaryStage.setTitle("ED");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
    public static void main( String[] args )
    {
    	launch(args);
    }
    
}

