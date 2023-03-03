package com.fancy.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.fancy.demo.models.OldScoreBoards;



@Component("oldScoreBoardsRepository")
public interface OldScoreBoardsRepository  extends JpaRepository<OldScoreBoards, Long>
{
	List<OldScoreBoards> findByMatchIdContaining(String matchId);
	
	List<OldScoreBoards> findByMatchIdContainingAndInning(String matchId,Integer inning);
	
	@Query("SELECT DISTINCT matchId , matchTitle FROM OldScoreBoards")
	public List<Object> findAllDistinctMatches();	
	
	public List<OldScoreBoards> findByMatchId(String matchId);
}	
