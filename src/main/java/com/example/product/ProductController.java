package com.example.product;
import java.util.*;

import javax.validation.Valid;

import com.example.preset.*;
import com.example.user.UserNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

@CrossOrigin
@RestController
// @CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    @Autowired
    private ProductService products;

    @GetMapping("/products")
    public List<Product> getProducts() {
        return products.getAllProducts();
    }
    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable(value = "id") Long id){
        Product ret = products.getProduct(id);
        if(ret == null){
            throw new ProductNotFoundException(id);
        }
        return ret; 
    }
    @GetMapping("/products/type/{name}")
    public List<Product> getByName(@PathVariable String name){
        return products.getByName(name);
    }
    @GetMapping("/products/brand/{brand}")
    public List<Product> getByBrand(@PathVariable String brand){
        return products.getByBrand(brand);
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product){
        Product product2 = products.addProduct(product);
        if(product2 == null){
            throw new ProductExistException(product.getName());
        }
        return product2;
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product newProduct){
        Product product2 = products.updateProduct(id, newProduct );
        if(product2 == null){
            throw new ProductNotFoundException(newProduct.getName());
        }
        return product2;
    }
    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id){
        try {
            products.deleteProduct(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException(id);
        }
    }


    //Cart Operations 
    @PostMapping("/users/{userId}/cart")
    public void addToCart(@PathVariable Long userId , @RequestBody Product product){
        products.addToCart(userId,product);
    }

    @GetMapping("/users/{userId}/cart")
    public List<Product> getCart(@PathVariable Long userId){
        List<Product> list = products.getCart(userId);
        if(list == null ){
            throw new ProductNotFoundException(userId);
        }else{
            return list; 
        }
    }

    @DeleteMapping("users/{userId}/cart/{product_id}")
    public void deleteFromCart(@PathVariable Long userId, @PathVariable Long product_id){
        try {
            products.deleteFromCart(userId, product_id);;
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException(product_id);
        }
    }
    @DeleteMapping("users/{userId}/cart/deleteAll")
    public void deleteFromCart(@PathVariable Long userId){
        try {
            products.deleteAllFromCart(userId);;
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(userId);
        }
    }

    @PostMapping("products/{name}/getReco/")
    public List<Product> getReco(@PathVariable String name, @RequestBody Preset p ){
        System.out.println(name + "-"+p );
        List<Product> test = products.getReco(name,p);
        System.out.println("HELLOOOOO THIS IS tHE TEST SIZE" + test.size());
        for(int i = 0 ; i < test.size();i++){
            System.out.println(test.get(i));
        }
        return test;
    }

    
}
