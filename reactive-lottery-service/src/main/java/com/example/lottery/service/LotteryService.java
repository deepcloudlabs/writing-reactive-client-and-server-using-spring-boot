package com.example.lottery.service;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LotteryService {

	Flux<List<Integer>> draw(int n);
	Mono<List<Integer>> draw();

}
