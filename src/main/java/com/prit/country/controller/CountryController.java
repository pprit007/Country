package com.prit.country.controller;
import com.prit.country.bean.Country;
import com.prit.country.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class CountryController {
    @Autowired
    CountryService countryService;

    @GetMapping("/getcountries" )
    public ResponseEntity<List<Country>> getCountries(){
        try{
            List<Country> countries=countryService.getAllCountries();
            return new ResponseEntity<List<Country>>(countries,HttpStatus.FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getcountries/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable(value = "id") int id){
        try {
            Country country= countryService.getCountryById(id);
            return new ResponseEntity<Country>(country,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getcountries/countryname")
    public ResponseEntity<Country> getCountryByName(@RequestParam(value = "name")String name){
        try {
            Country country= countryService.getCountryByName(name);
            return new ResponseEntity<Country>(country,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addcountry")
    public ResponseEntity<Country> addCountry(@RequestBody Country country){
        try {
            country=countryService.addCountry(country);
            return new ResponseEntity<Country>(country,HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/updatecountry/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable (value = "id") int id, @RequestBody Country country){
        try {
            Country existcountry= countryService.getCountryById(id);
            existcountry.setCountryName(country.getCountryName());
            existcountry.setCountryCapital(country.getCountryCapital());
            Country updatedcountry=countryService.updateCountry(existcountry);
            return new ResponseEntity<Country>(updatedcountry,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(path = "/deletecountry/{id}")
    public ResponseEntity<Country> deleteCountry(@PathVariable("id") int id){
        Country country=null;
        try {
            country=countryService.getCountryById(id);
            countryService.deleteCountry(country);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Country>(country,HttpStatus.OK);
    }

}
