package com.edu.mum.domain;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

//    @NotNull(message = "Product name is required.")
//    @Basic(optional = false)
    private String name;

    private Double price;

    private String coverImage;
    private String description;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;


}