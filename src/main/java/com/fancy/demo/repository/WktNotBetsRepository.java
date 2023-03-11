package com.fancy.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.fancy.demo.models.WktNotBets;


@Component("wktNotBetsRepository")
public interface WktNotBetsRepository extends JpaRepository<WktNotBets, Long>
{
	public WktNotBets findByMatchTitleAndInningAndPlayerNameAndLayRunAndLayOddAndBackRunAndBackOddAndPlayerRunAndMatchId(
			String matchTitle,Integer inning,String playerName,
			Integer layRun,Integer layOdd,
			Integer backRun,Integer backOdd,
			Integer playerRun,String matchId);
	
	
	public WktNotBets findByMatchTitleAndPlayerNameAndMatchId(
			String matchTitle,String playerName,String matchId);
}
