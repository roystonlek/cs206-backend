package com.example.product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductInCartException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ProductInCartException(String Productname) {
        super("Product already exists in cart" + Productname) ;
    }

    public ProductInCartException(Long id) {
        super("Product already exists in cart" + id) ;
    }
}
