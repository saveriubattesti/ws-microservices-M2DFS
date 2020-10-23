package com.circuitbreaker.ms.mscircuitbreaker.delegate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductServiceDelegate {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "callProductServiceAndGetData_Fallback")
    public String callProductServiceAndGetProduct(int id) {

        String response = restTemplate.exchange(
                "http://localhost:9090/Produit/{id}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {},
                id).getBody();

        return "NORMAL FLOW !!! - Student Details : " + response + "-";
    }

    @HystrixCommand(fallbackMethod = "callProductServiceAndGetData_Fallback")
    public String callProductServiceAndGetAllProducts() {

        String response = restTemplate.exchange(
                "http://localhost:9090/Produits",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {}).getBody();

        return "NORMAL FLOW !!! - Products Details : " + response + "-";
    }

    @HystrixCommand(fallbackMethod = "callProductServiceAndGetData_Fallback")
    public String callProductServiceAndGetMarge() {

        String response = restTemplate.exchange(
                "http://localhost:9090/AdminProduits",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {}).getBody();

        return "NORMAL FLOW !!! - Products Details : " + response + "-";
    }

    @HystrixCommand(fallbackMethod = "callProductServiceAndGetData_Fallback")
    public String callProductServiceAndGetOrderedProducts() {

        String response = restTemplate.exchange(
                "http://localhost:9090/ProduitsParOrdreAlphabetique",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {}).getBody();

        return "NORMAL FLOW !!! - Products Details : " + response + "-";
    }

    public String callProductServiceAndGetData_Fallback(int id) {
        return "Fallback response: Not available temporarily";
    }

    public String callProductServiceAndGetData_Fallback() {
        return "Fallback response: Not available temporarily";
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
