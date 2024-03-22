package Service;
import Entite.*;
import Utils.*;
import java.net.URL;
import java.sql.*;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
public class dashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    @FXML
    private Button home_btn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button availableCars_btn;
    @FXML
    private Button availableClient_btn;

    @FXML
    private Button rentCar_btn;

    @FXML
    private Label home_availableCars;

    @FXML
    private Label home_totalIncome;

    @FXML
    private Label home_totalCustomers;

    @FXML
    private BarChart<?, ?> home_incomeChart;

    @FXML
    private LineChart<?, ?> home_customerChart;

    @FXML
    private AnchorPane availableCars_form;
    @FXML
    private AnchorPane availableClient_form;

    @FXML
    private TextField availableCars_carId;
    @FXML
    private TextField availableClient_clientId;
    @FXML
    private TextField availableClient_nom;
    @FXML
    private TextField availableClient_prenom;
    @FXML
    private TextField availableClient_adresse;
    @FXML
    private TextField availableClient_phone;

    @FXML
    private TextField availableCars_brand;

    @FXML
    private TextField availableCars_model;

    @FXML
    private ComboBox<String> availableCars_status;

    @FXML
    private ImageView availableCars_imageView;

    @FXML
    private Button availableCars_importBtn;

    @FXML
    private Button availableCars_insertBtn;

    @FXML
    private Button availableCars_updateBtn;

    @FXML
    private Button availableCars_deleteBtn;

    @FXML
    private Button availableCars_clearBtn;

    @FXML
    private TextField availableCars_price;

    @FXML
    private TableView<Vehicule> availableCars_tableView;
    @FXML
    private TableView<Client> availableClient_tableView;

    @FXML
    private TableColumn<Vehicule, String> availableCars_col_carId;

    @FXML
    private TableColumn<Vehicule, String> availableCars_col_brand;

    @FXML
    private TableColumn<Vehicule, String> availableCars_col_model;

    @FXML
    private TableColumn<Vehicule, String> availableCars_col_price;
    @FXML
    private TableColumn<Client, String> availableClient_col_id;
    @FXML
    private TableColumn<Client, String> availableClient_col_nom;
    @FXML
    private TableColumn<Client, String> availableClient_col_prenom;
    @FXML
    private TableColumn<Client, String> availableClient_col_adresse;
    @FXML
    private TableColumn<Client, String> availableClient_col_phone;

    @FXML
    private TableColumn<Vehicule, String> availableCars_col_status;

    @FXML
    private TextField availableCars_search;

    @FXML
    private AnchorPane rent_form;

    @FXML
    private ComboBox<?> rent_carId;

    @FXML
    private ComboBox<?> rent_brand;

    @FXML
    private ComboBox<?> rent_model;

    @FXML
    private TextField rent_firstName;

    @FXML
    private TextField rent_lastName;

    @FXML
    private ComboBox<?> rent_gender;

    @FXML
    private DatePicker rent_dateRented;

    @FXML
    private DatePicker rent_dateReturn;

    @FXML
    private Button rentBtn;

    @FXML
    private Label rent_total;

    @FXML
    private TextField rent_amount;

    @FXML
    private Label rent_balance;

    @FXML
    private AnchorPane home_form;

    @FXML
    private TableView<Vehicule> rent_tableView;

    @FXML
    private TableColumn<Vehicule, String> rent_col_carId;

    @FXML
    private TableColumn<Vehicule, String> rent_col_brand;

    @FXML
    private TableColumn<Vehicule, String> rent_col_model;

    @FXML
    private TableColumn<Vehicule, String> rent_col_price;

    @FXML
    private TableColumn<Vehicule, String> rent_col_status;

    //    DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private Image image;

    private static dashboardController instance;

    public static dashboardController getInstance() {
        if (instance == null) {
            instance = new dashboardController();
        }
        return instance;
    }

    public dashboardController() {
        try {
            connect = database.getInstance().getCon();
            statement = connect.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void homeAvailableCars(){

        String sql = "SELECT COUNT(Matricule) FROM agences_vehicules.vehicule WHERE status = 'Available'";

        int countAC = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                countAC = result.getInt("COUNT(Matricule)");
            }

            home_availableCars.setText(String.valueOf(countAC));

        }catch(Exception e){e.printStackTrace();}

    }
    private ObservableList<Client> availableClientList;
    public void availableClientShowListData(){


        availableClientList = availableClientListData();

            availableClient_col_id.setCellValueFactory(new PropertyValueFactory<>("IdClient"));
            availableClient_col_nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
            availableClient_col_prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
            availableClient_col_adresse.setCellValueFactory(new PropertyValueFactory<>("Adresse"));
            availableClient_col_phone.setCellValueFactory(new PropertyValueFactory<>("NumTel"));

            availableClient_tableView.setItems(availableClientList);

    }

    public void homeTotalCustomers(){

        String sql = "SELECT COUNT(IdClient) FROM agences_vehicules.client;";

        int countTC = 0;



        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                countTC = result.getInt("COUNT(IdClient)");
            }
            home_totalCustomers.setText(String.valueOf(countTC));
        }catch(Exception e){e.printStackTrace();}

    }

    /*public void homeIncomeChart(){

        home_incomeChart.getData().clear();

        String sql = "SELECT date_rented, SUM(total) FROM customer GROUP BY date_rented ORDER BY TIMESTAMP(date_rented) ASC LIMIT 6";


        try{
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            home_incomeChart.getData().add(chart);

        }catch(Exception e){e.printStackTrace();}
    }*/
//    }THAT IS IT FOR THIS VIDEO, THANKS FOR WATCHING! 
//     DONT FORGET TO SUBSCRIBE OUR CHANNEL FOR MORE UNIQUE TUTORIALS : ) 
//     THANKS FOR THE SUPPORT GUYS!


    /*public void homeCustomerChart(){
       /* home_customerChart.getData().clear();

        String sql = "SELECT date_rented, COUNT(id) FROM customer GROUP BY date_rented ORDER BY TIMESTAMP(date_rented) ASC LIMIT 4";


        try{
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            home_customerChart.getData().add(chart);

        }catch(Exception e){e.printStackTrace();}

    }*/

    private double x = 0;
    private double y = 0;
    public void logout() {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if (option.get().equals(ButtonType.OK)) {
                // HIDE YOUR DASHBOARD FORM
                logoutBtn.getScene().getWindow().hide();

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
    public void availableCarAdd() {

       String sql = "INSERT INTO agences_vehicules.vehicule (Matricule,Type, Model, PrixLocation,status)"
                + "VALUES(?,?,?,?,?)";


        try {
            Alert alert;

            if (availableCars_carId.getText().isEmpty()
                    || availableCars_brand.getText().isEmpty()
                    || availableCars_model.getText().isEmpty()
                    || availableCars_status.getSelectionModel().getSelectedItem() == null
                    || availableCars_price.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
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
        }

    }
    public void availableClientAdd() {

        String sql = "INSERT INTO agences_vehicules.client (IdClient,Nom,Prenom,Adresse,NumTel)"
                + "VALUES(?,?,?,?,?)";


        try {
            Alert alert;

            if (availableClient_clientId.getText().isEmpty()
                    || availableClient_nom.getText().isEmpty()
                    || availableClient_prenom.getText().isEmpty()//
                    || availableClient_adresse.getText().isEmpty()
                    || availableClient_phone.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, availableClient_clientId.getText());
                prepare.setString(2, availableClient_nom.getText());
                prepare.setString(3, availableClient_prenom.getText());
                prepare.setString(4, availableClient_adresse.getText());
                prepare.setString(5, availableClient_phone.getText());

                String req = "INSERT INTO agences_vehicules.client (IdClient,Nom,Prenom,Adresse,NumTel) VALUES ("
                        + availableClient_clientId.getText() + ", '" + availableClient_nom.getText() + "', '" + availableClient_prenom.getText() + "','"
                        + availableClient_adresse.getText() +"',"+availableClient_phone.getText()+");";
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

                    availableClientShowListData();
                    availableClientClear();
                }catch (SQLException e){
                    System.out.println(req+"\n erreur d'ajout "+e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void availableCarUpdate() {
        String req = "UPDATE agences_vehicules.vehicule SET Type='"+availableCars_brand.getText()
                +"', Model='"+availableCars_model.getText()+"',PrixLocation= "
                +availableCars_price.getText()+",status='"+(String) availableCars_status.getSelectionModel().getSelectedItem()+"' WHERE Matricule='"+availableCars_carId.getText()+"';";


        try {
            Alert alert;

            if (availableCars_carId.getText().isEmpty()
                    || availableCars_brand.getText().isEmpty()
                    || availableCars_model.getText().isEmpty()
                    || availableCars_status.getSelectionModel().getSelectedItem() == null
                    || availableCars_price.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill the all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Car ID: " + availableCars_carId.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    int res=statement.executeUpdate(req);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    availableCarShowListData();
                    availableCarClear();
                }

            }
        } catch (Exception e) {
            System.out.println(req+"\n erreur de update "+e);
            e.printStackTrace();
        }

    }
    public void availableClientUpdate() {
        String req = "UPDATE agences_vehicules.client SET Nom='"+availableClient_nom.getText()
                +"', Prenom='"+availableClient_prenom.getText()+"',Adresse='"
                +availableClient_adresse.getText()+"',NumTel="+availableClient_phone.getText()+" WHERE IdClient="+availableClient_clientId.getText()+";";


        try {
            Alert alert;

            if (availableClient_clientId.getText().isEmpty()
                    || availableClient_nom.getText().isEmpty()
                    || availableClient_prenom.getText().isEmpty()
                    || availableClient_adresse.getText().isEmpty()
                    || availableClient_phone.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill the all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Client ID: " + availableClient_clientId.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    int res=statement.executeUpdate(req);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    availableClientShowListData();
                    availableClientClear();
                }

            }
        } catch (Exception e) {
            System.out.println(req+"\n erreur d(update "+e);
            e.printStackTrace();
        }

    }

    public void availableCarDelete() {

        String sql = "DELETE FROM agences_vehicules.vehicule WHERE Matricule ='"+availableCars_carId.getText()+"';";

        try {
            Alert alert;
            if  (availableCars_carId.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Car ID: " + availableCars_carId.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    availableCarShowListData();
                    availableCarClear();
                }

            }
        } catch (Exception e) {
            System.out.println(sql+"\n erreur d'ajout "+e);
            e.printStackTrace();
        }

    }
    public void availableClientDelete() {

        String sql = "DELETE FROM agences_vehicules.client WHERE IdClient ="+availableClient_clientId.getText()+";";

        try {
            Alert alert;
            if  (availableClient_clientId.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Client ID: " + availableClient_clientId.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    availableClientShowListData();
                    availableClientClear();
                }

            }
        } catch (Exception e) {
            System.out.println(sql+"\n erreur d'ajout "+e);
            e.printStackTrace();
        }

    }
    public void availableCarClear() {
        availableCars_carId.setText("");
        availableCars_brand.setText("");
        availableCars_model.setText("");
        availableCars_status.getSelectionModel().clearSelection();
        availableCars_price.setText("");

    }
    public void availableClientClear() {
        availableClient_clientId.setText("");
        availableClient_nom.setText("");
        availableClient_prenom.setText("");
        availableClient_adresse.setText("");
        availableClient_phone.setText("");


    }


    public void rentCarCarId() {

        String sql = "SELECT * FROM agences_vehicules.vehicule WHERE status = 'Available'";


        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("car_id"));
            }

            rent_carId.setItems(listData);

            rentCarBrand();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void rentCarBrand() {

        String sql = "SELECT * FROM agences_vehicules.vehicule WHERE Matricule = '"
                + rent_carId.getSelectionModel().getSelectedItem() + "'";


        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("brand"));
            }

            rent_brand.setItems(listData);

            rentCarModel();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void rentCarModel() {

        String sql = "SELECT * FROM agences_vehicules.vehicule WHERE Model = '"
                + rent_brand.getSelectionModel().getSelectedItem() + "'";


        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("model"));
            }

            rent_model.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public ObservableList<Vehicule> rentCarListData() {

        ObservableList<Vehicule> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM agences_vehicules.vehicule";



        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Vehicule carD;

            while (result.next()) {
                carD = new Vehicule(result.getString("Matricule"), result.getString("Type"),
                        result.getString("Model"), result.getDouble("price"),
                        result.getString("status"));

                listData.add(carD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<Vehicule> rentCarList;

    public void rentCarShowListData() {
        rentCarList = rentCarListData();

        rent_col_carId.setCellValueFactory(new PropertyValueFactory<>("carId"));
        rent_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        rent_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        rent_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        rent_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        rent_tableView.setItems(rentCarList);
    }


    public void displayUsername() {
        String user = getData.username;
        // TO SET THE FIRST LETTER TO BIG LETTER
        username.setText(user.substring(0, 1).toUpperCase() + user.substring(1));

    }
    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }
    private int customerId;
    public void rentCustomerId(){
        String sql = "SELECT IdClient FROM agences_vehicules.client";


        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                // GET THE LAST id and add + 1
                customerId = result.getInt("IdClient") + 1;
            }

        }catch(Exception e){e.printStackTrace();}
    }
    public void rentPay(){
        rentCustomerId();
        String sql = "INSERT INTO agences_vehicules.client (IdClient,Nom,Prenom,Adresse,NumTel) VALUES (?,?,?,?,?,?,?,?,?,?)";

        try{
            Alert alert;

            if(rent_firstName.getText().isEmpty()
                    || rent_lastName.getText().isEmpty()
                    || rent_gender.getSelectionModel().getSelectedItem() == null
                    || rent_carId.getSelectionModel().getSelectedItem() == null
                    || rent_brand.getSelectionModel().getSelectedItem() == null
                    || rent_model.getSelectionModel().getSelectedItem() == null
                    || totalP == 0 || rent_amount.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Something wrong :3");
                alert.showAndWait();
            }else{

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, rent_firstName.getText());
                    prepare.setString(3, rent_lastName.getText());
                    prepare.setString(4, (String)rent_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(5, (String)rent_carId.getSelectionModel().getSelectedItem());
                    prepare.setString(6, (String)rent_brand.getSelectionModel().getSelectedItem());
                    prepare.setString(7, (String)rent_model.getSelectionModel().getSelectedItem());
                    prepare.setString(8, String.valueOf(totalP));
                    prepare.setString(9, String.valueOf(rent_dateRented.getValue()));
                    prepare.setString(10, String.valueOf(rent_dateReturn.getValue()));

                    prepare.executeUpdate();

                    // SET THE  STATUS OF CAR TO NOT AVAILABLE
                    String updateCar = "UPDATE car SET status = 'Not Available' WHERE car_id = '"
                            +rent_carId.getSelectionModel().getSelectedItem()+"'";

                    statement = connect.createStatement();
                    statement.executeUpdate(updateCar);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Successful!");
                    alert.showAndWait();

                    rentCarShowListData();

                    rentClear();
                } // NOW LETS PROCEED TO DASHBOARD FORM : )
            }
        }catch(Exception e){e.printStackTrace();}

    }
    public void rentAmount(){
        Alert alert;
        if(totalP == 0 || rent_amount.getText().isEmpty()){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid :3");
            alert.showAndWait();

            rent_amount.setText("");
        }else{
            amount = Double.parseDouble(rent_amount.getText());

            if(amount >= totalP){
                balance = (amount - totalP);
                rent_balance.setText("$" + String.valueOf(balance));
            }else{
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid :3");
                alert.showAndWait();

                rent_amount.setText("");
            }

        }

    }
    private double amount;
    private double balance;
    public void rentClear(){
        totalP = 0;
        rent_firstName.setText("");
        rent_lastName.setText("");
        rent_gender.getSelectionModel().clearSelection();
        amount = 0;
        balance = 0;
        rent_balance.setText("$0.0");
        rent_total.setText("$0.0");
        rent_amount.setText("");
        rent_carId.getSelectionModel().clearSelection();
        rent_brand.getSelectionModel().clearSelection();
        rent_model.getSelectionModel().clearSelection();
    }
    public ObservableList<Vehicule> availableCarListData() {

        ObservableList<Vehicule> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM agences_vehicules.vehicule";


        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Vehicule carD;

            while (result.next()) {
                carD = new Vehicule(result.getString("Matricule"),
                        result.getString("Type"),
                        result.getString("Model"),
                        result.getDouble("PrixLocation"),
                        result.getString("status"));

                listData.add(carD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    public ObservableList<Client> availableClientListData() {

        ObservableList<Client> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM agences_vehicules.client";


        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Client client;
            while (result.next()) {
                client = new Client(result.getInt("IdClient"),
                        result.getString("Nom"),
                        result.getString("Prenom"),
                        result.getString("Adresse"),
                        result.getInt("NumTel"));

                listData.add(client);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    private double totalP;
    private int countDate;
    public void rentDate(){
        Alert alert;
        if(rent_carId.getSelectionModel().getSelectedItem() == null
                || rent_brand.getSelectionModel().getSelectedItem() == null
                || rent_model.getSelectionModel().getSelectedItem() == null){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Something wrong :3");
            alert.showAndWait();

            rent_dateRented.setValue(null);
            rent_dateReturn.setValue(null);

        }else{

            if(rent_dateReturn.getValue().isAfter(rent_dateRented.getValue())){
                // COUNT THE DAY
                countDate = rent_dateReturn.getValue().compareTo(rent_dateRented.getValue());
            }else{
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Something wrong :3");
                alert.showAndWait();
                // INCREASE OF 1 DAY ONCE THE USER CLICKED THE SAME DAY
                rent_dateReturn.setValue(rent_dateRented.getValue().plusDays(1));

            }
        }
    }
    public void rentDisplayTotal(){
        rentDate();
        double price = 0;
        String sql = "SELECT price, model FROM car WHERE model = '"
                +rent_model.getSelectionModel().getSelectedItem()+"'";


        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                price = result.getDouble("price");
            }
            // price * the count day you want to use the car
            totalP = (price * countDate);
            // DISPLAY TOTAL
            rent_total.setText("$" + String.valueOf(totalP));

        }catch(Exception e){e.printStackTrace();}

    }
    private String[] genderList = {"Male", "Female", "Others"};
    public void rentCarGender() {

        List<String> listG = new ArrayList<>();

        for (String data : genderList) {
            listG.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listG);

        rent_gender.setItems(listData);

    }
    private String[] listStatus = {"Available", "Not Available"};

    public void availableCarStatusList() {

        List<String> listS = new ArrayList<>();

        for (String data : listStatus) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        availableCars_status.setItems(listData);
    }
    /*public void availableCarSearch() {

        FilteredList<carData> filter = new FilteredList<>(availableCarList, e -> true);

        availableCars_search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateCarData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateCarData.getCarId().toString().contains(searchKey)) {
                    return true;
                } else if (predicateCarData.getBrand().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCarData.getModel().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCarData.getPrice().toString().contains(searchKey)) {
                    return true;
                } else if (predicateCarData.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                }
                else {
                    return false;
                }
            });
        });

        SortedList<carData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(availableCars_tableView.comparatorProperty());
        availableCars_tableView.setItems(sortList);

    }*/

    private ObservableList<Vehicule> availableCarList;

    public void availableCarShowListData() {
        availableCarList = availableCarListData();

        availableCars_col_carId.setCellValueFactory(new PropertyValueFactory<>("carId"));
        availableCars_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        availableCars_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        availableCars_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        availableCars_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        availableCars_tableView.setItems(availableCarList);
    }


    public void switchForm(ActionEvent event) {

        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            availableCars_form.setVisible(false);
            rent_form.setVisible(false);
            availableClient_form.setVisible(false);
            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #686f86, #8e9296);");
            availableCars_btn.setStyle("-fx-background-color:transparent");
            rentCar_btn.setStyle("-fx-background-color:transparent");
            availableClient_btn.setStyle("-fx-background-color:transparent");
            homeAvailableCars();
            homeTotalCustomers();
           // homeIncomeChart();
            //homeCustomerChart();

        } else if (event.getSource() == availableCars_btn) {
            home_form.setVisible(false);
            availableCars_form.setVisible(true);
            rent_form.setVisible(false);
            availableClient_form.setVisible(false);

            availableCars_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #686f86, #8e9296);");
            home_btn.setStyle("-fx-background-color:transparent");
            rentCar_btn.setStyle("-fx-background-color:transparent");
            availableClient_btn.setStyle("-fx-background-color:transparent");

            // TO UPDATE YOUR TABLEVIEW ONCE YOU CLICK THE AVAILABLE CAR NAV BUTTON
            availableCarShowListData();
            availableCarStatusList();
           // availableCarSearch();

        } else if (event.getSource() == rentCar_btn) {
            home_form.setVisible(false);
            availableCars_form.setVisible(false);
            rent_form.setVisible(true);
            availableClient_form.setVisible(false);

            rentCar_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #686f86, #8e9296);");
            home_btn.setStyle("-fx-background-color:transparent");
            availableCars_btn.setStyle("-fx-background-color:transparent");
            availableClient_btn.setStyle("-fx-background-color:transparent");
            /*rentCarShowListData();
            rentCarCarId();
            rentCarBrand();
            rentCarModel();
            rentCarGender();*/

        }else if (event.getSource() == availableClient_btn) {
            home_form.setVisible(false);
            availableCars_form.setVisible(false);
            rent_form.setVisible(false);
            availableClient_form.setVisible(true);
            availableClient_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #686f86, #8e9296);");
            home_btn.setStyle("-fx-background-color:transparent");
            rentCar_btn.setStyle("-fx-background-color:transparent");
            availableCars_btn.setStyle("-fx-background-color:transparent");
            // TO UPDATE YOUR TABLEVIEW ONCE YOU CLICK THE AVAILABLE CAR NAV BUTTON
            availableClientShowListData();

            // availableCarSearch();

        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();

        homeAvailableCars();

        homeTotalCustomers();
       // homeIncomeChart();
       // homeCustomerChart();


        // TO DISPLAY THE DATA ON THE TABLEVIEW
        availableCarShowListData();
        availableCarStatusList();

        availableClientShowListData();


       // availableCarSearch();
/*
        rentCarShowListData();
        rentCarCarId();
        rentCarBrand();
        rentCarModel();
        rentCarGender();*/

    }


    }



