/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;
import Utils.database;
import Entite.*;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 *
 * @author MarcoMan
 * Youtube Channel: https://www.youtube.com/channel/UCPgcmw0LXToDn49akUEJBkQ
    Subscribe our Channel, thank you for the support!
 */
public class FXMLDocumentController implements Initializable {

    private double x = 0;
    private double y = 0;
    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginBtn;
    @FXML
    private Button logupBtn;
    @FXML
    private Button close;
    
//    DATABASE TOOLS
    private Connection connection;
    private Statement statement;
    private static FXMLDocumentController instance;

    public static FXMLDocumentController getInstance() {
        if (instance == null) {
            instance = new FXMLDocumentController();
        }
        return instance;
    }

    public FXMLDocumentController() {
        try {
            connection = database.getInstance().getCon();
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void loginAdmin(ActionEvent event) {
        String sql = "SELECT * FROM agences_vehicules.user WHERE Cin = ? and password = ?";

        try (PreparedStatement prepare = connection.prepareStatement(sql)) {
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());

            ResultSet result = prepare.executeQuery();
            Alert alert;
            
            if(username.getText().isEmpty() || password.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                if(result.next()){
                    
                    getData.username = result.getString("Nom");
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();
                    // HIDE YOUR LOGIN FORM
                    loginBtn.getScene().getWindow().hide();

                    Parent root;

                        // LINK YOUR DASHBOARD FORM
                        root= FXMLLoader.load(getClass().getResource("../FXML/dashboard.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    root.setOnMousePressed((MouseEvent event1) ->{
                        x = event1.getSceneX();
                        y = event1.getSceneY();
                    });
                    
                    root.setOnMouseDragged((MouseEvent event1) ->{
                        stage.setX(event1.getScreenX() - x);
                        stage.setY(event1.getScreenY() - y);
                    });
                    
                    stage.initStyle(StageStyle.TRANSPARENT);
                    
                    stage.setScene(scene);
                    stage.show();
                    
                }else{
                    alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                }
            }
        }catch(Exception e){e.printStackTrace();}
        
    }

    public void logUpAdmin(ActionEvent event) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to create an account?");
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if (option.get().equals(ButtonType.OK)) {
                // HIDE YOUR DASHBOARD FORM
                logupBtn.getScene().getWindow().hide();

                // LINK YOUR LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("../FXML/LogUp.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event1) -> {
                    x = event1.getSceneX();
                    y = event1.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event1) -> {
                    stage.setX(event1.getScreenX() - x);
                    stage.setY(event1.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event1) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void close(){
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
