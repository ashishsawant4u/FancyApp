package com.fancy.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.fancy.demo.models.BatsmanScore;

@Component("batsmanScoreRepository")
public interface BatsmanScoreRepository extends JpaRepository<BatsmanScore, Long>
{
	public BatsmanScore findByMatchTitleAndInningAndPlayerNameAndPlayerRunAndMatchId(
			String matchTitle,Integer inning,String playerName,
			Integer playerRun,String matchId);
	
	public List<BatsmanScore> findByMatchId(String matchId);
	
	public List<BatsmanScore> findByMatchIdAndInningAndPlayerNameEndsWith(String matchId,Integer inning,String playerName);
	
	public List<BatsmanScore> findByMatchIdAndInningAndPlayerNameStartsWithAndPlayerNameEndsWith(String matchId,Integer inning,String startName,String endName);
}
