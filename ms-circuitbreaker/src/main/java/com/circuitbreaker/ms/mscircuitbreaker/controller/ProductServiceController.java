package com.circuitbreaker.ms.mscircuitbreaker.controller;

import com.circuitbreaker.ms.mscircuitbreaker.delegate.ProductServiceDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableEurekaClient
public class ProductServiceController {

    @Autowired
    ProductServiceDelegate productServiceDelegate;

    @GetMapping("/Produit/{id}")
    public String getProduct(@PathVariable int id) {
        return productServiceDelegate.callProductServiceAndGetProduct(id);
    }

    @GetMapping("/Produits")
    public String getProducts() {
        return productServiceDelegate.callProductServiceAndGetAllProducts();
    }

    @GetMapping("/AdminProduits")
    public String getCalculMargeProduits() {
        return productServiceDelegate.callProductServiceAndGetMarge();
    }

    @GetMapping("/ProduitsParOrdreAlphabetique")
    public String getOrderedProducts() {
        return productServiceDelegate.callProductServiceAndGetOrderedProducts();
    }


}
