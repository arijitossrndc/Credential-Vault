package com.epam.pwt.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.epam.pwt.entity.Group;
import com.epam.pwt.entity.User;

@Transactional
public interface GroupRepository extends JpaRepository<Group,Integer>{
	
	
	Optional<Group> findById(Integer groupId);
	
	List<Group> findByUser(final User user);
	
	@Query("select case when count(g)> 0 then true else false end from Group g where lower(g.groupName) like lower(:groupName) and g.user.id=:id")
	boolean existsGroupWithGroupNameAndUserId(@Param("groupName") String groupName,@Param("id") int id);
}


