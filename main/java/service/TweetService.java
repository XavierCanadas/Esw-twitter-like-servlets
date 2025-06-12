package service;

import repository.TweetRepository;
import java.util.List;
import java.util.Optional;
import model.Tweet;

public class TweetService {
	
	private TweetRepository tweetRepository;
	
	public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }
	
	public void add(Tweet tweet) {
		tweetRepository.save(tweet);	
	}
	
	public void delete(Integer id, Integer uid) {
		tweetRepository.delete(id, uid);
	}

	public List<Tweet> getTweetsByUser(Integer uid, Integer start, Integer end) {
		Optional<List<Tweet>> tweets = tweetRepository.findByUser(uid,start,end);
    	if (tweets.isPresent())
    	    return tweets.get();
        return null;
	}


}
