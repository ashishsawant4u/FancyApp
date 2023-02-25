package com.fancy.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fancy.demo.models.MatchIgnore;
import com.fancy.demo.repository.MatchIgnoreRepository;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/matchignore")
public class MatchIgnoreController 
{
	
	@Resource(name = "matchIgnoreRepository")
	MatchIgnoreRepository matchIgnoreRepository;
	
	
	@GetMapping("/add/{matchId}")
	public ResponseEntity<String> add(@PathVariable("matchId") String matchId)
	{
		
		try 
		{
			MatchIgnore m =  new MatchIgnore();
			m.setMatchId(matchId);
			matchIgnoreRepository.save(m);
			
		} catch (Exception e) 
		{
			log.error("failed while saving "+e);
			return new ResponseEntity<String>("NOK",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<String>("OK",HttpStatus.CREATED);
	}

}
