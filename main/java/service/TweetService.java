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
        tweetRepository.save(tweet);
    }

    public void addComment(Tweet tweet) {
        tweetRepository.saveComment(tweet);
    }

    public void delete(Integer id, Integer uid) {
        tweetRepository.delete(id, uid);
    }

    public List<Tweet> getTweetsByUser(Integer uid, Integer start, Integer end, HttpServletRequest request) {
        Optional<List<Tweet>> tweets = tweetRepository.findByUser(uid, start, end);
        if (tweets.isPresent()) {
            for (Tweet tweet : tweets.get()) {
                tweet.setProfilePictureUrl(Common.setPictureUrl(tweet.getProfilePictureUrl(), request));
            }
        }
        return tweets.orElse(Collections.emptyList());
    }

    public List<Tweet> getLatestTweets(HttpServletRequest request) {
        Optional<List<Tweet>> tweets = tweetRepository.getLatestTweets();
        if (tweets.isPresent()) {
            for (Tweet tweet : tweets.get()) {
                tweet.setProfilePictureUrl(Common.setPictureUrl(tweet.getProfilePictureUrl(), request));
            }
        }
        return tweets.orElse(Collections.emptyList());
    }

    public List<Tweet> getFollowingTweets(Integer uid, Integer start, Integer end, HttpServletRequest request) {
        Optional<List<Tweet>> tweets = tweetRepository.getFollowingTweets(uid, start, end);
        if (tweets.isPresent()) {
            for (Tweet tweet : tweets.get()) {
                tweet.setProfilePictureUrl(Common.setPictureUrl(tweet.getProfilePictureUrl(), request));
            }
        }
        return tweets.orElse(Collections.emptyList());

    }

    public List<Tweet> getPolisTweets(Integer uid, Integer start, Integer end, HttpServletRequest request) {
        Optional<List<Tweet>> tweets = tweetRepository.getPolisTweets(uid, start, end);
        if (tweets.isPresent()) {
            for (Tweet tweet : tweets.get()) {
                tweet.setProfilePictureUrl(Common.setPictureUrl(tweet.getProfilePictureUrl(), request));
            }
        }
        return tweets.orElse(Collections.emptyList());
    }

    // get the comments of a tweet by its id
    public List<Tweet> getCommentsByTweetId(Integer tweetId, Integer currentUserId, Integer start, Integer end, HttpServletRequest request) {
        Optional<List<Tweet>> tweets = tweetRepository.getCommentsByTweetId(tweetId, currentUserId, start, end);

        if (tweets.isPresent()) {
            for (Tweet tweet : tweets.get()) {
                tweet.setProfilePictureUrl(Common.setPictureUrl(tweet.getProfilePictureUrl(), request));
            }
        }

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
}
