package com.fancy.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fancy.demo.models.OldScoreBoards;
import com.fancy.demo.models.ScoreRangeAnalysis;
import com.fancy.demo.repository.OldScoreBoardsRepository;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/oldscorebaords")
public class OldScoreBoardsController 
{
	@Resource(name = "oldScoreBoardsRepository")
	OldScoreBoardsRepository oldScoreBoardsRepository;
	
	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody List<OldScoreBoards> scores)
	{
		
		try 
		{
			oldScoreBoardsRepository.saveAll(scores);
			
		} catch (Exception e) 
		{
			log.error("failed while saving "+e);
			return new ResponseEntity<String>("NOK",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<String>("OK",HttpStatus.CREATED);
	}
	
	BiFunction<List<OldScoreBoards>,Integer, Long> SCORE_OUT_COUNT =  (seasonAllScores,run) -> seasonAllScores.stream().filter(s->s.getBatsmanScore() <= run ).count();
	
	@RequestMapping("/analyzescorerange/{matchId}/{inning}/{scoreRange}")
	@ResponseBody
	public Map<String, List<ScoreRangeAnalysis>> analyzescorerange(@PathVariable(name = "matchId") String matchId,
			@PathVariable(name = "inning", required = false) String inning,
			@PathVariable(name = "scoreRange", required = false) String scoreRange)
	{
		inning = (null!=inning) ? inning : "0";
		scoreRange = (null!=scoreRange) ? scoreRange : "10";
		
		List<OldScoreBoards> seasonAllScores = null;
		
		if(inning.equals("0"))
		{
			seasonAllScores = oldScoreBoardsRepository.findByMatchIdContainingOrderByIdAsc(matchId);
		}
		else
		{
			seasonAllScores = oldScoreBoardsRepository.findByMatchIdContainingAndInningOrderByIdAsc(matchId,Integer.parseInt(inning));
		}
		
		int totalBatsmans = seasonAllScores.size();
	
		List<ScoreRangeAnalysis> analysis = new ArrayList<>(); 
		
		analysis.add(new ScoreRangeAnalysis(1000, "ALL",totalBatsmans , 100.0));
		
		int range = Integer.parseInt(scoreRange);
		
		for (int i = 0; i <= 40 ;i += range) 
		{
			int index  = i;
			
			if(i==0)
			{
				String key = index+"-"+ (index+range);
				System.out.println(key);
				int count = (int) seasonAllScores.stream().filter(s-> s.getBatsmanScore()>= index && s.getBatsmanScore() <= (index+range)).count();
				analysis.add(new ScoreRangeAnalysis(index, key , count , ((count*100)/totalBatsmans) ));
			}
			else
			{
				String key = (index+1)+"-"+ (index+range);
				System.out.println(key);
				int count = (int) seasonAllScores.stream().filter(s-> s.getBatsmanScore()>= (index+1) && s.getBatsmanScore() <= (index+range)).count();
				analysis.add(new ScoreRangeAnalysis(index+1, key , count , ((count*100)/totalBatsmans) ));
			}
			
		}
		
		analysis.sort(Comparator.comparing(ScoreRangeAnalysis::getIndex, Comparator.nullsFirst(Comparator.naturalOrder())));
		
		
		Map<String, List<ScoreRangeAnalysis>> data = new HashMap<>();
		
		data.put("data", analysis);
		
		return data;
	}
	
	@RequestMapping("/analyzescore/{matchId}/{inning}/{scoreRange}")
	@ResponseBody
	public Map<String, List<ScoreRangeAnalysis>> analyzeScore(@PathVariable(name = "matchId") String matchId,
			@PathVariable(name = "inning", required = false) String inning,
			@PathVariable(name = "scoreRange", required = false) String scoreRange)
	{
		inning = (null!=inning) ? inning : "0";
		scoreRange = (null!=scoreRange) ? scoreRange : "10";
		
		List<OldScoreBoards> seasonAllScores = null;
		
		if(inning.equals("0"))
		{
			seasonAllScores = oldScoreBoardsRepository.findByMatchIdContainingOrderByIdAsc(matchId);
		}
		else
		{
			seasonAllScores = oldScoreBoardsRepository.findByMatchIdContainingAndInningOrderByIdAsc(matchId,Integer.parseInt(inning));
		}
		
		int totalBatsmans = seasonAllScores.size();
	
		List<ScoreRangeAnalysis> analysis = new ArrayList<>(); 
		
		analysis.add(new ScoreRangeAnalysis(1000, "ALL",totalBatsmans , 100.0));
		
		long scoredZero =  SCORE_OUT_COUNT.apply(seasonAllScores,0);
		analysis.add(new ScoreRangeAnalysis(0, "0",  (int)scoredZero , ((scoredZero*100)/totalBatsmans) ));
		
		int range = Integer.parseInt(scoreRange);
		
		for (int i = range; i <= 40 ;i += range) 
		{
			int index  = i;
			
			long outCount = SCORE_OUT_COUNT.apply(seasonAllScores, index);
			analysis.add(new ScoreRangeAnalysis(index, String.valueOf(index),  (int)outCount , ((outCount*100)/totalBatsmans) ));
		}
		
		analysis.sort(Comparator.comparing(ScoreRangeAnalysis::getIndex, Comparator.nullsFirst(Comparator.naturalOrder())));
		
		
		Map<String, List<ScoreRangeAnalysis>> data = new HashMap<>();
		
		data.put("data", analysis);
		
		return data;
	}
	
	
	@RequestMapping("/matches")
	@ResponseBody
	public List<OldScoreBoards> getAllMatches()
	{
		
		try 
		{
			
			List<Object> cdataList = oldScoreBoardsRepository.findAllDistinctMatches();		
			
			List<OldScoreBoards> allMatches = new ArrayList<OldScoreBoards>();
			
			
			for (Object cdata:cdataList) 
			{
				   Object[] obj= (Object[]) cdata;
				   OldScoreBoards match = new OldScoreBoards();
				   match.setMatchId((String)obj[0]);
				   match.setMatchTitle((String)obj[1]);
				   allMatches.add(match);
			}
		    
			Collections.reverse(allMatches);
			
			//allMatches.remove(0);
			
		    return allMatches;
		} 
		catch (Exception e) 
		{
			 e.printStackTrace();
			 return null;
		}
		
	}
	
	@RequestMapping("/tournaments")
	@ResponseBody
	public List<OldScoreBoards> getAllTournaments()
	{
		
		try 
		{
			
			List<Object> cdataList = oldScoreBoardsRepository.findAllDistinctMatches();		
			
			List<OldScoreBoards> allTournaments = new ArrayList<OldScoreBoards>();
			
			Set<String> uniquerTournaments = new HashSet<>();
			
			for (Object cdata:cdataList) 
			{
				   Object[] obj= (Object[]) cdata;
				   OldScoreBoards match = new OldScoreBoards();
				   String tournament =  (String)obj[0];
				   List<String> tournamentNameSplit =  List.of(tournament.split("-"));
				   
				   String tournamentName = tournamentNameSplit.subList(0, tournamentNameSplit.size()-2).stream().collect(Collectors.joining("-"));
				   if(!uniquerTournaments.contains(tournamentName))
				   {
					   match.setMatchId(tournamentName);
					   allTournaments.add(match);
					   uniquerTournaments.add(tournamentName);
				   }
			}
			
			Collections.reverse(allTournaments);
			
			//allTournaments.remove(0);
			
		    return allTournaments.stream().distinct().collect(Collectors.toList());
		} 
		catch (Exception e) 
		{
			 e.printStackTrace();
			 return null;
		}
		
	}
	
	@RequestMapping("/summary")
	public String getSummaryPage(Model model)
	{
		model.addAttribute("allMatches", getAllMatches());
		model.addAttribute("allTournaments", getAllTournaments());
		
		return "oldScoreAnalysisPage";
	}

}
