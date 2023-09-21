package ca.spot;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;


@Entity
public class FWOOrder extends PanacheEntity {
    public String customerName;
    public String orderDate;
    public String orderStatus; // created, shipped, revised

}

