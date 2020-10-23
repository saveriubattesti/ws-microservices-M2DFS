package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    private static List<Product> products = new ArrayList<Product>() {
        {
            add(new Product(1, "Ordinateur portable" , 350, 120));
            add(new Product(2, "Aspirateur Robot" , 500, 200));
            add(new Product(3, "Table de Ping Pong" , 750, 400));
        }
    };

    //Récupérer la liste des produits
    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
    public List<Product> listeProduits() {
        return products;
    }

    /*public MappingJacksonValue listeProduits() {
        Iterable<Product> produits = productDao.findAll();
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
        produitsFiltres.setFilters(listDeNosFiltres);
        return produitsFiltres;
    }*/

    //Récupérer un produit par son Id
    @GetMapping("/Produit/{id}")
    public Product afficherUnProduit(@PathVariable int id) {
        for (Product product : products) {
            if (product.getId() == id) return product;
        }

        return null;
    }




    //ajouter un produit
    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) {

        Product productAdded =  productDao.save(product);

        if (productAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    // supprimer un produit
    @DeleteMapping("/SupprProduit/{id}")
    public void supprimerProduit(@PathVariable int id) {
        products.removeIf(product -> product.getId() == id);
    }

    // Mettre à jour un produit
    @PutMapping("/UpdateProduit/{id}")
    public void updateProduit(@PathVariable int id, @RequestBody Product product) {
        for (Product p : products) {
            if (p.getId() == id) {
                p.setId(product.getId());
                p.setNom(product.getNom());
                p.setPrix(product.getPrix());
                p.setPrixAchat(product.getPrixAchat());
            }
        }
    }

    @GetMapping("/AdminProduits")
    public Map<String, Integer> calculMargeProduit() {
        Map<String, Integer> map = new HashMap<>();
        for (Product product : products) {
            int marge = product.getPrix() - product.getPrixAchat();
            map.put(product.toString(), marge);
        }

        return map;
    }

    @GetMapping("/ProduitsParOrdreAlphabetique")
    public List<Product> trierProduitsParOrdreAlphabetique() {
        return productDao.trier();
    }


    //Pour les tests
    @GetMapping(value = "test/produits/{prix}")
    public List<Product>  testeDeRequetes(@PathVariable int prix) {
        return productDao.chercherUnProduitCher(400);
    }



}
