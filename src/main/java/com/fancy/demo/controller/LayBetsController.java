package com.fancy.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
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

import com.fancy.demo.models.FancyPlayerRunAnalysis;
import com.fancy.demo.models.LayBetTracker;
import com.fancy.demo.models.PlayerRun;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/laybets")
public class LayBetsController 
{
	@Autowired
	FancyPlayerRunDataController fancyPlayerRunDataController;
	
	@RequestMapping("/data/{inning}")
	@ResponseBody
	public Map<String, List<Integer>> allLayBets(@PathVariable(name = "inning", required = false) String inning)
	{
		inning = (null!=inning) ? inning : "0";
		
		Map<String, List<Integer>> data = new HashMap<>();
		
		List<PlayerRun> allMatches = fancyPlayerRunDataController.getAllMatches();
		
		List<FancyPlayerRunAnalysis> allMatchAnalysis = new ArrayList<FancyPlayerRunAnalysis>();
		
		for(PlayerRun match : allMatches)
		{
			List<FancyPlayerRunAnalysis> matchAnalysis = fancyPlayerRunDataController.analyzeMatch(match.getMatchId(),Integer.parseInt(inning));
		
			allMatchAnalysis.addAll(matchAnalysis);
		}	
		
		List<Integer> allLayBets =  allMatchAnalysis.stream().flatMap(a->a.getLayBets().stream()).distinct().collect(Collectors.toList());
		
		Collections.sort(allLayBets);
		
		data.put("data", allLayBets);
		
		return data;
	}
	
	@RequestMapping("/scorewise/{inning}")
	@ResponseBody
	public Map<Integer, List<Integer>> scorewiseLaybets(@PathVariable(name = "inning", required = false) String inning)
	{
		inning = (null!=inning) ? inning : "0";
		
		List<PlayerRun> allMatches = fancyPlayerRunDataController.getAllMatches();
		
		List<FancyPlayerRunAnalysis> allMatchAnalysis = new ArrayList<FancyPlayerRunAnalysis>();
		
		for(PlayerRun match : allMatches)
		{
			List<FancyPlayerRunAnalysis> matchAnalysis = fancyPlayerRunDataController.analyzeMatch(match.getMatchId(),Integer.parseInt(inning));
		
			allMatchAnalysis.addAll(matchAnalysis);
		}
		
		Map<Integer, List<Integer>> scoreWiseBetsMap = new HashMap<>();
		
		for (int i = 5; i < 100 ; i++) 
		{
			int score = i;
			List<Integer> layBetsForScore = allMatchAnalysis.stream().filter(a->a.getBatsmanScored()<=score).flatMap(a->a.getLayBets().stream()).distinct().collect(Collectors.toList());
			
			Collections.sort(layBetsForScore);
			
			scoreWiseBetsMap.put(i, layBetsForScore);
			
			i = i+4;
		}
		
		
		return scoreWiseBetsMap;
	}
	
	@RequestMapping("/analyze/{inning}")
	@ResponseBody
	public Map<String, List<LayBetTracker>> analyzelayBets(@PathVariable(name = "inning", required = false) String inning)
	{
		inning = (null!=inning) ? inning : "0";
		
		Map<String, List<LayBetTracker>> data = new HashMap<>();
		
		List<PlayerRun> allMatches = fancyPlayerRunDataController.getAllMatches();
		
		List<FancyPlayerRunAnalysis> allMatchAnalysis = new ArrayList<FancyPlayerRunAnalysis>();
		
		for(PlayerRun match : allMatches)
		{
			List<FancyPlayerRunAnalysis> matchAnalysis = fancyPlayerRunDataController.analyzeMatch(match.getMatchId(),Integer.parseInt(inning));
		
			allMatchAnalysis.addAll(matchAnalysis);
		}
		
		List<Integer> allLayBets =  allMatchAnalysis.stream().flatMap(a->a.getLayBets().stream()).distinct().collect(Collectors.toList());
		
		Collections.sort(allLayBets);
		
		List<LayBetTracker> trackerList = new ArrayList<>();
		
		for(Integer bet : allLayBets)
		{
			List<FancyPlayerRunAnalysis> recordForBet = allMatchAnalysis.stream().filter(a->a.getLayBets().contains(bet)).collect(Collectors.toList());
			
			//a.getLayBets().size()-1
			long passCount = recordForBet.stream().filter(a->a.getLayBets().get(0) > a.getBatsmanScored()).count();
			long failCount = recordForBet.stream().filter(a->a.getLayBets().get(0) <= a.getBatsmanScored()).count();
			double winRate = (passCount * 100)/recordForBet.size();
			
			LayBetTracker tracker = new LayBetTracker();
			tracker.setBet(bet);
			tracker.setBetCounter(recordForBet.size());
			tracker.setPassCounter((int)passCount);
			tracker.setFailCounter((int)failCount);
			tracker.setWinRate(winRate);
			trackerList.add(tracker);
		}
		
		data.put("data", trackerList);
		
		return data;
	}
	
	@RequestMapping("/summary/{inning}")
	public String getSummaryPage(@PathVariable(name = "inning", required = false) String inning,Model model)
	{
		
		return "layBetsSummaryPage";
	}
}
