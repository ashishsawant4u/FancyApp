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
		
		
		int maxBets = allMatchAnalysis.stream().mapToInt(a -> a.getTotalBets()).max().getAsInt();
		
		for (int j = 1; j <= maxBets ; j++) 
		{
			Integer index  = Integer.valueOf(j);
			
			List<FancyPlayerRunAnalysis> betSpecificMatches = allMatchAnalysis.stream().filter(a->a.getTotalBets() == Integer.valueOf(index)).collect(Collectors.toList());
			
			if(!betSpecificMatches.isEmpty())
			{
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
			
		}
		
		data.put("data", tracker);
		
		return data;
	}
	
	@RequestMapping("/tracker2/{inning}")
	@ResponseBody
	public Map<String, Integer>  betCounterAnalysis(@PathVariable(name = "inning", required = false) String inning)
	{
		Map<String, List<BetCounterTracker>> data = new HashMap<>();
		
		List<PlayerRun> allMatches = fancyPlayerRunDataController.getAllMatches();
		
		List<FancyPlayerRunAnalysis> allMatchAnalysis = new ArrayList<FancyPlayerRunAnalysis>();
		
		for(PlayerRun match : allMatches)
		{
			List<FancyPlayerRunAnalysis> matchAnalysis = fancyPlayerRunDataController.analyzeMatch(match.getMatchId(),Integer.parseInt(inning));
		
			allMatchAnalysis.addAll(matchAnalysis);
		}	
		
		List<FancyPlayerRunAnalysis> betsMoreThan1 = allMatchAnalysis.stream().filter(a -> a.getTotalBets()>1).collect(Collectors.toList());
		
		List<FancyPlayerRunAnalysis> betsWon = betsMoreThan1.stream().filter(a->a.getLayBets().get(1) > a.getBatsmanScored()).collect(Collectors.toList());
		
		int passMoreThan2X = (int) betsWon.stream().filter(b->b.getLayBets().get(1) > (b.getLayBets().get(0)*2)).count();
		
		int passLessThan2X = (int) betsWon.stream().filter(b->b.getLayBets().get(1) < (b.getLayBets().get(0)*2)).count();
		
		Map<String, Integer> data2 = new HashMap<String, Integer>();
		data2.put("betsMoreThan1", betsMoreThan1.size());
		data2.put("betsWon", betsWon.size());
		data2.put("passMoreThan2X", passMoreThan2X);
		data2.put("passLessThan2X", passLessThan2X);
		
		
		return data2;
	}
}
