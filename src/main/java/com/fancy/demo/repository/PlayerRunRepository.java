package com.fancy.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.fancy.demo.models.PlayerRun;

@Component("playerRunRepository")
public interface PlayerRunRepository extends JpaRepository<PlayerRun, Long>
{
	public PlayerRun findFirstByOrderByIdDesc();
	
	public PlayerRun findByMatchTitleAndInningAndPlayerNameAndLayRunAndLayOddAndBackRunAndBackOddAndPlayerRunAndMatchId(
			String matchTitle,Integer inning,String playerName,
			Integer layRun,Integer layOdd,
			Integer backRun,Integer backOdd,
			Integer playerRun,String matchId);

	public List<PlayerRun> findByMatchId(String matchId);
	
	public List<PlayerRun> findByMatchIdAndInning(String matchId,Integer inning);
	
	public List<PlayerRun> findDistinctPlayerNameByMatchIdOrderByIdAsc(String matchId);
	
	public List<PlayerRun> findByMatchIdAndPlayerNameOrderByIdAsc(String matchId,String playerName);
	
	 @Query("SELECT DISTINCT matchId FROM PlayerRun")
	 public List<String> findDistinctMatchId();
	
	 @Query("SELECT DISTINCT matchId , matchTitle FROM PlayerRun WHERE matchId NOT IN (SELECT DISTINCT matchId FROM MatchIgnore)")
	 public List<Object> findAllDistinctMatches();
}
