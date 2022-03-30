package com.example.product;

import java.util.*;

import com.example.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.preset.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository products;
    @Autowired
    private UserRepository users;

    // Get methods
    public List<Product> getAllProducts() {
        return products.findAll();
    }

    public Product getProduct(Long id) {
        return products.findById(id).map(product -> {
            return product;
        }).orElse(null);
    }

    // finding similar types of product
    public List<Product> getByName(String name) {
        return products.findByName(name);
    }

    public List<Product> getByBrand(String brand) {
        return products.findByBrand(brand);
    }

    // POST
    public Product addProduct(Product product) {
        List<Product> brandList = products.findByBrand(product.getBrand());
        if (brandList == null) {
            return products.save(product);
        }
        for (int i = 0; i < brandList.size(); i++) {
            if (brandList.get(i).getName().equals(product.getName())) {
                return null;
            }
        }
        return products.save(product);
    }

    // updating the information
    public Product updateProduct(Long id, Product newProduct) {
        return products.findById(id).map(product -> {
            setDetails(newProduct, product);
            return products.save(product);
        }).orElse(null);
    }

    private void setDetails(Product newProduct, Product product) {
        product.setPrice(newProduct.getPrice());
        product.setCalories(newProduct.getCalories());
        product.setSugar(newProduct.getSugar());
        product.setFat(newProduct.getFat());
        product.setProtein(newProduct.getProtein());
        product.setSodium(newProduct.getSodium());
        product.setFibre(newProduct.getFibre());
    }

    // deleting the product from shelf
    public void deleteProduct(Long id) {
        products.deleteById(id);
    }

    public List<Product> getReco(String name, Preset p) {
        System.out.println(p + "-" + name);
        String factors[] = new String[3];
        String sorts[] = new String[3];
        int count = 0;
        for (int i = 0; i < 3; i++) {
            String compare = p.getFactor(i + 1).toLowerCase();
            if(compare == null){
                continue;
            }
            String components[] = compare.split("-");
            System.out.println(Arrays.toString(components));
            switch (components[0]) {
                case "calories":
                    factors[i] = "calories";
                    if (components[1].equals("high")) {
                        sorts[i] = "desc";
                    } else {
                        sorts[i] = "asc";
                    }
                    count++;
                    break;

                case "price":
                    factors[i] = "price";
                    if (components[1].equals("high")) {
                        sorts[i] = "desc";
                    } else {
                        sorts[i] = "asc";
                    }
                    count++;
                    break;

                case "sugar":
                    factors[i] = "sugar";
                    if (components[1].equals("high")) {
                        sorts[i] = "desc";
                    } else {
                        sorts[i] = "asc";
                    }
                    count++;
                    break;

                case "fat":
                    factors[i] = "fat";
                    if (components[1].equals("high")) {
                        sorts[i] = "desc";
                    } else {
                        sorts[i] = "asc";
                    }
                    count++;
                    break;

                case "protein":
                    factors[i] = "protein";
                    if (components[1].equals("high")) {
                        sorts[i] = "desc";
                    } else {
                        sorts[i] = "asc";
                    }
                    count++;
                    break;

                case "sodium":
                    factors[i] = "sodium";
                    if (components[1].equals("high")) {
                        sorts[i] = "desc";
                    } else {
                        sorts[i] = "asc";
                    }
                    count++;
                    break;

                case "fibre":
                    factors[i] = "fibre";
                    if (components[1].equals("high")) {
                        sorts[i] = "desc";
                    } else {
                        sorts[i] = "asc";
                    }
                    count++;
                    break;
                default:
                    continue;
            }
        }
        if(count == 1 ){
            factors[1] = factors[0];
            factors[2] = factors[0];
            sorts[1] = sorts[0];
            sorts[2] = sorts[0];
        }else if(count ==2){
            System.out.println(name + factors[0] + factors[1] + sorts[0] + sorts[1]);
            System.out.println(factors[0] + " THIS IS THE FACTOR WRTF");
            return products.get2Rank(name, factors[0], sorts[0], factors[1], sorts[1]);
        }else if(count == 0 ){
            System.out.println("i entered here !! ");
            return getByName(name);
        }
        System.out.println(name + factors[0] + factors[1] + factors[1] + sorts);
        System.out.println(factors[0] + " THIS IS THE FACTOR WRTF");
        return products.get3Rank(name, factors[0], sorts[0], factors[1], sorts[1], factors[2], sorts[2]);
    }

    // --------------------------------------------------Cart operations
    // --------------------------------------------------------------------

    // adding to cart
    public void addToCart(Long userId, Product product) {
        List<Product> list = users.findById(userId).map(user2 -> {
            List<Product> productsList = products.getCartList(userId);
            if (productsList == null) {
                return null;
            } else {
                return productsList;
            }
        }).orElse(null);
        if (list != null) {

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId().equals(product.getId())) {
                    throw new ProductInCartException(product.getId());
                }
            }

            products.addToCart(userId, product.getId());
        }
    }

    public List<Product> getCart(Long userId) {
        List<Product> list = users.findById(userId).map(user2 -> {
            List<Product> productsList = products.getCartList(userId);
            return productsList;
        }).orElse(null);
        return list;
    }

    public void deleteFromCart(Long userId, Long product_id) {
        products.deleteFromCartList(userId, product_id);
    }

    public void deleteAllFromCart(Long userId) {
        products.deleteAllFromCart(userId);
    }

}
