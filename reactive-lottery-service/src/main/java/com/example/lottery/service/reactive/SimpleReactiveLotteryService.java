package com.example.lottery.service.reactive;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.example.lottery.service.LotteryService;

import io.netty.util.internal.ThreadLocalRandom;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SimpleReactiveLotteryService implements LotteryService {

	@Override
	public Flux<List<Integer>> draw(int n) {
		return Flux.concat(IntStream.range(0, n).mapToObj(i -> this.draw()).collect(Collectors.toList()));
	}

	@Override
	public Mono<List<Integer>> draw() {
		return Mono.fromFuture(CompletableFuture.supplyAsync(() -> ThreadLocalRandom.current().ints(1, 50).distinct()
				.limit(6).sorted().boxed().collect(Collectors.toList())));
	}

}
