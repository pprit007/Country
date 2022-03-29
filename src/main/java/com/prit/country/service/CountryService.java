package com.prit.country.service;

import com.prit.country.bean.Country;
import com.prit.country.controller.AddResponse;
import com.prit.country.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class CountryService {
//    static HashMap<Integer, Country> countryIdMap;
//    public CountryService(){
//        countryIdMap=new HashMap<Integer,Country>();
//        Country indiaCountry= new Country(1,"India","Delhi");
//        Country usaCountry= new Country(2,"USA","Washington");
//        Country ukCountry= new Country(3,"UK","London");
//
//        countryIdMap.put(1,indiaCountry);
//        countryIdMap.put(2,usaCountry);
//        countryIdMap.put(3,ukCountry);
//}

@Autowired
CountryRepository countryrep;
    public List<Country> getAllCountries(){
//        List countries=new ArrayList(countryIdMap.values());
//        return countries;
        return countryrep.findAll();
    }

    public Country getCountryById(int id){
//        Country  country=countryIdMap.get(id);
//        return country;
        List<Country> countries=countryrep.findAll();
        Country country=null;
        for (Country con:countries){
            if (con.getId()==id){
                country=con;
            }
        }
        return country;
    }

    public Country getCountryByName(String countryName){
//        Country country=null;
//        for(int i:countryIdMap.keySet()){
//            if (countryIdMap.get(i).getCountryName().equals(countryName))
//                country=countryIdMap.get(i);
//        }
//        return country;co
        List<Country> countries=countryrep.findAll();
        Country country=null;
        for (Country con:countries){
            if (con.getCountryName().equalsIgnoreCase(countryName)){
                country=con;
            }
        }
        return country;
    }

    public Country addCountry(Country country){
//        country.setId(getMaxId());
//        countryIdMap.put(country.getId(),country);
//        return country;
        country.setId(getMaxId());
        return countryrep.save(country);
    }
    //Utility method
    public int getMaxId(){
//        int max=0;
//        for (int id:countryIdMap.keySet())
//            if(max<=id)
//                max=id;
//    return max+1;
        return countryrep.findAll().size()+1;
    }

    public Country updateCountry(Country country){
//        if(country.getId()>0){
//            countryIdMap.put(country.getId(),country);
//        }
//        return country;
        countryrep.save(country);
        return country;
    }

    public AddResponse deleteCountry(Country country) {
//        countryIdMap.remove(id);
//        AddResponse res=new AddResponse();
//        res.setMsg("Country deleted...");
//        res.setId(id);
//        return res;
//    }
//        countryrep.deleteById(id);
//        AddResponse res=new AddResponse();
//        res.setMsg("Country deleted");
//        res.setId(id);
//        return res;
        countryrep.delete(country);
        return null;
    }
}
