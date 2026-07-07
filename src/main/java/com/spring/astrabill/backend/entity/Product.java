package com.spring.astrabill.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "sku", nullable = false, unique = true)
    private String stockKeepingUnit;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "mrp", nullable = false)
    private Double maximumRetailPrice;

    @Column(name = "sp", nullable = false)
    private Double sellingPrice;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "image", nullable = false)
    private String image;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

}
