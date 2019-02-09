package com.tavi.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.User;

public class DatabaseUtil {
	public static EntityManagerFactory entityManagerFactory;
	public static EntityManager entityManager;
	
	public void setup() throws Exception{
		entityManagerFactory=Persistence.createEntityManagerFactory("ProiectTPD");
		entityManager=entityManagerFactory.createEntityManager();
	}
	
	public void saveUser(User user){
		entityManager.persist(user);
	}
	
	public void startTransaction(){
		entityManager.getTransaction().begin();
	}
	
	public void commitTransaction(){
		entityManager.getTransaction().commit();
	}
	
	public void stopEntityManager(){
		entityManager.close();
	}
	
	public void getAllUsers(){
		List<User> users=entityManager.createNativeQuery("Select * from ProiectTPD.users",User.class).getResultList();
		
		for(User user:users){
			System.out.println("User: "+user.getIdusers()+" "+user.getUsername()+" "+user.getPassword());
		}
	}
}
