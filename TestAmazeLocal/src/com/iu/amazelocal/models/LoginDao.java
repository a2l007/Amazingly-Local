package com.iu.amazelocal.models;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class LoginDao  {
  
	public void create(Users user) {
	    entityManager.persist(user);
	    return;
	}
	@PersistenceContext
	private EntityManager entityManager;
}