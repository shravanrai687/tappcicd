package com.tweetapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.entity.Like;
import com.tweetapp.entity.Login;
import com.tweetapp.entity.TweetReply;
import com.tweetapp.entity.UserTweet;
import com.tweetapp.kafka.Producer;
import com.tweetapp.service.TweetService;

//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
@RestController
public class TweetController {

	@Autowired
	TweetService tweetService;
	public Producer producer;

	Logger log = LoggerFactory.getLogger(TweetController.class);

	@Autowired
	TweetController(Producer producer) {
		this.producer = producer;

	}

	@GetMapping("/connectTweet")
	public String Hello() {

		return "Welcome to Tweet";

	}

	@PostMapping(value = "/api/v1.0/tweets/add")
	public void postTweetReply(@Valid @RequestBody UserTweet userTweet) {
		log.info("Adding the tweet");
		UserTweet result = tweetService.postUserTweet(userTweet);

	}

	@DeleteMapping("/api/v1.0/tweets/delete/{tweetId}")
	public String DeleteTweet(@PathVariable String tweetId) {
		log.info("Deleting all the tweets");
		tweetService.deleteTweet(tweetId);
		return "Deleted";
	}

	@PostMapping("/api/v1.0/tweets/myTweet")
	public List<UserTweet> getUserTweet(@RequestBody Login login) {
		log.info("Getting the  user tweet");
		return tweetService.getUserTweet(login.getUserId());
	}

	@GetMapping("/api/v1.0/tweets/all")
	public List<UserTweet> getAllTweets() {
		log.info("Getting all the tweets");
		List<UserTweet> obj = tweetService.getAllTweet();
		return obj;
	}

	@PostMapping("/api/v1.0/tweets/update")
	public void updateTweet(@RequestBody UserTweet userTweet) {
		log.info("Upating the tweets");
		tweetService.updateTweet(userTweet);

	}

	@PostMapping(value = "/api/v1.0/tweets/addLike")
	public Like getLikeDetails(@RequestBody Like like) {
		log.info("Liking the tweets");
		Like result = tweetService.postLike(like);

		return result;
	}

	@GetMapping("/api/v1.0/tweets/getLikes")
	public List<Like> getAllLikes() {
		log.info("Getting all the likes of a tweet");
		return tweetService.getAllLikes();
	}

	@GetMapping("/publish")
	public String sendMessage(@PathVariable String message) {
		this.producer.sendMessage(message);
		return "connection established";

	}

	@PostMapping(value = "/api/v1.0/tweets/reply")
	public String postTweetReply(@RequestBody TweetReply tweetReply) {
		String response;
		log.info("Replying to the tweets");
		TweetReply result = tweetService.postTweetReply(tweetReply);
		if (result != null) {
			response = "Details registered successfully";
		} else {
			response = "Details not registered successfully";
		}

		return response;
	}

	@PostMapping("/api/v1.0/tweets/getReply")
	public List<TweetReply> getAllReply(@RequestBody UserTweet usertweet) {
		log.info("Getting all the replies of a tweet");
		List<TweetReply> replies = tweetService.getAllReply(usertweet.getTweetId());
		return replies;
	}
}
