package com.prit.country;
import com.prit.country.bean.Country;
import com.prit.country.repository.CountryRepository;
import com.prit.country.service.CountryService;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ServiceMackitoTest.class})
public class ServiceMackitoTest {
    @Mock
    CountryRepository countryrep;
    @InjectMocks
    CountryService countryService;

    public List<Country> mycountries;

    @Test @Order(1)
    public void test_getAllcountries(){
        List<Country> mycountries=new ArrayList<Country>();
        mycountries.add(new Country(1,"India","Delhi"));
        mycountries.add(new Country(2,"USA","Washington"));

        when(countryrep.findAll()).thenReturn(mycountries);
        assertEquals(2,countryService.getAllCountries().size());
    }

    @Test @Order(2)
    public void test_getcountryByid(){
        List<Country> mycountries=new ArrayList<Country>();
        mycountries.add(new Country(1,"India","Delhi"));
        mycountries.add(new Country(2,"USA","Washington"));

        int countryid=1;

        when(countryrep.findAll()).thenReturn(mycountries);//Mocking the repo

        assertEquals(countryid,countryService.getCountryById(countryid).getId());
    }

    @Test @Order(3)
    public void test_getcountryByname(){
        List<Country> mycountries=new ArrayList<Country>();
        mycountries.add(new Country(1,"India","Delhi"));
        mycountries.add(new Country(2,"USA","Washington"));

        String countryname="India";

        when(countryrep.findAll()).thenReturn(mycountries);//Mocking the repo

        assertEquals(countryname,countryService.getCountryByName(countryname).getCountryName());
    }

    @Test @Order(4)
    public void test_addCountry(){
        Country country=new Country(3,"Germany","Berlin");

        when(countryrep.save(country)).thenReturn(country);
        assertEquals(country,countryService.addCountry(country));
    }

    @Test @Order(5)
    public void test_updateCountry(){
        Country country=new Country(2,"Germany","Berlin");
        when(countryrep.save(country)).thenReturn(country);
        assertEquals(country,countryService.updateCountry(country));
    }

    @Test @Order(6)
    public void test_deleteCountry(){
        Country country=new Country(3,"Germany","Berlin");
        countryService.deleteCountry(country);
        verify(countryrep,times(1)).delete(country);
    }

}
