package com.example.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // 404 Error
public class ProductNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ProductNotFoundException(String Productname) {
        super("Could not find Product " + Productname) ;
    }

    public ProductNotFoundException(Long id) {
        super("Could not find Product " + id) ;
    }
    
}
