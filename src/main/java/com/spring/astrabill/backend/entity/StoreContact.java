package com.spring.astrabill.backend.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreContact {
    private String phone;
    private String email;
    private String address;
}
