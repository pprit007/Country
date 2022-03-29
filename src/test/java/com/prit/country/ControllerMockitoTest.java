package com.prit.country;

import com.prit.country.bean.Country;
import com.prit.country.controller.CountryController;
import com.prit.country.service.CountryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ControllerMockitoTest.class})
public class ControllerMockitoTest {
    @Mock
    CountryService countryservice;
    @InjectMocks
    CountryController countryController;

    List<Country> mycountries;
    Country country;

    @Test @Order(1)
    public void test_getAllCountries(){
        mycountries=new ArrayList<Country>();
        mycountries.add(new Country(1,"India","Delhi"));
        mycountries.add(new Country(1,"USA","Washington"));

        when(countryservice.getAllCountries()).thenReturn(mycountries);//Mocking
        ResponseEntity<List<Country>> res=countryController.getCountries();
        assertEquals(HttpStatus.FOUND,res.getStatusCode());
        assertEquals(2,res.getBody().size());
    }

    @Test @Order(2)
    public void test_getCountryById(){
        country=new Country(2,"USA","Washington");
        int countryID=2;
        when(countryservice.getCountryById(countryID)).thenReturn(country);
        ResponseEntity<Country> res=countryController.getCountryById(countryID);
        assertEquals(countryID,res.getBody().getId());
        assertEquals(HttpStatus.OK,res.getStatusCode());
    }

    @Test @Order(3)
    public void getCountryByName(){
        country=new Country(2,"USA","Washington");
        String countryname="USA";
        when(countryservice.getCountryByName(countryname)).thenReturn(country);
        ResponseEntity<Country> res=countryController.getCountryByName(countryname);
        assertEquals(HttpStatus.OK,res.getStatusCode());
        assertEquals(countryname,res.getBody().getCountryName());
    }

    @Test @Order(4)
    public void addCountry(){
        country=new Country(3,"Germany","Berlin");
        when(countryservice.addCountry(country)).thenReturn(country);
        ResponseEntity<Country> res=countryController.addCountry(country);
        assertEquals(HttpStatus.CREATED,res.getStatusCode());
        assertEquals(country,res.getBody());
    }

    @Test @Order(5)
    public void updateCountry(){
        country =new Country(3,"Japan","Tokyo");
        int countryId=3;
        when(countryservice.getCountryById(countryId)).thenReturn(country);
        when(countryservice.updateCountry(country)).thenReturn(country);
        ResponseEntity<Country> res = countryController.updateCountry(countryId,country);
        assertEquals(HttpStatus.OK,res.getStatusCode());
        assertEquals(countryId,res.getBody().getId());
        assertEquals(country.getCountryName(),res.getBody().getCountryName());
        assertEquals(country.getCountryCapital(),res.getBody().getCountryCapital());
    }

    @Test @Order(6)
    public void deleteCountry(){
        country=new Country(2,"India","Delhi");
        int countryId=2;

        when(countryservice.getCountryById(countryId)).thenReturn(country);
        ResponseEntity<Country> res=countryController.deleteCountry(countryId);
        assertEquals(HttpStatus.OK,res.getStatusCode());
    }


}
