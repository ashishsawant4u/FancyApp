package com.fancy.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fancy.demo.models.BatsmanScore;
import com.fancy.demo.models.FancyPlayerRunAnalysis;
import com.fancy.demo.models.LayBetTestAnalysis;
import com.fancy.demo.models.LayBetTestMatchWisePnL;
import com.fancy.demo.models.LayBetTestSummaryStats;
import com.fancy.demo.models.OldScoreBoards;
import com.fancy.demo.models.PlayerRun;
import com.fancy.demo.repository.OldScoreBoardsRepository;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/laybettest")
public class LayBetTestController 
{
	@Autowired
	FancyPlayerRunDataController fancyPlayerRunDataController;
	
	@Autowired
	OldScoreBoardsController oldScoreBoardsController;
	
	@Resource(name = "oldScoreBoardsRepository")
	OldScoreBoardsRepository oldScoreBoardsRepository;
	
	List<LayBetTestAnalysis> LAY_BET_ANALYSIS_LIST = null;
	
	List<LayBetTestMatchWisePnL> LAY_BET_ANALYSIS_MATCH_WISE_LIST = null;
	
	Function<List<Integer>, Integer> RANDOM_LAYBET = (bets) ->{
		
		 Random rand = new Random();
		 return bets.get(rand.nextInt(bets.size()));
	};
	
	@RequestMapping("/analyze/{matchId}/{inning}")
	@ResponseBody
	public Map<String, List<LayBetTestAnalysis>> analyzelayBets(@PathVariable(name = "matchId") String matchId,
			@PathVariable(name = "inning", required = false) String inning)
	{
		LAY_BET_ANALYSIS_LIST = null;
		
		inning = (null!=inning) ? inning : "0";
		
		List<Integer> allLayBets = getAllFirstLaybets(inning);		
		
		List<OldScoreBoards> seasonAllScores = null;
		
		if(inning.equals("0"))
		{
			seasonAllScores = oldScoreBoardsRepository.findByMatchIdContaining(matchId);
		}
		else
		{
			seasonAllScores = oldScoreBoardsRepository.findByMatchIdContainingAndInning(matchId,Integer.parseInt(inning));
		}
		
		List<LayBetTestAnalysis> analysisList = new ArrayList<>();
		
		for(OldScoreBoards score : seasonAllScores)
		{
			LayBetTestAnalysis analysis = getLayBetAnalysisInstance(score);
			int randomLayBet = RANDOM_LAYBET.apply(allLayBets);
			analysis.setLayBet(randomLayBet);
			if(score.getBatsmanScore() < randomLayBet)
			{
				analysis.setBetPnL(100);
			}
			else
			{
				analysis.setBetPnL(-100);
			}
			analysisList.add(analysis);
		}
		
		LAY_BET_ANALYSIS_LIST = analysisList;
		
		Map<String, List<LayBetTestAnalysis>> data = new HashMap<>();
		
		data.put("data", analysisList);
		
		return data;
	}
	
	@RequestMapping("/pnlstats")
	@ResponseBody
	public Map<String, List<LayBetTestMatchWisePnL>> layBetAnalysisStats()
	{
		LAY_BET_ANALYSIS_MATCH_WISE_LIST = null;
		
		Map<String, List<LayBetTestAnalysis>> layeBetTestMap =
				LAY_BET_ANALYSIS_LIST.stream().collect(Collectors.groupingBy(LayBetTestAnalysis::getMatchTitle));
		
		List<LayBetTestMatchWisePnL> analysis = new ArrayList<>();
		
		for(String match : layeBetTestMap.keySet())
		{
			int totalPnL = layeBetTestMap.get(match).stream().mapToInt((a->a.getBetPnL())).sum();
			LayBetTestMatchWisePnL matchPnL = new LayBetTestMatchWisePnL(match, totalPnL);
			analysis.add(matchPnL);
		}
		
		int MAX_PROFIT_IN_MATCH = analysis.stream().mapToInt(m->m.getTotalPnL()).max().getAsInt();
				
		int MAX_LOSS_IN_MATCH = analysis.stream().mapToInt(m->m.getTotalPnL()).min().getAsInt();
		
		LayBetTestMatchWisePnL maxMatchProfit = new LayBetTestMatchWisePnL("MAX_PROFIT_IN_MATCH", MAX_PROFIT_IN_MATCH);

		LayBetTestMatchWisePnL maxMatchLoss = new LayBetTestMatchWisePnL("MAX_LOSS_IN_MATCH", MAX_LOSS_IN_MATCH);
		
		analysis.add(maxMatchProfit);
		analysis.add(maxMatchLoss);
		
		LAY_BET_ANALYSIS_MATCH_WISE_LIST = analysis;
		
		Map<String, List<LayBetTestMatchWisePnL>> data = new HashMap<>();
		
		data.put("data", analysis);
		
		return data;
	}
	
	
	@RequestMapping("/pnlstats/summary")
	@ResponseBody
	public LayBetTestSummaryStats layBetAnalysisSummaryStats()
	{
		
		LayBetTestSummaryStats stats = new LayBetTestSummaryStats();
		
		stats.setTotalBets(LAY_BET_ANALYSIS_LIST.size());
		stats.setPassCount((int) LAY_BET_ANALYSIS_LIST.stream().filter(a->a.getBetPnL() == 100).count());
		stats.setFailCount((int) LAY_BET_ANALYSIS_LIST.stream().filter(a->a.getBetPnL() == -100).count());
		stats.setWinRate((stats.getPassCount()*100)/stats.getTotalBets());
		stats.setFailRate((stats.getFailCount()*100)/stats.getTotalBets());
		
		int MAX_PROFIT_IN_MATCH = LAY_BET_ANALYSIS_MATCH_WISE_LIST.stream().mapToInt(m->m.getTotalPnL()).max().getAsInt();
		int MAX_LOSS_IN_MATCH = LAY_BET_ANALYSIS_MATCH_WISE_LIST.stream().mapToInt(m->m.getTotalPnL()).min().getAsInt();
		
		stats.setMaxProfitInMatch(MAX_PROFIT_IN_MATCH);
		stats.setMaxLossInMatch(MAX_LOSS_IN_MATCH);
		
		return stats;
	}

	private LayBetTestAnalysis getLayBetAnalysisInstance(OldScoreBoards score) 
	{
		LayBetTestAnalysis analysis = new LayBetTestAnalysis();
		analysis.setMatchId(score.getMatchId());
		analysis.setMatchTitle(score.getMatchTitle());
		analysis.setInning(score.getInning());
		analysis.setBatsman(score.getBatsman());
		analysis.setBatsmanScore(score.getBatsmanScore());
		
		return analysis;
	}

	public List<Integer> getAllFirstLaybets(String inning) 
	{
		List<PlayerRun> allMatches = fancyPlayerRunDataController.getAllMatches();
		
		List<FancyPlayerRunAnalysis> allMatchAnalysis = new ArrayList<FancyPlayerRunAnalysis>();
		
		for(PlayerRun match : allMatches)
		{
			List<FancyPlayerRunAnalysis> matchAnalysis = fancyPlayerRunDataController.analyzeMatch(match.getMatchId(),Integer.parseInt(inning));
		
			allMatchAnalysis.addAll(matchAnalysis);
		}
		
		List<Integer> allLayBets =  allMatchAnalysis.stream().map(a->a.getLayBets().get(0)).toList();
		
		allLayBets = allLayBets.stream().distinct().collect(Collectors.toList());
		
		Collections.sort(allLayBets);
		
		return allLayBets;
	}
	
	@RequestMapping("/summary")
	public String getSummaryPage(Model model)
	{
		model.addAttribute("allTournaments", oldScoreBoardsController.getAllTournaments());
		return "layBetTestSummaryPage";
	}
}
