package com.ssafy.special;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.special.controller.SSHUtils;
//import com.ssafy.special.domain.Product;
import com.ssafy.special.domain.ProductSellList;
import com.ssafy.special.dto.PriceStepResponseDTO;
import com.ssafy.special.dto.ProductPriceResponseDTO;
import com.ssafy.special.main.MainApplication;
import com.ssafy.special.repository.ProductRepository;
import com.ssafy.special.repository.ProductSellArticleSimilerRepository;
import com.ssafy.special.repository.ProductSellListRepository;
import com.ssafy.special.service.DaangnCrawlingServiceImpl;
import com.ssafy.special.service.JoongnaCrawlingService;
import com.ssafy.special.service.SimilarityService;
import com.ssafy.special.service.ThunderCrawlingService;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;


//import lombok.extern.log4j.Log4j2;

@SpringBootTest
//@Log4j2
class CrawlingServerApplicationTests {

	@Autowired
	DaangnCrawlingServiceImpl daangnCrawlingService;
	@Autowired
	JoongnaCrawlingService joongnaCrawlingService;
	@Autowired
	ThunderCrawlingService thunderCrawlingService;
	@Autowired
	MainApplication mainApplication;
	@Autowired
	ProductSellListRepository productSellListRepository;
	
	@Autowired
	ProductSellArticleSimilerRepository productSellArticleSimilerRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	SSHUtils ssh;
	
	@Autowired
	SimilarityService similarityService;
	
//	@Test
//	@Transactional
	void contextLoads() {
//		daangnCrawlingService.crawlingProducts();
//		joongnaCrawlingService.joongnainit();
//		mainApplication.crawlingStart();		
	}

//	@Test
	public void TestSSH() throws Exception {
		String cmd ="ls -lrt";
		ssh.connectSSH();
		String sendFilePath = "C:\\SSAFY\\aws\\test\\sellList.txt";
//		String sendFilePath = "/home/ubuntu/mysqltablefile/sellList.txt";
		String receiveFilePath = "/home/j5d205/receive/";
		ssh.sendFileToOtherServer(sendFilePath,receiveFilePath,"sellList.txt");
		System.out.println(ssh.getSSHResponse("cat "+receiveFilePath+"sellList.txt"));
	}

//	@Test
	void test() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime date_now=LocalDateTime.now();
	
		System.out.println(date_now.format(DateTimeFormatter.ofPattern("yyMMddHH")));
//		System.out.println(date_now.format(formatter));
//		2021-09-17 21:00:38

	}
//	@Test
	void komoran() {
		Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
		String s = "갤럭시 톰브라운 버즈 / 와치 단품,세트판매/새상품\n🍅🍅🍅🍅근거리 퀵배송가능~~🍅🍅🍅🍅\n😊미개봉\n😊버즈만 구매시 :30만원\n😊와치만 구매시 :85만원\n😊버즈 + 와치 구매 :110만원\n\n간지 납니다:)\n직거래 지역은 👉1대1 챗 👈주세요^^\n\n👌👌와치는 가죽 스트랩 같이 드려요!";
        String strToAnalyze = s.replaceAll("[^\\uAC00-\\uD7AF\\u1100-\\u11FF\\u3130-\\u318F]+", " ");
        System.out.println(strToAnalyze);
        KomoranResult analyzeResultList = komoran.analyze(strToAnalyze);

        System.out.println(analyzeResultList.getPlainText());

        List<Token> tokenList = analyzeResultList.getTokenList();
        for (Token token : tokenList) {
        	if("NNP".equals(token.getPos())||"NNG".equals(token.getPos())) {
        		System.out.println(token.getMorph());
        	}
//            System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
        }
	}
	
//	@Test
	void query() {
		Optional<ProductSellList> test =productSellListRepository.findByIdAndMarket((long)20290899, "joonnaApp");
		System.out.println(test.get());
		Long cycle = Long.parseLong("21092518");
		List<ProductSellList> list = productSellListRepository.getRecentProductSellList(cycle, test.get().getProductId().getId()).orElse(new ArrayList<ProductSellList>());
		
		for(ProductSellList psl:list) {
			System.out.println(psl);
		}
	}
//	@Test
	void Service() {
//		if (!ssh.checksession()) {
//			try {
//				ssh.connectSSH();
//			} catch (JSchException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		System.out.println(ssh.getSSHResponse("/usr/local/hadoop/bin/hadoop fs -mkdir Test"));
//		String f = "/usr/local/hadoop/bin/hadoop fs -put /home/j5d205/receive/" + 1 + ".txt "+ "Test/"+1+".txt";
//		ssh.getSSHResponse(f);
//		System.out.println(ssh.getSSHResponse("/usr/local/hadoop/bin/hadoop fs -ls"));

//		ssh.getSSHResponse(f);
//		ssh.getSSHResponse("hadoop fs -ls Set");
//		similarityService.similarityProduct();
//		String f = "hadoop fs -put /home/j5d205/receive/" + 1 + ".txt "+ "Set/"+1+".txt";
//		System.out.println(f);
	}
	
	@Test
	void qq() {
		List<ProductPriceResponseDTO> list = productSellListRepository.getProductByPrice(Long.parseLong("21092912"),(long)10).orElse(new ArrayList<ProductPriceResponseDTO>());
		long maxPrice = list.get(0).getMaxPrice();
		long stepbase = priceRound(maxPrice*0.1);
		long Fstep = priceRound(maxPrice*0.28);
		long Sstep = priceRound(maxPrice*0.46);
		long Thstep = priceRound(maxPrice*0.64);
		long Fostep = priceRound(maxPrice*0.82);
		
		Map<String,PriceStepResponseDTO> pricemap = new HashMap<String, PriceStepResponseDTO>();
		
		int[] arr = new int[5];
		long[] arr2 = {stepbase,Fstep,Sstep,Thstep,Fostep,maxPrice};
		System.out.println(Fstep+" "+Sstep+" "+Thstep+" "+Fostep+" "+maxPrice);
		for(ProductPriceResponseDTO ppr:list) {
			if(stepbase>=ppr.getPrice()) {
				continue;
			}else if(stepbase<ppr.getPrice() && Fstep>=ppr.getPrice()) {
				arr[0]++;
			}else if(Fstep<ppr.getPrice() && Sstep>=ppr.getPrice()) {
				arr[1]++;
			}else if(Sstep<ppr.getPrice() && Thstep>=ppr.getPrice()) {
				arr[2]++;
			}else if(Thstep<ppr.getPrice() && Fostep>=ppr.getPrice()) {
				arr[3]++;
			}else if(Fostep<ppr.getPrice() && maxPrice>=ppr.getPrice()) {
				arr[4]++;
			}
		}
		PriceStepResponseDTO psr;
		psr = new PriceStepResponseDTO();
		psr.setMin(arr2[0]);
		psr.setMax(arr2[1]);
		psr.setCount(0);
		pricemap.put("1Step", psr);
		for(int i=1;i<5;i++) {
			psr = new PriceStepResponseDTO();
			psr.setMin(arr2[i]);
			psr.setMax(arr2[i+1]);
			psr.setCount(arr[i]);
			pricemap.put(i+1+"Step", psr);
		}
		
	}
	
	public long priceRound(double price) {
		String temp = Integer.toString((int)Math.round(price));
		int round =(int) Math.round(Integer.parseInt(temp.substring(0, temp.length()-3))*0.1);
		temp = Integer.toString(round)+"0000";
		return Long.parseLong(temp);
	}

	
}
