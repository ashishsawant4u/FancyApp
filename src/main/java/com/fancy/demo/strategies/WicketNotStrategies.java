package com.fancy.demo.strategies;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WicketNotStrategies 
{
	
	public static BiFunction<Integer, Integer, Boolean> isSecondBetMoreThanDouble = (b1,b2) -> b2 > (b1*2);
	
	public static int strategy1BetPnL(List<Integer> layBets, int batsmanScored) 
	{
		int betPnL = 0 ;
		
		if(layBets.size()>1 && !isSecondBetMoreThanDouble.apply(layBets.get(0), layBets.get(1)) )
		{
			betPnL = -100;
		}
		
		if(layBets.size()>1 && isSecondBetMoreThanDouble.apply(layBets.get(0), layBets.get(1)))
		{
			betPnL = (layBets.get(layBets.size()-1) > batsmanScored) ? 100 : getLossByAttempts(layBets.size());
		}
		
		
		if(layBets.size() == 1 )
		{
			betPnL = (layBets.get(0) > batsmanScored) ? 100 : -100;
		}
		
		return betPnL;
	}
	
	public static  int strategy2BetPnL(List<Integer> layBets, int batsmanScored) 
	{
		int betPnL = 0 ;
		
		if(layBets.size()>1 && !isSecondBetMoreThanDouble.apply(layBets.get(0), layBets.get(1)) )
		{
			betPnL = -100;
			
			betPnL = (batsmanScored >=layBets.get(1)) ? betPnL + 100 : betPnL - 100;
		}
		
		if(layBets.size()>1 && isSecondBetMoreThanDouble.apply(layBets.get(0), layBets.get(1)))
		{
			betPnL = (layBets.get(layBets.size()-1) > batsmanScored) ? 100 : getLossByAttempts(layBets.size());
		}
		
		
		if(layBets.size() == 1 )
		{
			betPnL = (layBets.get(0) > batsmanScored) ? 100 : -100;
		}
		
		return betPnL;
	}
	
	public static  int strategy3BetPnL(List<Integer> layBets, int batsmanScored) 
	{
		layBets = layBets.stream().limit(3).collect(Collectors.toList());
		
		int betPnL = 0 ;
		
		if(layBets.size() == 1 )
		{
			betPnL = (layBets.get(0) > batsmanScored) ? 100 : -100;
		}
		else
		{
			int maxLaybet = layBets.stream().max(Integer::compare).get();
			
			betPnL = (maxLaybet > batsmanScored) ? 100 : getLossByAttempts(layBets.size());
			
		}
		
		return betPnL;
	}
	
	public static  int getLossByAttempts(int attemptsMade)
	{		
		int baseAmount = 100;
		int preBet = baseAmount;
		
		for (int i = 0; i < attemptsMade -1 ; i++) 
		{
			baseAmount = baseAmount + (preBet * 2);
			preBet =  preBet * 2;
		}
		
		return -(baseAmount);
	}
	
	/**
	 * 
	 * STRATEGY 2 + only 2X
	 */
	public static  int strategy4BetPnL(List<Integer> layBets, int batsmanScored) 
	{
		layBets = layBets.stream().limit(2).collect(Collectors.toList());
		
		int betPnL = 0 ;
		
		if(layBets.size()>1 && !isSecondBetMoreThanDouble.apply(layBets.get(0), layBets.get(1)) )
		{
			betPnL = -100;
			
			betPnL = (batsmanScored >=layBets.get(1)) ? betPnL + 100 : betPnL - 100;
		}
		
		if(layBets.size()>1 && isSecondBetMoreThanDouble.apply(layBets.get(0), layBets.get(1)))
		{
			betPnL = (layBets.get(layBets.size()-1) > batsmanScored) ? 100 : getLossByAttempts(layBets.size());
		}
		
		
		if(layBets.size() == 1 )
		{
			betPnL = (layBets.get(0) > batsmanScored) ? 100 : -100;
		}
		
		return betPnL;
	}
	
	/**
	 * 
	 * only first bet NO
	 */
	public static  int strategy5BetPnL(List<Integer> layBets, int batsmanScored) 
	{	
		int betPnL = 0 ;
		
		if(layBets.size() > 0)
		{
			betPnL = (layBets.get(0) > batsmanScored) ? 100 : -100;
		}
		
		return betPnL;
	}
}
