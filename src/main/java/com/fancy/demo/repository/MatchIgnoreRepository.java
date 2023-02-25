package com.fancy.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.fancy.demo.models.MatchIgnore;


@Component("matchIgnoreRepository")
public interface MatchIgnoreRepository  extends JpaRepository<MatchIgnore, Long>
{

}
