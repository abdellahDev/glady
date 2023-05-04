package com.wedoogift.deposit.controller;

import static org.mockito.Mockito.when;
import com.wedoogift.deposit.dto.DepositDto;
import com.wedoogift.deposit.enumeration.DepositTypeEnum;
import com.wedoogift.deposit.factory.DepositConsumerFactory;
import com.wedoogift.deposit.feignproxy.UserIdentityProxyService;
import com.wedoogift.deposit.model.Deposit;
import com.wedoogift.deposit.repository.DepositRepository;
import com.wedoogift.deposit.service.DepositService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DepositControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepositService depositService;

    @Autowired
    private DepositRepository depositRepository;

    @MockBean
    private UserIdentityProxyService userIdentityProxyService;

    @Container
    private static final MongoDBContainer depositDb = new MongoDBContainer("mongo:latest");

    @Container
    private static final KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"))
            .withEmbeddedZookeeper();




    @DynamicPropertySource
    static void mongoDBProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", depositDb::getReplicaSetUrl);
    }

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }


    @Test
    public void test_deposit() throws Exception {
        //given
        DepositDto depositDto = DepositDto.builder()
                                          .amount(BigDecimal.valueOf(100))
                                          .depositType(DepositTypeEnum.GIFT)
                                          .companyId(1)
                                          .depositExpirationDate(LocalDateTime.now())
                                          .userId(1)
                                          .build();


        // mock feing client calls
        // for checking user existence
        when(userIdentityProxyService.loadUser(1)).thenReturn("glady user");
        // for checking Company's balance is enough
        when(userIdentityProxyService.getCompanyBalance(1)).thenReturn(BigDecimal.valueOf(1000000));

        // when
        MockHttpServletResponse response = (MockHttpServletResponse) mockMvc.perform(
                post("/deposit")
                                                  .contentType("application/json")
                                                  .content(objectMapper.writeValueAsString(depositDto))
                                                                            ).andReturn().getResponse();



        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        // then
     //   DepositDto savedDeposit = depositService.addDeposit(depositDto);

        // assert that the deposit was saved in the database
        Optional<Deposit> depositOptional = depositRepository.findAll().stream().findFirst();
        Assertions.assertTrue(depositOptional.isPresent());
        Deposit deposit = depositOptional.get();
        Assertions.assertEquals(deposit.getAmount(), depositDto.amount());
        Assertions.assertEquals(deposit.getType().getDepositType(), depositDto.depositType().getDepositType());


        // Wait for Kafka message consumption
        Thread.sleep(5000);

        // verify that the message updated is sent to kafka

        ConsumerRecord<String, String> consumedRecord = DepositConsumerFactory.consumeFromKafka(kafkaContainer);
        Assertions.assertNotNull(consumedRecord);
        String expectedValue = "{\"companyId\":1,\"userId\":1,\"transactionType\":0,\"depositAmount\":100,\"depositType\":\"Gift\"}";
        Assertions.assertEquals(expectedValue, consumedRecord.value());

    }

    @Test
    public void test_deposit_Without_Enough_Balance() throws Exception {
        //given
        DepositDto depositDto = DepositDto.builder()
                                          .amount(BigDecimal.valueOf(100))
                                          .depositType(DepositTypeEnum.GIFT)
                                          .companyId(1)
                                          .depositExpirationDate(LocalDateTime.now())
                                          .userId(1)
                                          .build();


        // mock feing client calls
        // for checking user existence
        when(userIdentityProxyService.loadUser(1)).thenReturn("glady user");


        // Set company balance less than deposit amout
        when(userIdentityProxyService.getCompanyBalance(1)).thenReturn(BigDecimal.valueOf(20));

        // when
        MockHttpServletResponse response = (MockHttpServletResponse) mockMvc.perform(
                post("/deposit")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(depositDto))
        ).andReturn().getResponse();



        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());


    }


    @Test
    public void test_deposit_With_User_Not_Existing() throws Exception {
        //given
        DepositDto depositDto = DepositDto.builder()
                                          .amount(BigDecimal.valueOf(100))
                                          .depositType(DepositTypeEnum.GIFT)
                                          .companyId(1)
                                          .depositExpirationDate(LocalDateTime.now())
                                          .userId(1)
                                          .build();


        // mock feing client calls
        // set user to null
        when(userIdentityProxyService.loadUser(1)).thenReturn(null);


        // Set company balance
        when(userIdentityProxyService.getCompanyBalance(1)).thenReturn(BigDecimal.valueOf(100000));

        // when
        MockHttpServletResponse response = (MockHttpServletResponse) mockMvc.perform(
                post("/deposit")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(depositDto))
        ).andReturn().getResponse();



        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());


    }



}
