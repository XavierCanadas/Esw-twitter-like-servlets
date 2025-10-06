package service;

import jakarta.servlet.http.HttpServletRequest;
import repository.TweetRepository;

import java.net.http.HttpRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import model.Tweet;
import util.Common;

public class TweetService {

    private TweetRepository tweetRepository;

    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public void add(Tweet tweet) {
        int id = tweetRepository.save(tweet);
        tweet.setId(id);
        saveWordTweet(tweet);
    }

    public void addComment(Tweet tweet) {
        tweetRepository.saveComment(tweet);
    }

    public void delete(Integer id, Integer uid) {
        tweetRepository.delete(id, uid);
    }

    public List<Tweet> getTweetsByUser(Integer uid, Integer start, Integer end, HttpServletRequest request) {
        Optional<List<Tweet>> tweets = tweetRepository.findByUser(uid, start, end);
        updateTweetsProfilePictureUrl(tweets, request);
        return tweets.orElse(Collections.emptyList());
    }

    public List<Tweet> getLatestTweets(HttpServletRequest request) {
        Optional<List<Tweet>> tweets = tweetRepository.getLatestTweets();
        updateTweetsProfilePictureUrl(tweets, request);
        return tweets.orElse(Collections.emptyList());
    }

    public List<Tweet> getFollowingTweets(Integer uid, Integer start, Integer end, HttpServletRequest request) {
        Optional<List<Tweet>> tweets = tweetRepository.getFollowingTweets(uid, start, end);
        updateTweetsProfilePictureUrl(tweets, request);
        return tweets.orElse(Collections.emptyList());

    }

    public List<Tweet> getPolisTweets(Integer uid, Integer start, Integer end, HttpServletRequest request) {
        Optional<List<Tweet>> tweets = tweetRepository.getPolisTweets(uid, start, end);
        updateTweetsProfilePictureUrl(tweets, request);
        return tweets.orElse(Collections.emptyList());
    }

    // get the comments of a tweet by its id
    public List<Tweet> getCommentsByTweetId(Integer tweetId, Integer currentUserId, Integer start, Integer end, HttpServletRequest request) {
        Optional<List<Tweet>> tweets = tweetRepository.getCommentsByTweetId(tweetId, currentUserId, start, end);

        updateTweetsProfilePictureUrl(tweets, request);

        return tweets.orElse(Collections.emptyList());
    }

    public Tweet getTweetById(Integer tweetId, Integer currentUserId, HttpServletRequest request) {
        Optional<Tweet> tweetOptional = tweetRepository.getTweetById(tweetId, currentUserId);
        if (tweetOptional.isPresent()) {
            Tweet tweet = tweetOptional.get();
            tweet.setProfilePictureUrl(Common.setPictureUrl(tweet.getProfilePictureUrl(), request));
            return tweet;

        }
        return null;
    }

    private void updateTweetsProfilePictureUrl(Optional<List<Tweet>> tweets, HttpServletRequest request) {
        if (tweets.isPresent()) {
            for (Tweet tweet : tweets.get()) {
                tweet.setProfilePictureUrl(Common.setPictureUrl(tweet.getProfilePictureUrl(), request));
            }
        }
    }

    public void update(Tweet tweet) {
        tweetRepository.update(tweet);
    }

    public void saveWordTweet(Tweet tweet) {
        if (tweet.getContent() == null || tweet.getContent().isEmpty()) {
            return;
        }
        String[] words = tweet.getContent().toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").split("\\s+");

        tweetRepository.saveWordTweet(words, tweet.getId());
    }

    public List<Tweet> searchTweetsByWord(String word, Integer currentUserId, int start, int end, HttpServletRequest request) {
        Optional<List<Tweet>> tweets = tweetRepository.searchTweetsByWord(word, currentUserId, start, end);
        if (tweets.isPresent()) {
            for (Tweet tweet : tweets.get()) {
                tweet.setProfilePictureUrl(Common.setPictureUrl(tweet.getProfilePictureUrl(), request));
            }
        }
        return tweets.orElse(Collections.emptyList());
    }
}
