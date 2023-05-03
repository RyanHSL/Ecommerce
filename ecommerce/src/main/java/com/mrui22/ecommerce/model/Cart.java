package com.mrui22.ecommerce.model;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name="Cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "CreateDate")
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "ProductPkId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "UserPkId")
    private User user;

    private int quantity;

    public Cart() {
    }

    public Cart(Product product, int quantity, User user) {
        this.createDate = createDate;
        this.product = product;
        this.user = user;
        this.quantity = quantity;
        this.createDate = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
