package com.ssafy.special.service;

import java.util.List;

import com.ssafy.special.domain.ProductSellList;
import com.ssafy.special.dto.ProductSellArticleSimilerResponseDTO;

public interface SimilarityService {
	public List<ProductSellArticleSimilerResponseDTO> returnSimilarity(long pid, String market);
	public void similarityProduct();
}