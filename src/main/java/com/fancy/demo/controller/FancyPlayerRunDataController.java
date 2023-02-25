package com.fancy.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fancy.demo.models.BatsmanScore;
import com.fancy.demo.models.FancyPlayerRunAnalysis;
import com.fancy.demo.models.PlayerRun;
import com.fancy.demo.repository.BatsmanScoreRepository;
import com.fancy.demo.repository.PlayerRunRepository;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/fancyplayer")
public class FancyPlayerRunDataController 
{
	@Resource(name = "playerRunRepository")
	PlayerRunRepository playerRunRepository;
	
	@Resource(name = "batsmanScoreRepository")
	BatsmanScoreRepository batsmanScoreRepository;
	
	@RequestMapping("/data")
	public String getData(Model model)
	{
		model.addAttribute("allMatches", getAllMatches());
		
		return "fancyPlayerRunData";
	}
	
	@RequestMapping("/data/{matchId}")
	@ResponseBody
	public Map<String, List<PlayerRun>> getTradeSummary(@PathVariable("matchId") String matchId)
	{
		log.info(matchId);
		
		try 
		{
			List<PlayerRun> fancyRunRecords = playerRunRepository.findByMatchId(matchId);
			
			//Collections.reverse(listOfTrades);
		    
			Map<String, List<PlayerRun>> data = new HashMap<>();
		    
		    data.put("data", fancyRunRecords);
		    
		    return data;
		} 
		catch (Exception e) 
		{
			 e.printStackTrace();
			 return null;
		}
		
	}
	
	@RequestMapping("/analyzeMatch/{matchId}")
	@ResponseBody
	public List<FancyPlayerRunAnalysis> analyzeMatch(@PathVariable("matchId") String matchId)
	{
		List<PlayerRun> fancyRunRecords =  playerRunRepository.findDistinctPlayerNameByMatchIdOrderByIdAsc(matchId);
		
		Set<String> playerNames =  fancyRunRecords.stream().map(r -> r.getPlayerName()).collect(Collectors.toSet());
		
		List<FancyPlayerRunAnalysis> playerBetsCount = new ArrayList<FancyPlayerRunAnalysis>();
		
		List<BatsmanScore> batsmanScore =  batsmanScoreRepository.findByMatchId(matchId);
		
		batsmanScore.forEach(b -> b.setPlayerName(b.getPlayerName().replace("*", "").trim()));
		
		for(String playerName : playerNames)
		{
			List<PlayerRun> records = playerRunRepository.findByMatchIdAndPlayerNameOrderByIdAsc(matchId, playerName);
			
			List<PlayerRun> layWithoutZero = records.stream().filter(r->r.getLayRun()!=0).collect(Collectors.toList());
			
			//System.out.println("===================== "+playerName);
			//layWithoutZero.forEach(r -> System.out.println(r.getLayRun()));
			
			if(!CollectionUtils.isEmpty(layWithoutZero))
			{
				List<Integer> layBets = getLayBetsForPlayer(playerName, layWithoutZero);
				
				
				FancyPlayerRunAnalysis p = getBetPnL(layBets, playerName,batsmanScore);
				
				playerBetsCount.add(p);
			}
		}
		
		//playerBetsCount.sort(Comparator.comparing(FancyPlayerRunAnalysis::getInning, Comparator.nullsFirst(Comparator.naturalOrder())));
		
		playerBetsCount.sort(Comparator.comparing(FancyPlayerRunAnalysis::getCreateDateTime, Comparator.nullsFirst(Comparator.naturalOrder())));

		
		return playerBetsCount;
	}
	
	Function<List<PlayerRun>, Integer> getFirstLayRun = (r) -> r.stream().findFirst().get().getLayRun();
	
	BiFunction<List<PlayerRun>, Integer, PlayerRun> findRecordWhereLayFailed = (r,layrun) -> r.stream().filter(d -> d.getPlayerRun() >= layrun).findFirst().orElse(null);
	
	public List<Integer> getLayBetsForPlayer(String playerName, List<PlayerRun> records)
	{
		List<Integer> layBets = new ArrayList<Integer>();
		
		boolean layBetOn = true;
		
		Integer layRun = getFirstLayRun.apply(records);
		
		
		if(layRun > 0)
		{
			while(layBetOn)
			{
				log.info(playerName+" layRun "+layRun);
				
				layBets.add(layRun);
				
				PlayerRun layFailedRecord = findRecordWhereLayFailed.apply(records, layRun);
				
				if(null == layFailedRecord)
				{
					layBetOn = false;
				}
				else
				{
					layRun = layFailedRecord.getLayRun();
				}
			}
		}
		
		
		
		List<Integer> leyBetsSet = layBets.stream().sorted().distinct().collect(Collectors.toList());
		
		log.info(playerName + " bets "+layBets.size()+" ===> "+leyBetsSet);
		
		return leyBetsSet;
	}
	

	BiFunction<Integer, Integer, Boolean> isSecondBetDouble = (b1,b2) -> b2 > (b1*2);
	
	Predicate<List<BatsmanScore>> hasMultipleBatsmanWithSimilarName = (scores) -> scores.stream().map(b->b.getPlayerName()).distinct().count() > 1;
	
	
	private FancyPlayerRunAnalysis getBetPnL(List<Integer> layBets,String playerName,List<BatsmanScore> batsmanScore)
	{

		FancyPlayerRunAnalysis analysis = new FancyPlayerRunAnalysis();
		
		System.out.println("getBetPnL for "+playerName);
		
		int betPnL = 0 ;
		
		String batterName = getBastmanName(playerName);
		
		List<BatsmanScore> playerScore = batsmanScore.stream().filter(p->p.getPlayerName().contains(batterName)).collect(Collectors.toList());
			
		if(CollectionUtils.isEmpty(playerScore) && playerName.contains("-"))
		{
			String batterName2 [] = playerName.split("-");
			String batterName2Str = batterName2[batterName2.length-2];
			String batterName3 [] = batterName2Str.split(" ");
			String batterName3Str = batterName3[batterName3.length-1];
			System.out.println("batterName2Str "+batterName2Str);
			playerScore = batsmanScore.stream().filter(p->p.getPlayerName().contains(batterName3Str)).collect(Collectors.toList());
		}
		
		if(hasMultipleBatsmanWithSimilarName.test(playerScore))
		{	
			
			Map<String, List<BatsmanScore>> playerNameVsRuns =
					playerScore.stream().collect(Collectors.groupingBy(BatsmanScore::getPlayerName));
			
			
			Map<String, Integer> playerNameVsMaxRun = new HashMap<String, Integer>();
			for(String name : playerNameVsRuns.keySet())
			{
				int maxRuns = playerNameVsRuns.get(name).stream().map(b->b.getPlayerRun()).max(Integer::compare).get();
				
				playerNameVsMaxRun.put(name, maxRuns);
			}
			
			
			int highestLayBet = layBets.stream().max(Integer::compare).get();
			
			int differenceWithLayBet  = Integer.MAX_VALUE;
			
			for(String name : playerNameVsMaxRun.keySet())
			{
				if(Math.abs(playerNameVsMaxRun.get(name) - highestLayBet) < differenceWithLayBet)
				{
					differenceWithLayBet = Math.abs(playerNameVsMaxRun.get(name) - highestLayBet);
					playerScore = playerNameVsRuns.get(name);
				}
			}

			
		}
		
		System.out.println("matched batsman ===> "+playerScore.iterator().next().getPlayerName());
		
		int batsmanScored = playerScore.get(playerScore.size()-1).getPlayerRun();
		
		if(layBets.size()>1 && !isSecondBetDouble.apply(layBets.get(0), layBets.get(1)) )
		{
			betPnL = -100;
		}
		
		if(layBets.size()>1 && isSecondBetDouble.apply(layBets.get(0), layBets.get(1)))
		{
			betPnL = (layBets.get(layBets.size()-1) > batsmanScored) ? 100 : -100;
		}
		
		
		if(layBets.size() == 1 )
		{
			betPnL = (layBets.get(0) > batsmanScored) ? 100 : -100;
		}
		
		analysis.setPlayerName(playerName);
		analysis.setLayBets(layBets);
		analysis.setTotalBets(layBets.size());
		analysis.setBetPnL(betPnL);
		analysis.setBatsmanScored(batsmanScored);
		analysis.setInning(playerScore.iterator().next().getInning());
		analysis.setCreateDateTime(playerScore.iterator().next().getCreateDateTime());
		
		return analysis;
	}
	
	private String getBastmanName(String playerName) 
	{
		String playerNameForSearch [] = playerName.split(" ");
		
		String batterName = playerNameForSearch[playerNameForSearch.length-1];
		
		if(batterName.contains("-"))
		{
			String batterNames [] = batterName.split("-");
			batterName = batterNames[batterNames.length -1];
		}
		return batterName;
	}
	
	@RequestMapping("/matches")
	@ResponseBody
	public List<PlayerRun> getAllMatches()
	{
		
		try 
		{
			
			List<Object> cdataList = playerRunRepository.findAllDistinctMatches();		
			
			List<PlayerRun> allMatches = new ArrayList<PlayerRun>();
			
			
			for (Object cdata:cdataList) 
			{
				   Object[] obj= (Object[]) cdata;
				   PlayerRun match = new PlayerRun();
				   match.setMatchId((String)obj[0]);
				   match.setMatchTitle((String)obj[1]);
				   allMatches.add(match);
			}
		    
			Collections.reverse(allMatches);
			
		    return allMatches;
		} 
		catch (Exception e) 
		{
			 e.printStackTrace();
			 return null;
		}
		
	}
	
}