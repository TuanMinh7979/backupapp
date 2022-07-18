package com.simple.simpleapp.Repo;

import com.simple.simpleapp.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, String> {


}
