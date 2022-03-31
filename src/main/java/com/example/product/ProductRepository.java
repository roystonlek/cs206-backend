package com.example.product;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query(value = "select id, brand,calories,fat,fibre,img_url,name,round(price,2) as price , protein , sodium ,sugar, serving_size from product where name like concat('%',?,'%') " , nativeQuery = true)
    List<Product> findByName(String name);
    @Query(value = "select * from product where id = min(?1) " , nativeQuery = true)
    Product findMinOfProperty(String property);
    List<Product> findByBrand(String brand);


    @Query(value = "select p.id, brand,calories,fat,fibre,img_url,name,price , protein , sodium ,sugar, serving_size from cart_products cp , product p where user_id = ? and cp.product_id = p.id ;" , nativeQuery = true)
    List<Product> getCartList(Long userId);

    @Modifying
    @Transactional
    @Query(value = "delete from cart_products where user_id = ?1 and product_id = ?2" , nativeQuery = true)
    void deleteFromCartList(Long userId,Long product_id);

    @Modifying
    @Transactional
    @Query(value = "delete from cart_products where user_id = ?1" , nativeQuery = true)
    void deleteAllFromCart(Long userId);
    
    @Modifying
    @Transactional
    @Query(value = "insert into cart_products(user_id,product_id) values(?1,?2)" , nativeQuery = true)
    void addToCart(Long userId,Long productId);

    @Transactional
    @Query(value = "call looksgood(?1, ?2 , ?3 ,?4,?5,?6,?7) " , nativeQuery = true)
    List<Product> get3Rank(String name,String factor1 ,String sort1 ,String factor2 ,String sort2,String factor3 ,String sort3);
    @Transactional
    @Query(value = "call looksgood2(?1, ?2 , ?3 ,?4,?5) " , nativeQuery = true)
    List<Product> get2Rank(String name,String factor1 ,String sort1 ,String factor2 ,String sort2);

}
