package com.ivs.map.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ivs.map.Model.Ratting;
import Embed.RattingId;
import org.springframework.stereotype.Repository;

@Repository
public interface RattingRepo extends JpaRepository<Ratting, RattingId>{

}
