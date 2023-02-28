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
public class BetCounterTracker 
{
	 Integer betCount;
	 
	 Integer betCounter;
	 
	 Integer passCounter;
	 
	 Integer failCounter;
	 
	 Double winRate;
	 
}
