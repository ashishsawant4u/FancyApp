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
public class LayBetTestMatchWisePnL
{
	
	private String matchTitle;
	
	private Integer totalPnL;

	private Integer totalBets;
	
	private Long matchRef;
}

