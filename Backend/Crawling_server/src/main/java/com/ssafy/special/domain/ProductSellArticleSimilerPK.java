package com.ssafy.special.domain;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSellArticleSimilerPK implements Serializable {


	private ProductSellListPK articleA;//게시글
	
	private ProductSellListPK articleB;//게시글
	
	@Override
	public int hashCode() {
		return Objects.hash(this.articleA.getId(),this.articleA.getMarket(),this.articleB.getId(),this.articleB.getMarket());
	}
	@Override
	public boolean equals(Object obj) {
		ProductSellArticleSimilerPK tmp =(ProductSellArticleSimilerPK)obj;
		if(this.articleA.getId() == tmp.getArticleA().getId() && this.articleA.getMarket().equals(tmp.getArticleA().getMarket()) && this.articleB.getId() == tmp.getArticleB().getId() && this.articleB.getMarket().equals(tmp.getArticleB().getMarket()))
			return true;
		return false;
	}	
}
