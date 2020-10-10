package com.example.lottery.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class LotteryReactiveClientService {
	
	@Scheduled(fixedRateString = "${lottery.period}")
	public void callLotteryService() {
		WebClient.builder().baseUrl("http://localhost:7100").build()
		  .get()
 		  .uri(uriBuilder -> uriBuilder.path("/numbers").queryParam("n", 10).build() )
 		  .header("Accept", "application/json")
		  .retrieve()
		  .bodyToMono(String.class)
		  .subscribe(System.out::println, System.err::println);		
	}
}
