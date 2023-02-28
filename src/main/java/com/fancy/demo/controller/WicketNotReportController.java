package com.fancy.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fancy.demo.models.FancyPlayerRunAnalysis;
import com.fancy.demo.models.PlayerRun;
import com.fancy.demo.models.WicketNotMatchSummaryPnL;
import com.fancy.demo.repository.PlayerRunRepository;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/wktnot-report")
public class WicketNotReportController 
{
	
	@Resource(name = "playerRunRepository")
	PlayerRunRepository playerRunRepository;

	
	@Autowired
	FancyPlayerRunDataController fancyPlayerRunDataController;
	
	Function<LocalDateTime, String> dateString = (d) -> DateTimeFormatter.ofPattern("dd-MMM-yyyy",Locale.ENGLISH).format(d);
	
	@RequestMapping("/matchpnl/{inning}")
	@ResponseBody
	public Map<String, List<WicketNotMatchSummaryPnL>> getMatchPnLByStrategies(@PathVariable(name = "inning", required = false) String inning)
	{
		Map<String, List<WicketNotMatchSummaryPnL>> data = new HashMap<>();
		
		
		List<WicketNotMatchSummaryPnL> sumaryList = new ArrayList<>();
		
		List<PlayerRun> allMatches = fancyPlayerRunDataController.getAllMatches();
		
		log.info("matches found "+allMatches.size());
		
		inning = (null!=inning) ? inning : "0";
		
		for(PlayerRun match : allMatches)
		{
			List<FancyPlayerRunAnalysis> matchAnalysis = fancyPlayerRunDataController.analyzeMatch(match.getMatchId(),Integer.parseInt(inning));
			
			if(!CollectionUtils.isEmpty(matchAnalysis))
			{
				WicketNotMatchSummaryPnL summary = new WicketNotMatchSummaryPnL();
				
				System.out.println(match.getMatchId());
				System.out.println(match.getMatchTitle());
				
				summary.setMatchId(match.getMatchId());
				summary.setMatchTitle(match.getMatchTitle());
				summary.setStrategy1PnL(matchAnalysis.stream().map(r->r.getStartegy1BetPnL()).collect((Collectors.summingInt(Integer::intValue))));
				summary.setStrategy2PnL(matchAnalysis.stream().map(r->r.getStartegy2BetPnL()).collect((Collectors.summingInt(Integer::intValue))));
				summary.setStrategy3PnL(matchAnalysis.stream().map(r->r.getStartegy3BetPnL()).collect((Collectors.summingInt(Integer::intValue))));
				summary.setStrategy4PnL(matchAnalysis.stream().map(r->r.getStartegy4BetPnL()).collect((Collectors.summingInt(Integer::intValue))));
				summary.setStrategy5PnL(matchAnalysis.stream().map(r->r.getStartegy5BetPnL()).collect((Collectors.summingInt(Integer::intValue))));
				summary.setMatchDate(dateString.apply(matchAnalysis.stream().findAny().get().getCreateDateTime()));
				sumaryList.add(summary);
			}
			
		}
		
		
		
		data.put("data", sumaryList);
		
		return data;
	}	
	
	@RequestMapping("/summary/{inning}")
	public String getSummaryPage(@PathVariable(name = "inning", required = false) String inning,Model model)
	{
		
		return "wktNotReportSummaryPage";
	}

}
