package com.example.lottery.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.lottery.service.LotteryService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("numbers")
public class LotteryReactiveController {
	private LotteryService lotteryService;

	public LotteryReactiveController(LotteryService lotteryService) {
		this.lotteryService = lotteryService;
	}

	@GetMapping(params = { "n" })
	public Flux<List<Integer>> getNumbers(@RequestParam int n) {
		return lotteryService.draw(n);
	}
}
