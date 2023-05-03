package com.mrui22.ecommerce.model;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import javax.persistence.*;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "AuthenticationToken")
public class AuthenticationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AuthenticationTokenPkId")
    private Integer id;

//    @Transient
    private String token;

    @Column(name = "CreateDate")
    private Date createDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "UserPkId")
    private User user;

    public AuthenticationToken(User user) {
        this.token = UUID.randomUUID().toString();
        this.createDate = new Date();
        this.user = user;
    }

    public AuthenticationToken() {

    }

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
