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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "f_old_scoreboards")
public class OldScoreBoards 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	
	@NotNull
	private String matchId;
	
	@NotNull
	private String matchTitle;
	
	@NotNull
	private Integer inning;
	
	@NotNull
	private String batsman;
	
	@NotNull
	private Integer batsmanScore;
	
	@CreationTimestamp
    private LocalDateTime createDateTime;
}
