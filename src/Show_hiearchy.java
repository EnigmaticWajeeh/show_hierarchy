import java.io.File;
import java.io.IOException;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;



import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class Show_hiearchy  extends Application {
	 static int level_file = 0;   
			
	public static void main(String[] args) {
		launch(args);	
	}
	 public static String getFileExtension(String  fileName) {
	        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
	        return fileName.substring(fileName.lastIndexOf(".")+1);
	        else return "";
	    }
	 /*=====================================================================================================================
	  * ======================================================================================================================
	  * ======================================================================================================================
	  * =====================================================================================================================
	  * ======================================================================================================================
	  * ====================================================================================================================*/
	 //method call when double click on folder next level of hiearchy will show 
	 public  void  file_show_with_hiearchy(ListView<Label> list_view_to_show_file,int i ,ObservableList<Label> items_to_show_file,GridFS photo){
		 Image text_file = new Image(getClass().getResourceAsStream("text_file.png"));
			Image pdf = new Image(getClass().getResourceAsStream("pdf.png"));
			Image folder = new Image(getClass().getResourceAsStream("folder.png"));
		 DBObject query = new BasicDBObject().append("metadata.level",level_file);
		    List<GridFSDBFile> files = photo.find(query);
	        for (GridFSDBFile file : files) {        
	        	System.out.println(file.getFilename());
	        	System.out.println(file.getMetaData());
	        	String type = file.getMetaData().get("type").toString();
	        	//System.out.println(type);
	        	
	        	if(type.equals("file")){
	        		System.out.println("Its a file");
	        		 String extension_of_file = getFileExtension(file.getFilename());
	            	 Label lbl = new Label();
	         		lbl.setText(file.getFilename());
	         		lbl.setContentDisplay(ContentDisplay.LEFT); //text will be displayed left to icon
	         		lbl.setGraphicTextGap(10.2);  //Distance between grahpics icon and Text 
	         		lbl.setOnMouseClicked(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent mouseEvent) {
							if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
								if(mouseEvent.getClickCount() == 2 ){
									System.out.println("Click on the file");
								}
															
								}														// TODO Auto-generated method stub							
						}
					});
	            	 if(extension_of_file.equals("pdf")){
	            		 lbl.setGraphic(new ImageView(pdf));
	            	 }
		 
	            	 else if  (extension_of_file.equals("txt")){
	            		 lbl.setGraphic(new ImageView(text_file));
	            	 }
	            	 items_to_show_file.addAll(lbl);
	                 	} //end of if file
	        	else if(type.equals("folder")){
	        		 Label lbl = new Label();
					 
	          		lbl.setText(file.getFilename());
	          		lbl.setGraphic(new ImageView(folder));
	          		lbl.setContentDisplay(ContentDisplay.LEFT); //text will be displayed left to icon
	          		lbl.setGraphicTextGap(10.2); 
	          		items_to_show_file.addAll(lbl);
	           	 list_view_to_show_file.setItems(items_to_show_file);
	           	 lbl.setOnMouseClicked(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent mouseEvent) {
							if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
								if(mouseEvent.getClickCount() == 2 ){
									list_view_to_show_file.getItems().clear();
									level_file = level_file + 1;
									if(level_file < i){
										System.out.println(level_file);
										list_view_to_show_file.getItems().clear();
										file_show_with_hiearchy(list_view_to_show_file,i,items_to_show_file,photo);
									}
									else{
										System.out.println("End of File Hiearcgu");
									} 									
								}
							}							// TODO Auto-generated method stub							
						}
					}); //end of mouse event
	        		System.out.println("Its a folder");        		
	        	}//end of folder	       
	        }//end of for loop
	 }

	 //End of method  method call when double click on folder next level of hiearchy will show 
	 /*=====================================================================================================================
	  * ======================================================================================================================
	  * ======================================================================================================================
	  * =====================================================================================================================
	  * ======================================================================================================================
	  * ====================================================================================================================*/
	 
	 
	 
	 //Start of our application ============================================================================================
	 /*===================================================================================================================
	  * ===================================================================================================================
	  * ===================================================================================================================*/
	@Override
	public void start(Stage stage) throws Exception {
		Image text_file = new Image(getClass().getResourceAsStream("text_file.png"));
		Image pdf = new Image(getClass().getResourceAsStream("pdf.gif"));
		Image folder = new Image(getClass().getResourceAsStream("folder.png"));
		ListView<Label> list_view_to_show_file = new ListView<Label>();
		ObservableList<Label> items_to_show_file = FXCollections.observableArrayList();	
		 Mongo mongo = new MongoClient("localhost",27017);	
			DB db1 = mongo.getDB("fyp1");
		    GridFS photo = new GridFS(db1,"photo"); 
	    String current_path = "/";	   	 
	    int i = 3;
	    DBObject query = new BasicDBObject().append("metadata.level",level_file);
	    List<GridFSDBFile> files = photo.find(query);
 
	    //this loop on same level get all files and folder  ==============================================================
	    /*================================================================================================================
	     * ==============================================================================================================*/
        for (GridFSDBFile file : files) {        
        	System.out.println(file.getFilename());
        	System.out.println(file.getMetaData());
        	String type = file.getMetaData().get("type").toString();
        	if(type.equals("file")){
        		System.out.println("Its a file");
        		 String extension_of_file = getFileExtension(file.getFilename());
            	 Label lbl = new Label();
         		lbl.setText(file.getFilename());
         		lbl.setContentDisplay(ContentDisplay.LEFT); //text will be displayed left to icon
         		lbl.setGraphicTextGap(10.2);  //Distance between grahpics icon and Text 
         		lbl.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent mouseEvent) {
						if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
							
							//double click on the file it will open in local filesyatem temporaray then delete also
							if(mouseEvent.getClickCount() == 2 ){
								//DBObject update_file = new BasicDBObject().append("metadata.level",0).append("metadata.path", " /Cover letter.txt");
								DBObject update_file = new BasicDBObject().append("filename", file.getFilename()).append("metadata.level", level_file);
								GridFSDBFile imageForOutput = photo.findOne(update_file);
								try {
									System.out.println("File Name= "+ file.getFilename() + "  File Meta Data= " +file.getMetaData() + " File upload Date = " + file.getUploadDate() );
									
									imageForOutput.writeTo("C:\\ali1.txt");
									Process p = Runtime.getRuntime().exec("notepad C:\\ali1.txt");
									if( p.waitFor() == 0){																		
										System.out.println(" exit" );
										 File imageFile = new File("C:\\ali1.txt");
										imageFile.delete();   //deletec temprary file
									System.out.println(imageForOutput);
									}
								} catch (IOException e) {
									
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								System.out.println("Click on the file");
								}  // end of the function to open in local 
							
														
							}														// TODO Auto-generated method stub							
					}
				});
            	 if(extension_of_file.equals("pdf")){
            		 lbl.setGraphic(new ImageView(pdf));
            	 }	 
            	 else if  (extension_of_file.equals("txt")){
            		 lbl.setGraphic(new ImageView(text_file));
            	 }
            	 items_to_show_file.addAll(lbl);
            	 list_view_to_show_file.setItems(items_to_show_file);
        	} //end of if file
        	else if(type.equals("folder")){
        		 Label lbl = new Label();
				 
          		lbl.setText(file.getFilename());
          		lbl.setGraphic(new ImageView(folder));
          		lbl.setContentDisplay(ContentDisplay.LEFT); //text will be displayed left to icon
          		lbl.setGraphicTextGap(10.2); 
          		items_to_show_file.addAll(lbl);
           	 list_view_to_show_file.setItems(items_to_show_file);
           	 lbl.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent mouseEvent) {
						if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
							if(mouseEvent.getClickCount() == 2 ){
								list_view_to_show_file.getItems().clear();
								level_file = level_file + 1;
								if(level_file < i){
									System.out.println(level_file);
									list_view_to_show_file.getItems().clear();
									file_show_with_hiearchy(list_view_to_show_file,i,items_to_show_file,photo);
								}
								else{
									System.out.println("End of File Hiearcgu");
								} 								
							}
						}					
					}
				}); //end of mouse event
        		System.out.println("Its a folder");        		
        	}//end of folder
        }//end of for loop	    
		Group root = new Group();
		root.getChildren().addAll(list_view_to_show_file);
		Scene scene = new Scene(root,500,500);
		stage.setScene(scene);
		stage.show();
		
		
	} //start application method end here 

}
