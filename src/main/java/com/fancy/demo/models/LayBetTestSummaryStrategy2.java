package com.fancy.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LayBetTestSummaryStrategy2
{	
	
	private Integer totalBets;
	
	private Integer passCount;
	
	private Integer failCount;
	
	private double winRate;
	
	private double failRate;
	
	private Integer maxProfitInMatch;
	
	private Integer maxLossInMatch;
	
	private Integer totalMatches;
	
	private Integer totalPnL;
	
	private Integer maxNosOfBetsInMatch;
	
	private double roi;
	
	private Integer scoredLessThan2XOfFirstLaybet;
	
	private Integer scoredMoreThan2XOfFirstLaybet;
	
}
