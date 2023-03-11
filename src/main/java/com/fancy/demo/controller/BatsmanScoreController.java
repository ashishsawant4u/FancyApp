package com.fancy.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.fancy.demo.commons.NameUtils;
import com.fancy.demo.models.BatsmanScore;
import com.fancy.demo.repository.BatsmanScoreRepository;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/batsmanscore")
public class BatsmanScoreController 
{
	@Resource(name = "batsmanScoreRepository")
	BatsmanScoreRepository batsmanScoreRepository;
	
	@PostMapping("/add")
	public ResponseEntity<String> add(@RequestBody BatsmanScore batsmanScore)
	{
		
		if(!existing(batsmanScore))
		{
			try 
			{
				batsmanScoreRepository.save(batsmanScore);
				
			} catch (Exception e) 
			{
				log.error("failed while saving "+e);
				return new ResponseEntity<String>("NOK",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		
		return new ResponseEntity<String>("OK",HttpStatus.CREATED);
	}
	
	private boolean existing(BatsmanScore newRecord)
	{
		BatsmanScore existingRecord = batsmanScoreRepository.findByMatchTitleAndInningAndPlayerNameAndPlayerRunAndMatchId
				(newRecord.getMatchTitle(), newRecord.getInning(), newRecord.getPlayerName(), 
						newRecord.getPlayerRun(),newRecord.getMatchId());
		
		System.out.println("existingRecord "+existingRecord);
		
		return (null!=existingRecord) ? true : false;
	}
	
	@RequestMapping("/get/{matchId}")
	@ResponseBody
	public Map<String, List<BatsmanScore>> getBatsmanScoreByMatchId(@PathVariable("matchId") String matchId)
	{
		
		Map<String, List<BatsmanScore>> data = new HashMap<>();
	    
	    data.put("data", batsmanScoreRepository.findByMatchId(matchId));
		
		return data;
	}
	
	@RequestMapping("/data/{matchId}")
	public String getData(Model model)
	{
		
		return "batsmanScoresData";
	}
	
	@RequestMapping("/batter/{matchId}/{inning}/{playerName}")
	@ResponseBody
	public List<BatsmanScore> getBatsmanScore(@PathVariable String matchId,
			@PathVariable String inning,
			@PathVariable String playerName)
	{
		
		String sirname = NameUtils.getSirname(playerName);
		List<BatsmanScore> results = batsmanScoreRepository.findByMatchIdAndInningAndPlayerNameEndsWith(matchId,Integer.valueOf(inning),sirname);
		
		boolean hasMultipleBatsmanWithSameSirname =  results.stream().map(s->s.getPlayerName()).distinct().count() > 1;
		
		if(hasMultipleBatsmanWithSameSirname)
		{
			results =  results.stream().filter(s->s.getPlayerName().startsWith(NameUtils.getFirstName(playerName))).collect(Collectors.toList());
		}
		
		boolean isBatsmanInitialsCorrect = results.stream().filter(s->s.getPlayerName().startsWith(String.valueOf(playerName.charAt(0)))).findAny().isPresent();
		
		if(!isBatsmanInitialsCorrect)
		{
			results = batsmanScoreRepository.findByMatchIdAndInningAndPlayerNameStartsWithAndPlayerNameEndsWith(matchId,Integer.valueOf(inning),String.valueOf(playerName.charAt(0)),sirname);
		
			if(results.isEmpty())
			{
				for(String word : NameUtils.getWordsInName(playerName))
				{
					if(results.isEmpty())
					{
						results = batsmanScoreRepository.findByMatchIdAndInningAndPlayerNameStartsWithAndPlayerNameEndsWith(matchId,Integer.valueOf(inning),String.valueOf(playerName.charAt(0)),word);
					}
				}
			}
		}
		 
		return results;
	}
}
