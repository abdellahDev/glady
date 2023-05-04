package com.wedoogift.identityprovider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wedoogift.identityprovider.dto.CompanyDto;
import com.wedoogift.identityprovider.dto.UserDetailsDto;
import com.wedoogift.identityprovider.entity.UserEntity;
import com.wedoogift.identityprovider.enumeration.RoleEnum;
import com.wedoogift.identityprovider.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private static final String DB_NAME = "identity-provider-db";
    private static final String DB_USR= "postgres";
    private static final String DB_PASSWORD = "mySecret";



    @Container
    private static PostgreSQLContainer postgreSQLContainer= (PostgreSQLContainer) new PostgreSQLContainer("postgres:latest")
            .withInitScript("wedoogift_dummy_data.sql");


    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username",postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password",postgreSQLContainer::getPassword);

    }
	@Test
	void test_user_create() throws Exception {

        // given
        CompanyDto companyTest= CompanyDto.builder()
                .companyName("Glady").build();

        UserDetailsDto userDetailsDto  =UserDetailsDto.builder()
                                                        .firstName("john")
                                                         .lastName("john")
                                                        .company(companyTest)
                                                        .email("john@gmail.com")
                                                        .password("secret")
                                                        .role(RoleEnum.USER)
                                                      .build();

       // when
        MockHttpServletResponse response = (MockHttpServletResponse) mockMvc.perform(
                post("/api/user/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDetailsDto))
        ).andReturn().getResponse();


        // then

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        Optional<UserEntity> createdUser= userRepository.findByEmail("john@gmail.com");
        Assertions.assertTrue(createdUser.isPresent());

        UserEntity userEntity = createdUser.get();
        Assertions.assertEquals(userEntity.getEmail(), userDetailsDto.email());
        Assertions.assertEquals(userEntity.getFirstname(), userDetailsDto.firstName());










    }
}
