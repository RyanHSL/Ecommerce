package com.mrui22.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "OrderItem")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderItemPkId")
    private Integer id;


    @Column(name = "Quantity")
    private @NotNull int quantity;

    @Column(name = "Price")
    private @NotNull double price;


    @Column(name = "CreateDate")
    private Date createdDate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "OrderPkId", referencedColumnName = "OrderPkId")
    private Order order;

    @OneToOne
    @JoinColumn(name = "ProductPkId", referencedColumnName = "ProductPkId")
    private Product product;

    public OrderItem(){}

    public OrderItem(Order order, @NotNull Product product, @NotNull int quantity, @NotNull double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.order= order;
        this.createdDate = new Date();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
