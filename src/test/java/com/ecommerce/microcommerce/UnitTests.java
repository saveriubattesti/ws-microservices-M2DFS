package com.ecommerce.microcommerce;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.controller.ProductController;
import com.ecommerce.microcommerce.web.exceptions.ProduitGratuitException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class UnitTests {
    @Mock
    ProductDao productDao;
    @InjectMocks
    ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSupprimerProduit() {
        productController.supprimerProduit(1);
    }

    @Test
    public void testUpdateProduit() throws ProduitGratuitException {
        productController.updateProduit(1, new Product(0, null, 10, 0));
    }

    @Test
    public void testTrierProduitsParOrdreAlphabetique() {
        when(productDao.trier()).
                thenReturn(Arrays.<Product>asList(
                        new Product(1, "A", 10, 5),
                        new Product(2, "C", 10, 5),
                        new Product(3, "B", 10, 5)));

        List<Product> result = productController.trierProduitsParOrdreAlphabetique();
        Assertions.assertEquals(1, result.get(0).getId());
    }

    @Test
    public void testTesteDeRequetes() throws ProduitGratuitException {
        when(productDao.chercherUnProduitCher(anyInt())).
                thenReturn(Arrays.<Product>asList(new Product(0, "nom", 10, 0)));

        List<Product> result = productController.testeDeRequetes(9);
        Assertions.assertEquals(Arrays.<Product>asList(new Product(0, "nom", 10, 0)).get(0).getId(), result.get(0).getId());

    }

    @Test
    public void testProductCreation() {
        Product p = new Product(44, "test", 10, 5);
        Assertions.assertEquals(44, p.getId());
        Assertions.assertEquals("test", p.getNom());
        Assertions.assertEquals(10, p.getPrix());
        Assertions.assertEquals(5, p.getPrixAchat());
        p.setId(45);
        p.setNom("test2");
        p.setPrix(11);
        p.setPrixAchat(6);
        Assertions.assertEquals(45, p.getId());
        Assertions.assertEquals("test2", p.getNom());
        Assertions.assertEquals(11, p.getPrix());
        Assertions.assertEquals(6, p.getPrixAchat());
    }
}
