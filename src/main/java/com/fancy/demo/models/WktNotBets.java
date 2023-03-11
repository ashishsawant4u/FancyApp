package com.fancy.demo.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "f_wkt_not_bets")
public class WktNotBets 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	
	@NotNull
	private String matchTitle;
	
	@NotNull
	private Integer inning;
	
	@NotNull
	private String playerName;
	
	@NotNull
	private Integer layRun;
	
	@NotNull
	private Integer layOdd;

	@NotNull
	private Integer backRun;
	
	@NotNull
	private Integer backOdd;
	
	@NotNull
	private Integer playerRun;
	
	@NotNull
	private String matchId;
	
	@CreationTimestamp
    private LocalDateTime createDateTime;
}
