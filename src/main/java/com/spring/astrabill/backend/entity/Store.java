package com.spring.astrabill.backend.entity;


import com.spring.astrabill.backend.enums.StoreStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stores")
public class Store extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @OneToOne
    @JoinColumn(name = "store_admin_id", nullable = false)
    private User storeAdmin;

    @Column(name = "store_type", nullable = false)
    private String storeType;

    @Column(name = "description")
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @Embedded
    private StoreContact storeContact = new StoreContact();

    @PrePersist
    protected void onCreate(){
        status = StoreStatus.PENDING;
    }

}
