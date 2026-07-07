package com.spring.astrabill.backend.service.implementation;

import com.spring.astrabill.backend.dto.StoreDTO;
import com.spring.astrabill.backend.dto.UserDTO;
import com.spring.astrabill.backend.entity.Store;
import com.spring.astrabill.backend.entity.User;
import com.spring.astrabill.backend.enums.StoreStatus;
import com.spring.astrabill.backend.enums.UserRoles;
import com.spring.astrabill.backend.exceptions.ForbiddenActivityException;
import com.spring.astrabill.backend.exceptions.ObjectNotFoundException;
import com.spring.astrabill.backend.mapper.StoreMapper;
import com.spring.astrabill.backend.mapper.UserMapper;
import com.spring.astrabill.backend.repository.StoreRepository;
import com.spring.astrabill.backend.repository.UserRepository;
import com.spring.astrabill.backend.service.abstraction.StoreService;
import com.spring.astrabill.backend.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ObjectNotFoundException.class})
@Slf4j
public class StoreServiceImplementation implements StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Override
    public StoreDTO createStore(StoreDTO storeDTO, UserDTO userDTO) {

        User storeAdmin = userRepository
                .findById(userDTO.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Store store = StoreMapper.mapToStore(storeDTO, storeAdmin);
        Store savedStore = storeRepository.save(store);
        storeAdmin.setStore(savedStore);
        userRepository.save(storeAdmin);
        StoreDTO savedStoreDTO = StoreMapper.mapToStoreDTO(savedStore);
        return savedStoreDTO;

    }

    @Override
    public StoreDTO getStoreById(String id) {

        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Store with id " + id + " not found"));
        StoreDTO storeDTO = StoreMapper.mapToStoreDTO(store);
        return storeDTO;

    }

    @Override
    public List<StoreDTO> getAllStores() {

        List<Store> stores = storeRepository.findAll();
        List<StoreDTO> storeDTOs = stores.stream().map(StoreMapper::mapToStoreDTO).toList();
        return storeDTOs;

    }

    @Override
    public StoreDTO getStoreByAdminId() {
        UserDTO currentUserDTO = userService.getCurrentAuthenticatedUser();
        Store store = storeRepository.findByStoreAdminId(currentUserDTO.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Store with admin id " + currentUserDTO.getId() + " not found"));
        StoreDTO storeDTO = StoreMapper.mapToStoreDTO(store);
        return storeDTO;
    }

    @Override
    public StoreDTO getStoreByEmployee() {
        UserDTO currentUserDTO = userService.getCurrentAuthenticatedUser();
        if(currentUserDTO.getStore() == null) {
            throw new ForbiddenActivityException("You are not authorized to handle this store");
        }
        StoreDTO storeDTO = StoreMapper.mapToStoreDTO(UserMapper.mapToUser(currentUserDTO).getStore());
        return storeDTO;
    }

    @Override
    public StoreDTO updateStore(StoreDTO storeDTO, String id) {
        UserDTO currentUserDTO = userService.getCurrentAuthenticatedUser();

        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Store with id " + id + " not found"));

        StoreMapper.updateStoreFromStoreDTO(storeDTO, store);
        Store updatedStore = storeRepository.save(store);
        StoreDTO updatedStoreDTO = StoreMapper.mapToStoreDTO(updatedStore);
        return updatedStoreDTO;

    }

    @Override
    public void deleteStore(String id) {
        storeRepository.findById(id)
                .ifPresentOrElse(storeRepository::delete,()->{
                    throw new ObjectNotFoundException("Store with id " + id + " not found");
                });
    }

    @Override
    public UserDTO addEmployee(UserDTO userDTO, String id) {
        StoreDTO storeDTO = getStoreByAdminId();
        Store store = StoreMapper.mapToStore(storeDTO);

        User employee = UserMapper.mapToUser(userDTO);

        if(userDTO.getRole().equals(UserRoles.ROLE_STORE_MANAGER)){
            employee.setStore(store);
        }

        // TODO : Implement case for Branch

        employee.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User addedEmployee = userRepository.save(employee);
        UserDTO addedEmployeeDTO = UserMapper.mapToUserDTO(addedEmployee);
        return addedEmployeeDTO;

    }

    @Override
    public List<UserDTO> getEmployeesByStore(String storeId) {
        UserDTO currentUserDTO = userService.getCurrentAuthenticatedUser();
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ObjectNotFoundException("Store with id " + storeId + " not found"));
        if(store.getStoreAdmin().getId().equals(currentUserDTO.getId()) || currentUserDTO.getStore().getId().equals(store.getId())){
            List<User> employees = userRepository.findByStoreId(storeId);
            List<UserDTO> employeeDTOS = employees.stream().map(UserMapper::mapToUserDTO).toList();
            return employeeDTOS;
        }
        else {
            throw new ForbiddenActivityException("You are not authorized to view employees of this store");
        }
    }

    @Override
    public StoreDTO moderateStore(StoreStatus action, String id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Store with id " + id + " not found"));
        store.setStatus(action);
        Store updatedStore = storeRepository.save(store);
        StoreDTO updatedStoreDTO = StoreMapper.mapToStoreDTO(updatedStore);
        return updatedStoreDTO;
    }
}
