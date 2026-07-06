package com.spring.astrabill.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.astrabill.backend.entity.StoreContact;
import com.spring.astrabill.backend.enums.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreDTO {
    private String id;
    private String brand;
    private String storeAdminId;
    private UserDTO storeAdmin;
    private String storeType;
    private String description;
    private StoreStatus status;
    private StoreContact storeContact;
}
