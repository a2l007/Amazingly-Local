package com.iu.amazelocal.models;


import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UsersDao  extends CrudRepository<Users, Long>  {
  
	 // public Users findByEmail(String email);

}