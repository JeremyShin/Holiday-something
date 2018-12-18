package com.holidaysomething.holidaysomething.repository;

import com.holidaysomething.holidaysomething.domain.Product;
import java.util.List;

import com.holidaysomething.holidaysomething.domain.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    // 상품 이미지 저장하기
    ProductImage save(ProductImage productImage);

    // 상품등록
    public Product save(Product product);

    @Query("select count(p) from Product p")
    public int countAll();

    // 상품 등록 (fk 카테고리 추가)
    // public Product save(Product product,Long categoryId);
  
  //모든 상품을 상풍명순으로 검색
    List<Product> findAlByOrderByName();

    //카테고리 번호로 상품을 검색
   List<Product> findByProductCategoryIdOrderByName(Long productCategoryId);

}

