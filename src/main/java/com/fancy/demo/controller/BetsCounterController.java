package com.fancy.demo.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fancy.demo.models.BetCounterTracker;
import com.fancy.demo.models.FancyPlayerRunAnalysis;
import com.fancy.demo.models.PlayerRun;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/betcounter")
public class BetsCounterController 
{
	
	@Autowired
	FancyPlayerRunDataController fancyPlayerRunDataController;
	
	
	@RequestMapping("/summary")
	public String getSummaryPage(Model model)
	{
		
		return "betsCounterSummaryPage";
	}
	
	
	@RequestMapping("/tracker/{inning}")
	@ResponseBody
	public Map<String, List<BetCounterTracker>> betCounterTracker(@PathVariable(name = "inning", required = false) String inning)
	{
		Map<String, List<BetCounterTracker>> data = new HashMap<>();
		
		List<BetCounterTracker> tracker = new ArrayList<BetCounterTracker>();
		
		List<PlayerRun> allMatches = fancyPlayerRunDataController.getAllMatches();
		
		List<FancyPlayerRunAnalysis> allMatchAnalysis = new ArrayList<FancyPlayerRunAnalysis>();
		
		for(PlayerRun match : allMatches)
		{
			List<FancyPlayerRunAnalysis> matchAnalysis = fancyPlayerRunDataController.analyzeMatch(match.getMatchId(),Integer.parseInt(inning));
		
			allMatchAnalysis.addAll(matchAnalysis);
		}	
		
		int maxBets = allMatchAnalysis.stream().map(a -> a.getTotalBets()).collect(Collectors.toList()).stream().max(Comparator.comparing(i -> i)).get();
		
		for (int j = 1; j <= maxBets ; j++) 
		{
			Integer index  = Integer.valueOf(j);
			
			List<FancyPlayerRunAnalysis> betSpecificMatches = allMatchAnalysis.stream().filter(a->a.getTotalBets() == Integer.valueOf(index)).collect(Collectors.toList());
			
			long passCount = betSpecificMatches.stream().filter(a->a.getLayBets().get(index-1) > a.getBatsmanScored()).count();
			long failCount = betSpecificMatches.stream().filter(a->a.getLayBets().get(index-1) <= a.getBatsmanScored()).count();
			double winRate = (passCount * 100)/betSpecificMatches.size();
			
			BetCounterTracker b = new BetCounterTracker();
			b.setBetCount(index);
			b.setBetCounter(betSpecificMatches.size());
			b.setPassCounter((int)passCount);
			b.setFailCounter((int)failCount);
			b.setWinRate(winRate);
			tracker.add(b);
		}
		
		data.put("data", tracker);
		
		return data;
	}
}
