package com.fancy.demo.commons;

import java.util.List;

public class NameUtils 
{
	public static String getSirname(String playerName)
	{
		List<String> playerNameSplit = null;
		
		if(playerName.contains("-"))
		{
			playerNameSplit = List.of(playerName.split("-"));
		}
		else
		{
			playerNameSplit = List.of(playerName.split(" "));
		}
		
		String sirname = playerNameSplit.get(playerNameSplit.size()-1);
		
		return sirname;
		
		
	}
	
	public static String getFirstName(String playerName)
	{
		List<String> playerNameSplit = List.of(playerName.split(" "));
		
		String sirname = playerNameSplit.get(0);
		
		return sirname;
	}
	
	public static List<String> getWordsInName(String playerName)
	{
		
		String regex = "([^a-zA-Z']+)'*\\1*";
		List<String> playerNameSplit = List.of(playerName.split(regex));
		
		return playerNameSplit;
	}
}
