package com.ssafy.special.repository;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductSellListRepositoryImpl implements ProductSellListRepositoryCustom{
	private final JPAQueryFactory queryFactory;	
}