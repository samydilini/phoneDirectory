package com.phone.directory.phoneDirectory.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)  // Use SpringExtension for JUnit 5
@SpringBootTest
@ActiveProfiles("test")  // Ensure it uses the application-test.yml
@AutoConfigureMockMvc
public class PhoneDirectoryIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    private final UUID customerId = UUID.randomUUID();
    private final UUID phoneId1 = UUID.randomUUID();
    private final UUID phoneId2 = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        // Insert test data into in-memory H2 database

        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS my_schema");
        jdbcTemplate.execute("SET SCHEMA my_schema");
        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS my_schema.customer (id uuid not null default random_uuid() primary key, first_name varchar(50) not null, surname varchar(255) not null, title varchar(3) not null, address varchar(255) not null)");

        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS my_schema.phone (" +
                "id uuid not null default random_uuid() primary key, " +
                "phone_number varchar(10) not null, " +
                "customer_id uuid not null, " +
                "status boolean default false, " +
                "last_updated timestamp not null default now(), " +
                "foreign key (customer_id) references customer(id))");

        jdbcTemplate.update("INSERT INTO my_schema.customer (id, first_name, surname, title, address) VALUES (?, ?, ?, ?, ?)", customerId, "Luffy", "Monkey", "Mr", "123 Thousand, Sunny");
        jdbcTemplate.update("INSERT INTO my_schema.phone (id, phone_number, customer_id, status, last_updated) VALUES (?, ?, ?, ?, ?)",
                phoneId1, "1234567890", customerId, true, new java.sql.Date(System.currentTimeMillis()));
        jdbcTemplate.update("INSERT INTO my_schema.phone (id, phone_number, customer_id, status, last_updated) VALUES (?, ?, ?, ?, ?)",
                phoneId2, "2344567890", customerId, false, new java.sql.Date(System.currentTimeMillis()));
    }

    @AfterEach
    void tearDown() {
        // Clean up the in-memory H2 database
        jdbcTemplate.execute("DROP ALL OBJECTS DELETE FILES");  // Drop all tables
    }


    @Test
    void getPhoneNumbers_ShouldReturnListOfPhoneNumbers() throws Exception {
        ResultActions result = mockMvc.perform(get("/phone-numbers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        result.andExpect(content().json("[{\"phoneNumber\":\"1234567890\",\"customerName\":\"Luffy\",\"customerId\":\"" + customerId + "\"}," +
                "{\"phoneNumber\":\"2344567890\",\"customerName\":\"Luffy\",\"customerId\":\"" + customerId + "\"}]"));
    }

    @Test
    void getPhoneNumbersForCustomer_ShouldReturnPhoneNumbers_WhenCustomerExists() throws Exception {
        ResultActions result = mockMvc.perform(get("/phone-numbers/{customerId}", customerId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        result.andExpect(content().json("[{\"phoneNumberID\":\"" + phoneId1 + "\",\"phoneNumber\":\"1234567890\",\"status\":true}," +
                "{\"phoneNumberID\":\"" + phoneId2 + "\",\"phoneNumber\":\"2344567890\",\"status\":false}]"));
    }

    @Test
    void getPhoneNumbersForCustomer_ShouldReturn404_WhenCustomerNotFound() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        mockMvc.perform(get("/phone-numbers/{customerId}", nonExistentId.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void activatePhoneNumbers_ShouldReturn200_WhenSuccessful() throws Exception {
        //check customer phone number status before activation
        assertFalse(statusCheck(phoneId2.toString()));

        mockMvc.perform(patch("/phone-numbers/{phoneNumber}/status", phoneId2.toString()))
                .andExpect(status().isOk());

        //check customer phone number status after activation
        assertTrue(statusCheck(phoneId2.toString()));
    }

    @SuppressWarnings("unchecked")
    private Boolean statusCheck(String phoneId) throws Exception {
        ResultActions result = mockMvc.perform(get("/phone-numbers/{customerId}", customerId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        String jsonResponse = result.andReturn().getResponse().getContentAsString();

        // Use ObjectMapper to deserialize the JSON into a List of Maps
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> phoneNumbers = objectMapper.readValue(jsonResponse, List.class);

        assertNotNull(phoneNumbers);  // Ensure the list is not null
        assertFalse(phoneNumbers.isEmpty());  // Ensure there's at least one phone number

        // Example check: Assert phone number and status for each entry
        for (Map<String, Object> phoneNumber : phoneNumbers) {

            String phoneNumberID = (String) phoneNumber.get("phoneNumberID");
            if (phoneNumberID.equals(phoneId)) {
                return (Boolean) phoneNumber.get("status");
            }

        }
        return false;
    }
}
