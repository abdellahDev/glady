package com.wedoogift.identityprovider.controller;


import com.wedoogift.identityprovider.dto.CompanyDto;
import com.wedoogift.identityprovider.dto.DepositFeignRequest;
import com.wedoogift.identityprovider.dto.UserBalance;
import com.wedoogift.identityprovider.dto.UserDetailsDto;
import com.wedoogift.identityprovider.exception.custom.WedoogiftBadRequestException;
import com.wedoogift.identityprovider.exception.custom.WedoogiftNotFoundException;
import com.wedoogift.identityprovider.feignProxy.DepositProxyService;
import com.wedoogift.identityprovider.service.UserService;
import com.wedoogift.identityprovider.util.CompanyUtil;
import com.wedoogift.identityprovider.util.UserUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("api/user")
@Tag(name = "Users API")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * Create a user - post¨
     *
     * @param userDetails : UserDetailsDto
     *
     * @return : UserDetailsDto
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public UserDetailsDto createUser(@RequestBody UserDetailsDto userDetails) {
        if(!UserUtil.checkUserDetails(userDetails) || userService.checkUserExist(userDetails.email())){
            throw new WedoogiftBadRequestException("exception.bad.request.user",userDetails);

        }

        return  this.userService.createUser(userDetails);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<UserDetailsDto> loadAllUsers(){
       return  userService.loadUsers();
    }


    @GetMapping("{userId}")
    @ResponseStatus(HttpStatus.OK)
    public String loadUser(@PathVariable Integer userId){
        return  userService.loadUserById(userId).email();
    }


    @GetMapping("balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserBalance calculateUserBalance(@PathVariable Integer id){
        return  userService.getUserBalance(id);
    }


}
