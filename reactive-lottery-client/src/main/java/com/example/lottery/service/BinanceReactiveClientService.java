package com.example.lottery.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.lottery.dto.Ticker;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class BinanceReactiveClientService {
	private static final List<String> BINANCE_URLS = List.of("BTCUSDT","LTCBTC","ETHBTC","BNBBTC","NEOBTC","EOSETH","SNTETH","BNTETH","BCCBTC","SALTBTC","SALTETH","XVGETH","XVGBTC",
			"SUBETH","EOSBTC","MTHBTC","ETCETH","DNTBTC","ENGBTC");

	private static WebClient webClient = WebClient.builder().baseUrl("https://api.binance.com/api/v3").build();
	
	@Scheduled(fixedRateString = "${binance.period}")
	public void callBinanceService() {
		System.err.println(String.format("Calling Binance Rest Api %d times...",BINANCE_URLS.size()));
		Flux.fromIterable(BINANCE_URLS)
		        .parallel()
		        .runOn(Schedulers.elastic())
		        .flatMap(this::getTicker)
		        .ordered(Comparator.comparing(Ticker::getSymbol))
		        .subscribe(System.err::println);		
		System.err.println(String.format("Calling Binance Rest Api %d times.✔",BINANCE_URLS.size()));

	}
	
	public Mono<Ticker> getTicker(String symbol) {
		return webClient.get()
		 		 .uri(uriBuilder -> uriBuilder.path("/ticker/price").queryParam("symbol", symbol).build() )
		        .retrieve()
		        .bodyToMono(Ticker.class);
	}
}
