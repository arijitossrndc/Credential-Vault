package com.epam.pwt.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epam.pwt.entity.Account;
import com.epam.pwt.entity.Group;

@Transactional
@Repository
public interface AccountRepository extends JpaRepository<Account,Integer>{

	List<Account> findByGroup(final Group group);
	
	Optional<Account> findById(Integer accountId);
	
	@Query("select case when count(a)> 0 then true else false end from Account a where lower(a.accountName) like lower(:accountName) and a.group.groupId=:groupId")
	boolean existsAccountWithAccountNameInGroup(@Param("accountName") String accountName,@Param("groupId") int groupId);
	
	
}