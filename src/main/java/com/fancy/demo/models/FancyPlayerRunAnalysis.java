package com.fancy.demo.models;

import java.time.LocalDateTime;
import java.util.List;

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
public class FancyPlayerRunAnalysis {
	
	String playerName;
	
	List<Integer> layBets;
	
	Integer totalBets;
	
	Integer inning;

	LocalDateTime createDateTime;
	
	Integer batsmanScored;
	
	Integer startegy1BetPnL;
	
	Integer startegy2BetPnL;
	
	Integer startegy3BetPnL;
	
	Integer startegy4BetPnL;
	
	Integer startegy5BetPnL;
}
