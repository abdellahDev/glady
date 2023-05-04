package com.wedoogift.identityprovider.service.impl;

import com.wedoogift.identityprovider.dto.CompanyDto;
import com.wedoogift.identityprovider.dto.UserBalance;
import com.wedoogift.identityprovider.dto.UserCredentials;
import com.wedoogift.identityprovider.dto.UserDetailsDto;
import com.wedoogift.identityprovider.entity.CompanyEntity;
import com.wedoogift.identityprovider.entity.UserEntity;
import com.wedoogift.identityprovider.exception.custom.WedoogiftBadRequestException;
import com.wedoogift.identityprovider.exception.custom.WedoogiftNotFoundException;
import com.wedoogift.identityprovider.mapper.CompanyMapper;
import com.wedoogift.identityprovider.mapper.UserMapper;
import com.wedoogift.identityprovider.repository.UserRepository;
import com.wedoogift.identityprovider.service.CompanyService;
import com.wedoogift.identityprovider.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CompanyService companyService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetailsDto loadUserById(Integer id)  {
        UserEntity userEntity = userRepository.findById(id)
                                              .orElseThrow(()->new WedoogiftNotFoundException("exception.not.found.user",id));

        return UserMapper.mapUserToDto(userEntity);
    }

    @Override
    public UserDetailsDto loadByEmailAndPassword(UserCredentials credentials){
        UserEntity userDetails = userRepository.findByEmailAndPassword(credentials.email(), credentials.password())
                                               .orElseThrow(()->new WedoogiftNotFoundException("exception.not.found.user",credentials));

        return UserMapper.mapUserToDto(userDetails);
    }



    @Override
    @Transactional
    public UserDetailsDto createUser(UserDetailsDto userDetails){
        UserEntity userEntity = UserMapper.mapUserDtoToEntity(userDetails);
        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
        CompanyDto company=companyService.findCompanyByName(userDetails.company().companyName());

        userEntity.setGiftBalance(BigDecimal.ZERO);
        userEntity.setMealBalance(BigDecimal.ZERO);
        //userEntity.setRole(RoleEnum.USER);
        userEntity.setPassword(encodedPassword);

        CompanyEntity companyEntity= CompanyMapper.mapCompanyDtoToEntity(company);
        userEntity.setCompany(companyEntity);
        userEntity = userRepository.save(userEntity);

        return UserMapper.mapUserToDto(userEntity);
    }

    @Override
    @Transactional
    public UserDetailsDto loadUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                                        .orElseThrow(()->new WedoogiftNotFoundException("exception.not.found.user",email));
        if(user==null){
            throw new RuntimeException();
        }
        return UserMapper.mapUserToDto(user);
    }

    @Override
    public boolean checkUserExist(String email){
        return userRepository.existsByEmail(email);
    }



    @Override
    @Transactional
    public List<UserDetailsDto> loadUsers() {
        return userRepository.findAll().stream().map(UserMapper::mapUserToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateUserBalance(Integer userId, String updateType, BigDecimal amount,Integer transaction) {
        UserEntity user= userRepository.findById(userId)
                                       .orElseThrow(()->new WedoogiftNotFoundException("exception.not.found.user",userId));


        switch (updateType) {
            case "Meal" -> {
                if(transaction==0){
                user.setMealBalance(user.getMealBalance().add(amount));}
                if(transaction==1){
                    user.setMealBalance(user.getMealBalance().subtract(amount));
                }
            }
            case "Gift" ->{
                if(transaction==0) {
                    user.setGiftBalance(user.getGiftBalance().add(amount));
                }
                if(transaction==1) {
                    user.setGiftBalance(user.getGiftBalance().subtract(amount));
                }
                }
            default -> throw new WedoogiftBadRequestException("exception.unauthorized.deposit-type");
        }

        userRepository.save(user);

        UserMapper.mapUserToDto(user);
    }

    @Override
    public UserBalance getUserBalance(Integer userId) {
        UserEntity userEntity = userRepository.findById(userId)
                                              .orElseThrow(()->new WedoogiftNotFoundException("exception.not.found.user",userId));

        return UserBalance.builder()
                          .userName(userEntity.getFirstname())
                          .giftBalance(userEntity.getGiftBalance())
                          .mealBalance(userEntity.getMealBalance())
                          .build();
    }
}
