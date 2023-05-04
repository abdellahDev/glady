package com.wedoogift.identityprovider.service;

import com.wedoogift.identityprovider.dto.UserBalance;
import com.wedoogift.identityprovider.dto.UserCredentials;
import com.wedoogift.identityprovider.dto.UserDetailsDto;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    public UserDetailsDto loadUserById(Integer id);


    public UserDetailsDto loadByEmailAndPassword(UserCredentials credentials);
    public UserDetailsDto createUser(UserDetailsDto userDetails);

    public UserDetailsDto loadUserByEmail(String email);

    public boolean checkUserExist(String email);



    public List<UserDetailsDto> loadUsers();

    public void updateUserBalance(Integer userId, String updateType, BigDecimal amount, Integer transaction);


    public UserBalance getUserBalance(Integer userId);
}
