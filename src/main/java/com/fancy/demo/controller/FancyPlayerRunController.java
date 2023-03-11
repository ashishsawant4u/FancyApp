package com.fancy.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fancy.demo.models.PlayerRun;
import com.fancy.demo.repository.PlayerRunRepository;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/fancyplayer")
public class FancyPlayerRunController 
{
	@Resource(name = "playerRunRepository")
	PlayerRunRepository playerRunRepository;
	
	@PostMapping("/run")
	public ResponseEntity<String> getFancyRun(@RequestBody PlayerRun playerRun)
	{
		
		if(!existing(playerRun))
		{
			try 
			{
				PlayerRun savedWktNotFancyForPlayer = playerRunRepository.save(playerRun);
				
				if(null!=savedWktNotFancyForPlayer)
				{
					
				}
				
			} catch (Exception e) 
			{
				log.error("failed while saving "+e);
				return new ResponseEntity<String>("NOK",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		
		return new ResponseEntity<String>("OK",HttpStatus.CREATED);
	}
	
	private boolean existing(PlayerRun newRecord)
	{
		PlayerRun existingRecord = playerRunRepository.findByMatchTitleAndInningAndPlayerNameAndLayRunAndLayOddAndBackRunAndBackOddAndPlayerRunAndMatchId
				(newRecord.getMatchTitle(), newRecord.getInning(), newRecord.getPlayerName(), 
						newRecord.getLayRun(), newRecord.getLayOdd(), 
						newRecord.getBackRun(), newRecord.getBackOdd(),
						newRecord.getPlayerRun(),newRecord.getMatchId());
		
		System.out.println("existingRecord "+existingRecord);
		
		return (null!=existingRecord) ? true : false;
	}
}
