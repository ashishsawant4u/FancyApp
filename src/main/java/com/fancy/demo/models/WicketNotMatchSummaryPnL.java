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
public class WicketNotMatchSummaryPnL 
{
	String matchId;
	
	String matchTitle;
	
	String matchDate;
	
	Integer strategy1PnL;
	
	Integer strategy2PnL;
	
	Integer strategy3PnL;
	
	Integer strategy4PnL;
	
	Integer strategy5PnL;
}

