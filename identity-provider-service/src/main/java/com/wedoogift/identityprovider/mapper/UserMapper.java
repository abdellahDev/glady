package com.wedoogift.identityprovider.mapper;

import com.wedoogift.identityprovider.dto.UserDetailsDto;
import com.wedoogift.identityprovider.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private UserMapper(){
    }

    /**
     * to convert a user entity to dto
     * @param userEntity : UserEntity
     * @return : UserDto
     */
    public static UserDetailsDto mapUserToDto(UserEntity userEntity){
        return UserDetailsDto.builder().
                             id(userEntity.getId()).
                             firstName(userEntity.getFirstname()).
                             lastName(userEntity.getLastname()).
                             email(userEntity.getEmail()).
                             role(userEntity.getRole()).
                             password(userEntity.getPassword()).
                             mealBalance(userEntity.getMealBalance()).
                             giftBalance(userEntity.getGiftBalance()).
                             company(CompanyMapper.mapCompanyToDto(userEntity.getCompany())).
                             build();
    }

    /**
     * convert User dto to entity
     * @param userDetailsDto : UserDto
     * @return : UserEntity
     */
    public static UserEntity mapUserDtoToEntity(UserDetailsDto userDetailsDto){
        return UserEntity.builder().
                id(userDetailsDto.id()).
                firstname(userDetailsDto.firstName()).
                lastname(userDetailsDto.lastName()).
                email(userDetailsDto.email()).
                role(userDetailsDto.role()).
                password(userDetailsDto.password()).
                giftBalance(userDetailsDto.giftBalance()).
                mealBalance(userDetailsDto.mealBalance()).
                         company(CompanyMapper.mapCompanyDtoToEntity(userDetailsDto.company())).
                build();
    }


}
