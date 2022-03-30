package com.example.product;


import java.util.*;

import javax.persistence.*;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.EqualsAndHashCode.Exclude;

import com.example.user.*;



@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity

public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    private String name; 
    private String brand; 
    private String imgUrl;
    //factors 
    private double price; 
    private double calories; 
    private double sugar; 
    private double fat; 
    private double protein; 
    private double sodium; 
    private double fibre;  
    @Exclude
    @ManyToMany(fetch = FetchType.EAGER )
    @JsonBackReference
    @JoinTable(name = "cart_products", 
                joinColumns = @JoinColumn(name = "product_id"), 
                inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

}
