package com.spring.astrabill.backend.mapper;

import com.spring.astrabill.backend.dto.StoreDTO;
import com.spring.astrabill.backend.entity.Store;
import com.spring.astrabill.backend.entity.StoreContact;
import com.spring.astrabill.backend.entity.User;

public class StoreMapper {

    public static StoreDTO mapToStoreDTO(Store store){

        StoreDTO storeDTO = new StoreDTO();

        storeDTO.setId(store.getId());
        storeDTO.setBrand(store.getBrand());
        if (store.getStoreAdmin() != null) {
            storeDTO.setStoreAdmin(UserMapper.mapToUserDTO(store.getStoreAdmin()));
        }
        storeDTO.setStoreAdminId(store.getStoreAdminId() != null ? store.getStoreAdminId() : 
            (store.getStoreAdmin() != null ? store.getStoreAdmin().getId() : null));
        storeDTO.setStoreType(store.getStoreType());
        storeDTO.setDescription(store.getDescription());
        storeDTO.setStatus(store.getStatus());
        storeDTO.setStoreContact(store.getStoreContact());

        return storeDTO;

    }

    public static Store mapToStore(StoreDTO storeDTO){

        Store store = new Store();

//        store.setId(storeDTO.getId());
        store.setBrand(storeDTO.getBrand());
        store.setStoreAdmin(UserMapper.mapToUser(storeDTO.getStoreAdmin()));
        store.setStoreType(storeDTO.getStoreType());
        store.setDescription(storeDTO.getDescription());
        store.setStatus(storeDTO.getStatus());
        store.setStoreContact(storeDTO.getStoreContact());

        return store;

    }

    public static Store mapToStore(StoreDTO storeDTO, User storeAdmin) {

        Store store = new Store();

        if (storeDTO.getId() != null) {
            store.setId(storeDTO.getId());
        }

        store.setBrand(storeDTO.getBrand());
        store.setStoreAdmin(storeAdmin);
        store.setStoreAdminId(storeAdmin.getId());
        store.setStoreType(storeDTO.getStoreType());
        store.setDescription(storeDTO.getDescription());
        store.setStatus(storeDTO.getStatus());
        store.setStoreContact(storeDTO.getStoreContact());

        // Set bidirectional relationship
        storeAdmin.setStore(store);

        return store;

    }

    public static void updateStoreFromStoreDTO(StoreDTO storeDTO, Store store) {

        if (storeDTO.getBrand() != null) {
            store.setBrand(storeDTO.getBrand());
        }

        if (storeDTO.getStoreType() != null) {
            store.setStoreType(storeDTO.getStoreType());
        }

        if (storeDTO.getDescription() != null) {
            store.setDescription(storeDTO.getDescription());
        }

        if (storeDTO.getStatus() != null) {
            store.setStatus(storeDTO.getStatus());
        }

        if (storeDTO.getStoreContact() != null) {
            StoreContact contact = StoreContact.builder()
                    .address(storeDTO.getStoreContact().getAddress())
                    .phone(storeDTO.getStoreContact().getPhone())
                    .email(storeDTO.getStoreContact().getEmail())
                    .build();
            store.setStoreContact(contact);
        }
    }

}
