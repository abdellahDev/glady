package com.wedoogift.identityprovider.controller;


import com.wedoogift.identityprovider.dto.CompanyDto;
import com.wedoogift.identityprovider.exception.custom.WedoogiftBadRequestException;
import com.wedoogift.identityprovider.service.CompanyService;
import com.wedoogift.identityprovider.util.CompanyUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@RestController
@RequestMapping("api/company")
@Tag(name = "Company API")
public class CompanyController {


    @Autowired
    private CompanyService companyService;

    /**
     * Create a company - post¨
     *
     * @param companyDto : CompanyDto
     *
     * @return : CompanyDto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Company not created",
                    content = @Content) })
    @Transactional
    public CompanyDto createCompany(@RequestBody CompanyDto companyDto) {
        if(!CompanyUtil.checkCompanyDetails(companyDto)){
             throw new WedoogiftBadRequestException("exception.bad.request.company",companyDto);
        }
        return  this.companyService.createCompany(companyDto);
    }


    @GetMapping("{id}")
    @Operation(summary = "retrieve a company balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "company retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BigDecimal.class)) }),
            @ApiResponse(responseCode = "404", description = "company not found",
                    content = @Content) })
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getCompanyBalance(@PathVariable Integer id){
        return   companyService.findCompanyById(id).balance();
    }








}
