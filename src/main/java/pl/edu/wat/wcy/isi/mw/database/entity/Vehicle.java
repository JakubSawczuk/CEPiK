package pl.edu.wat.wcy.isi.mw.database.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "vehicle")
public class Vehicle
{
    @Id
    private String vin;
    private String brand;
    private String category;
    private String type;
    private String model;
    private String variant;
    private String version;
    private String registrationNumber;
    private int productionYear;
    private int grossVehicleWeightRating;
    private String dateFirstRegistration;
    private String peopleCapacity;

    public String getPeopleCapacity() {
        return peopleCapacity;
    }

    public void setPeopleCapacity(String peopleCapacity) {
        this.peopleCapacity = peopleCapacity;
    }

    public String getDateFirstRegistration() {
        return dateFirstRegistration;
    }

    public void setDateFirstRegistration(String dateFirstRegistration) {
        this.dateFirstRegistration = dateFirstRegistration;
    }

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "vehicleowner",
            joinColumns = {@JoinColumn(name = "VIN")},
            inverseJoinColumns = {@JoinColumn(name = "PeselDrv")}
    )
    private List<Driver> driverList = new ArrayList<Driver>();

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String typ) {
        this.type = typ;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public int getGrossVehicleWeightRating() {
        return grossVehicleWeightRating;
    }

    public void setGrossVehicleWeightRating(int grossVehicleWeightRating) {
        this.grossVehicleWeightRating = grossVehicleWeightRating;
    }
}
