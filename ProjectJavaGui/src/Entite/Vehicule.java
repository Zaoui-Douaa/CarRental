/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entite;

/**
 *
 * @author MarcoMan
 * Youtube Channel: https://www.youtube.com/channel/UCPgcmw0LXToDn49akUEJBkQ
    Subscribe our Channel, thank you for the support!
 */
public class Vehicule {
    
    private String Matricule;
    private String brand;
    private String model;
    private Double price;
    private String status;
    private Double numPortes , Chevaux;


    public Vehicule(String  matricule, String brand, String model
            , double price, String status){
        this.Matricule = matricule;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.status = status;
    }
    
    public String getCarId(){
        return Matricule;
    }
    public String getBrand(){
        return brand;
    }
    public String getModel(){
        return model;
    }
    public Double getPrice(){
        return price;
    }


    public String getStatus(){
        return status;
    }

    
}
