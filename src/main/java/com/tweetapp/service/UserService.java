package com.tweetapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.tweetapp.entity.DisplayUserPojo;
import com.tweetapp.entity.Login;
import com.tweetapp.entity.UserTweet;
import com.tweetapp.entity.Users;
import com.tweetapp.repository.UsersDisplayRepo;
import com.tweetapp.repository.UsersRepo;

@Service
public class UserService {

	@Autowired
	UsersRepo usersRepo;
	@Autowired
	UsersDisplayRepo userDisplayRepo;
	@Autowired
	MongoOperations mongoOperations;
	
	public Users registerUser(Users user) {
		// TODO Auto-generated method stub
		
		return usersRepo.save(user);
		
	}
	public List<DisplayUserPojo> getUserDetails() {
		// TODO Auto-generated method stub
		List<Users> obj = usersRepo.findAll(); 
		List<DisplayUserPojo> detailsList = new ArrayList<>();
		
		for(int i=0;i<obj.size();i++)
		{
			DisplayUserPojo pojo = new DisplayUserPojo();
			pojo.setFirstName(obj.get(i).getFirstName());
			pojo.setLastName(obj.get(i).getLastName());
			pojo.setEmail(obj.get(i).getEmail());
			pojo.setUserId(obj.get(i).getUserId());
			pojo.setContactNumber(obj.get(i).getContactNumber());
			detailsList.add(pojo);
		}
		return detailsList;
	}
	public DisplayUserPojo getUser(String userId) {
		// TODO Auto-generated method stub
		return userDisplayRepo.findByUserId(userId);
	}
	public void updateUser(Users user) {
		// TODO Auto-generated method stub
		Query query = new Query();
        
        query.addCriteria(Criteria.where("userId").is(user.getUserId()));
        query.fields().include("userId");
        Update update = new Update();
        update.set("firstName",user.getFirstName());
        update.set("lastName",user.getLastName());
        update.set("contactNumber",user.getContactNumber());
        mongoOperations.updateFirst(query, update, UserTweet.class);
	}
	public Boolean checkCredentials(Login login) {
		// TODO Auto-generated method stub
		boolean status = false;
		List<Users> user = usersRepo.findAll();
		for(int i=0;i<user.size();i++)
		{
		if(user.get(i).getPassword().equals(login.getPassword())&&user.get(i).getUserId().equals(login.getUserId()))
		{
			status = true;
		}
		}
		
		return status;
	}
	public DisplayUserPojo getUserDetail(String userId) {
		// TODO Auto-generated method stub
		
		DisplayUserPojo user = userDisplayRepo.findByUserId(userId);
		
		return user;
	}
	
	public void updateUserPassword(Login login) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				Query query = new Query();
		        
		        query.addCriteria(Criteria.where("userId").is(login.getUserId()));
		        query.fields().include("userId");
		        Update update = new Update();
		        update.set("password",login.getPassword());
		        
		        mongoOperations.updateFirst(query, update, Users.class);
	}
	public Users findUserById(String userId) {
		// TODO Auto-generated method stub
		Users user = new Users();
		try {
			user =  usersRepo.findByUserId(userId);
		}
		catch (Exception e) {
			user.setUserId(null);
		}
		finally {
			
		}
		return user;
	}

}
