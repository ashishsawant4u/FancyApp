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
public class LayBetTestAnalysis
{
	
	private String matchId;
	
	private String matchTitle;
	
	private Integer inning;
	
	private String batsman;
	
	private Integer batsmanScore;
	
	private Integer layBet;
	
	private Integer betPnL;
}
