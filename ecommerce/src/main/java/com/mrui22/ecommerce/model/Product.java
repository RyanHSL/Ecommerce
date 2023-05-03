package com.mrui22.ecommerce.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductPkId")
    private Integer id;

    @Column(name = "ProductName")
    private @NotNull String name;

    @Column(name = "ImgUrl")
    private @NotNull String imgUrl;

    @Column(name = "Description")
    private @NotNull String description;

    @Column(name = "Price")
    private @NotNull double price;

    @ManyToOne
    @JoinColumn(name = "CategoryPkId")
    Category category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imgUrl;
    }

    public void setImageUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Entity
    @Table(name = "Tokens")
    public static class AuthenticationToken {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "tokenPkId")
        private Integer id;

        @Transient
        private String token;

        @Column(name = "createDate")
        private LocalDate createDate;

        @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
        @JoinColumn(nullable = false, name = "userPkId")
        private User user;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public LocalDate getCreateDate() {
            return createDate;
        }

        public void setCreateDate(LocalDate createDate) {
            this.createDate = createDate;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
}
