package com.spring.astrabill.backend.service.abstraction;

import com.spring.astrabill.backend.dto.StoreDTO;
import com.spring.astrabill.backend.dto.UserDTO;
import com.spring.astrabill.backend.entity.Store;
import com.spring.astrabill.backend.enums.StoreStatus;

import java.util.List;

public interface StoreService {
    StoreDTO createStore(StoreDTO storeDTO, UserDTO userDTO);
    StoreDTO getStoreById(String id);
    List<StoreDTO> getAllStores();
    StoreDTO getStoreByAdminId();
    StoreDTO getStoreByEmployee();
    StoreDTO updateStore(StoreDTO storeDTO, String id);
    void deleteStore(String id);
    UserDTO addEmployee(UserDTO userDTO, String id);
    List<UserDTO> getEmployeesByStore(String storeId);
    StoreDTO moderateStore(StoreStatus action, String id);
}
