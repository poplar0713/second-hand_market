package com.ssafy.special.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.special.domain.ProductSellList;
import com.ssafy.special.domain.ProductSellListPK;

public interface ProductSellListRepository extends JpaRepository<ProductSellList, ProductSellListPK> {
    @Modifying
    @Query(
            value = "truncate table product_sell_list",
            nativeQuery = true
    )
    void truncateProductSellList();
}
