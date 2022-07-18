package com.simple.simpleapp.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simple.simpleapp.Model.Ratting;
import Embed.RattingId;
import org.springframework.stereotype.Repository;

@Repository
public interface RattingRepo extends JpaRepository<Ratting, RattingId>{

}
