package com.tweetapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.tweetapp.entity.Like;
import com.tweetapp.entity.TweetReply;
import com.tweetapp.entity.UserTweet;
import com.tweetapp.repository.LikeRepo;
import com.tweetapp.repository.ReplyTweetRepo;
import com.tweetapp.repository.TweetRepo;

@Service
public class TweetService {

	@Autowired
	LikeRepo likerepo;
	@Autowired
	ReplyTweetRepo replyTweetRepo;
	@Autowired
	TweetRepo tweetRepo;
	@Autowired
	MongoOperations mongoOperations;
	
    
	public Like postLike(Like like) {
		// TODO Auto-generated method stub
		
		return  likerepo.save(like);
	}

	public List<Like> getAllLikes() {
		// TODO Auto-generated method stub
		
		
		return likerepo.findAll();
	}

	public TweetReply postTweetReply(TweetReply tweetReply) {
		// TODO Auto-generated method stub
		return replyTweetRepo.save(tweetReply);
	}

	public List<TweetReply> getAllReply(String tweetId) {
		// TODO Auto-generated method stub
		return replyTweetRepo.findByTweetId(tweetId);
	}

	public UserTweet postUserTweet(UserTweet userTweet) {
		// TODO Auto-generated method stub
		return tweetRepo.save(userTweet);
	}

	public List<UserTweet> getUserTweet(String userId) {
		// TODO Auto-generated method stub
		return tweetRepo.findByUserId(userId);
	}

	public List<UserTweet> getAllTweet() {
		// TODO Auto-generated method stub
		return tweetRepo.findAll();
	}

	public void updateTweet(UserTweet usertweet) {
		// TODO Auto-generated method stub
		Query query = new Query();
	        
	        query.addCriteria(Criteria.where("tweetId").is(usertweet.getTweetId()));
	        query.fields().include("tweetId");
	        Update update = new Update();
	        update.set("tweetText",usertweet.getTweetText());
	        update.set("tags", usertweet.getTags());
	        mongoOperations.updateFirst(query, update, UserTweet.class);
	        
	       
	    
	}

	public void deleteTweet(String tweetId) {
		// TODO Auto-generated method stub
		tweetRepo.deleteByTweetId(tweetId);
		
	}


}
