package com.fancy.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fancy.demo.models.PlayerRun;
import com.fancy.demo.models.WktNotBets;
import com.fancy.demo.repository.WktNotBetsRepository;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/wktnot-placebet")
public class WktNotPlaceBetController 
{
	@Resource(name = "wktNotBetsRepository")
	WktNotBetsRepository wktNotBetsRepository;
	
	@PostMapping("/perform")
	public ResponseEntity<String> placeBet(@RequestBody PlayerRun playerRun)
	{
		WktNotBets wktnotBet = getWktNotBetsInstance(playerRun);
		
		if(!isAnyBetPlacedForPlayer(wktnotBet) && !existing(wktnotBet))
		{
			try 
			{
				WktNotBets savedWktNotbet = wktNotBetsRepository.save(wktnotBet);
				
				log.info("Bet Placed "+savedWktNotbet.getId());
				
			} catch (Exception e) 
			{
				log.error("failed while saving "+e);
				return new ResponseEntity<String>("NOK",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		
		return new ResponseEntity<String>("OK",HttpStatus.CREATED);
	}
	
	private boolean existing(WktNotBets newRecord)
	{
		WktNotBets existingRecord = wktNotBetsRepository.findByMatchTitleAndInningAndPlayerNameAndLayRunAndLayOddAndBackRunAndBackOddAndPlayerRunAndMatchId
				(newRecord.getMatchTitle(), newRecord.getInning(), newRecord.getPlayerName(), 
						newRecord.getLayRun(), newRecord.getLayOdd(), 
						newRecord.getBackRun(), newRecord.getBackOdd(),
						newRecord.getPlayerRun(),newRecord.getMatchId());
		
		System.out.println("existingRecord "+existingRecord);
		
		return (null!=existingRecord) ? true : false;
	}
	
	private boolean isAnyBetPlacedForPlayer(WktNotBets newRecord)
	{
		WktNotBets isAnyBetPlaced = wktNotBetsRepository.findByMatchTitleAndPlayerNameAndMatchId
				(newRecord.getMatchTitle(),newRecord.getPlayerName(), newRecord.getMatchId());
		
		System.out.println("isAnyBetPlaced "+isAnyBetPlaced);
		
		return (null!=isAnyBetPlaced) ? true : false;
	}
	
	
	
	private WktNotBets getWktNotBetsInstance(PlayerRun fancy)
	{
		return WktNotBets.builder()
					.matchTitle(fancy.getMatchTitle())
					.inning(fancy.getInning())
					.playerName(fancy.getPlayerName())
					.layRun(fancy.getLayRun())
					.layOdd(fancy.getLayOdd())
					.backRun(fancy.getBackRun())
					.backOdd(fancy.getBackOdd())
					.playerRun(fancy.getPlayerRun())
					.matchId(fancy.getMatchId())
					.build();
	}
	
}
