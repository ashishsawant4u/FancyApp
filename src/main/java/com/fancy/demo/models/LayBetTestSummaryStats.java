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
public class LayBetTestSummaryStats
{	
	
	private Integer totalBets;
	
	private Integer passCount;
	
	private Integer failCount;
	
	private double winRate;
	
	private double failRate;
	
	private Integer maxProfitInMatch;
	
	private Integer maxLossInMatch;
}
