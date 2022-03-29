package com.prit.country;

import com.prit.country.bean.Country;
import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControlelrIntegrationTests {
    @Test @Order(1)
    void getAllCountriesIntegrationTest() throws JSONException {
        String expected="[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"countryName\": \"India\",\n" +
                "        \"countryCapital\": \"Delhi\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"countryName\": \"USA\",\n" +
                "        \"countryCapital\": \"Washington\"\n" +
                "    }\n" +
                "]";
        TestRestTemplate resttemplate=new TestRestTemplate();
        ResponseEntity<String> reponse=resttemplate.getForEntity("http://localhost:8080/getcountries",String.class);
        System.out.println(reponse.getStatusCode());
        System.out.println(reponse.getBody());
        JSONAssert.assertEquals(expected,reponse.getBody(),false);
    }

    @Test@Order(2)
    void getCountryByIdIntegrationTest() throws JSONException {
        String expected="{\n" +
                "    \"id\": 2,\n" +
                "    \"countryName\": \"USA\",\n" +
                "    \"countryCapital\": \"Washington\"\n" +
                "}";
        TestRestTemplate resttemplate=new TestRestTemplate();
        ResponseEntity<String> reponse=resttemplate.getForEntity("http://localhost:8080/getcountries/2",String.class);
        System.out.println(reponse.getStatusCode());
        System.out.println(reponse.getBody());
        JSONAssert.assertEquals(expected,reponse.getBody(),false);
    }

    @Test@Order(3)
    void getCountryByNameIntegrationTest() throws JSONException {

        String expected= "{\n" +
                "    \"id\": 1,\n" +
                "    \"countryName\": \"India\",\n" +
                "    \"countryCapital\": \"Delhi\"\n" +
                "}";
        TestRestTemplate resttemplate=new TestRestTemplate();
        ResponseEntity<String> reponse=resttemplate.getForEntity("http://localhost:8080/getcountries/countryname?name=India",String.class);
        System.out.println(reponse.getStatusCode());
        System.out.println(reponse.getBody());
        JSONAssert.assertEquals(expected,reponse.getBody(),false);
    }

    @Test @Order(4)
    void addCountryIntegrationTest() throws JSONException {
        Country country=new Country(3,"Germay","Berlin");
        String expected="{\n" +
                "    \"id\":\"3\",\n" +
                "    \"countryName\":\"Germany\",\n" +
                "    \"countryCapital\":\"Berlin\"\n" +
                "}";
        TestRestTemplate restTemplate=new TestRestTemplate();
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Country> request=new HttpEntity<Country>(country,headers);
        ResponseEntity<String> response=restTemplate.postForEntity("http://localhost:8080/addcountry",request,String.class);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        JSONAssert.assertEquals(expected,response.getBody(),false);
    }
}
