/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;
import Utils.database;
import Entite.*;
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

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author MarcoMan
 * Youtube Channel: https://www.youtube.com/channel/UCPgcmw0LXToDn49akUEJBkQ
    Subscribe our Channel, thank you for the support!
 */
public class LogUPController implements Initializable {

    private double x = 0;
    private double y = 0;
    @FXML
    private AnchorPane main_form;


    @FXML
    private PasswordField password;

    @FXML
    private Button createBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button close;
    @FXML
    private TextField cin;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    //    DATABASE TOOLS
    private Connection connection;
    private static LogUPController instance;

    public static LogUPController getInstance() {
        if (instance == null) {
            instance = new LogUPController();
        }
        return instance;
    }


    public LogUPController() {
        try {
            connect = database.getInstance().getCon();
            statement = connect.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    /*  prepare = connect.prepareStatement(sql);
      prepare.setString(1, availableCars_carId.getText());
      prepare.setString(2, availableCars_brand.getText());
      prepare.setString(3, availableCars_model.getText());
      prepare.setString(4, availableCars_price.getText());
      prepare.setString(5, (String) availableCars_status.getSelectionModel().getSelectedItem());

      String req = "INSERT INTO agences_vehicules.vehicule (Matricule,Type, Model, PrixLocation,status) VALUES ('"
              + availableCars_carId.getText() + "', '" + availableCars_brand.getText() + "', '" + availableCars_model.getText() + "',"
              + availableCars_price.getText() +",'"+(String) availableCars_status.getSelectionModel().getSelectedItem()+"');";
      try {

          prepare = connect.prepareStatement(req);
          int res=prepare.executeUpdate(req);
          // result = prepare.executeQuery();



          alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Information Message");
          System.out.println("Information Message");
          alert.setHeaderText(null);
          alert.setContentText("Successfully Added!");
          System.out.println("Successfully Added!");
          alert.showAndWait();

          availableCarShowListData();
          availableCarClear();
      }catch (SQLException e){
          System.out.println(req+"\n erreur d'ajout "+e);
      }
  }
} catch (Exception e) {
  e.printStackTrace();
}*/
    public void logupAdmin(ActionEvent event) {
        String sql = "INSERT INTO agences_vehicules.user (Cin,Nom,Prenom,password)"
                + "VALUES(?,?,?,?)";


        try {
            Alert alert;

            if (cin.getText().isEmpty()
                    || nom.getText().isEmpty()
                    || prenom.getText().isEmpty()
                    || password.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, cin.getText());
                prepare.setString(2, nom.getText());
                prepare.setString(3, prenom.getText());
                prepare.setString(4, password.getText());

                String req = "INSERT INTO agences_vehicules.user (Cin,Nom,Prenom,password) VALUES ("
                        + cin.getText() + ", '" + nom.getText() + "', '" + prenom.getText() + "','"
                        + password.getText() + "');";
                try {

                    prepare = connect.prepareStatement(req);
                    int res = prepare.executeUpdate(req);
                    // result = prepare.executeQuery();


                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    System.out.println("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Created!");
                    alert.showAndWait();

                    Optional<ButtonType> option = alert.showAndWait();
                    if (option.get().equals(ButtonType.OK)) {
                        // HIDE YOUR DASHBOARD FORM
                        createBtn.getScene().getWindow().hide();

                        // LINK YOUR LOGIN FORM
                        Parent root = FXMLLoader.load(getClass().getResource("../FXML/FXMLDocument.fxml"));
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
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void logout() {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if (option.get().equals(ButtonType.OK)) {
                // HIDE YOUR DASHBOARD FORM
                cancelBtn.getScene().getWindow().hide();

                // LINK YOUR LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("../FXML/FXMLDocument.fxml"));
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

                root.setOnMouseReleased((MouseEvent event) -> {
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
