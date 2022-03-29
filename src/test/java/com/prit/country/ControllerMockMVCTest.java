package com.prit.country;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prit.country.bean.Country;
import com.prit.country.controller.CountryController;
import com.prit.country.service.CountryService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.prit.country")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = {ControllerMockMVCTest.class})
public class ControllerMockMVCTest {
    @Autowired
    MockMvc mockMvc;
    @Mock
    CountryService countryService;
    @InjectMocks
    CountryController countryController;

    List<Country> myCountries;
    Country country;

    @BeforeEach
    public void setUp(){
        mockMvc= MockMvcBuilders.standaloneSetup(countryController).build();
    }

    @Test @Order(1)
    public void test_getAllCountries() throws Exception {
        myCountries=new ArrayList<Country>();
        myCountries.add(new Country(1,"India","Delhi"));
        myCountries.add(new Country(2,"USA","Washington"));
        when(countryService.getAllCountries()).thenReturn(myCountries);
        this.mockMvc.perform(get("/getcountries")).andExpect(status().isFound())
                    .andDo(print());
    }

    @Test @Order(2)
    public void test_getCountryById() throws Exception {
        country=new Country(2,"USA","Washington");
        int countryID=2;
        when(countryService.getCountryById(countryID)).thenReturn(country);
        this.mockMvc.perform(get("/getcountries/{id}",countryID))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("countryName").value("USA"))
                .andExpect(MockMvcResultMatchers.jsonPath("countryCapital").value("Washington"))
                .andDo(print());
    }

    @Test @Order(3)
    public void test_getCountryByName() throws Exception {
        country=new Country(2,"USA","Washington");
        String countryName="USA";
        when(countryService.getCountryByName(countryName)).thenReturn(country);
        this.mockMvc.perform(get("/getcountries/countryname").param("name",countryName))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("countryName").value("USA"))
                .andExpect(MockMvcResultMatchers.jsonPath("countryCapital").value("Washington"))
                .andDo(print());
    }

    @Test @Order(4)
    public void test_addCountry() throws Exception {
        country=new Country(3,"Germany","Berlin");
        when(countryService.addCountry(country)).thenReturn(country);
        ObjectMapper mapper=new ObjectMapper();
        String jsonbody= mapper.writeValueAsString(country);
        this.mockMvc.perform(post("/addCountry")
                .content(jsonbody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test @Order(5)
    public void test_updateCountry() throws Exception {
        country=new Country(3,"Japan","Tokyo");
        int countryID=3;
        when(countryService.getCountryById(countryID)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);
        ObjectMapper mapper=new ObjectMapper();
        String jsonbody=mapper.writeValueAsString(country);
        this.mockMvc.perform(put("/updatecountry/{id}",countryID)
                        .content(jsonbody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("countryName").value("Japan"))
                .andExpect(MockMvcResultMatchers.jsonPath("countryCapital").value("Tokyo"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test @Order(6)
    public void test_deletecountry() throws Exception {
        country=new Country(3,"Japan","Tokyo");
        int countryId=3;
        when(countryService.getCountryById(countryId)).thenReturn(country);
        this.mockMvc.perform(delete("/deletecountry/{id}",countryId ))
                .andExpect(status().isOk());

    }
}




