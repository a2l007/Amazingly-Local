package com.iu.amazelocal.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface LoginDao  extends CrudRepository<Users, Long>  {
  
	 // public Users findByEmail(String email);

}