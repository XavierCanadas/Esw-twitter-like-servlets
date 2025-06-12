package service;

import java.util.List;
import java.util.Optional;

import repository.LikeTweetRepository;
import model.Tweet;
import model.User;

public class LikeTweetService {

    private LikeTweetRepository likeTweetRepository;

    public LikeTweetService(LikeTweetRepository likeTweetRepository) {
        this.likeTweetRepository = likeTweetRepository;
    }

    /**
     * This method toggles the like status of a tweet for a user.
     * If the user has already liked the tweet, it removes the like.
     * If the user has not liked the tweet, it adds a like.
     *
     * @param tweetId The ID of the tweet to like or unlike.
     * @param userId  The ID of the user liking or unliking the tweet.
     */
    public void likeTweet(Integer tweetId, Integer userId) {
        if (!likeTweetRepository.isLiked(tweetId, userId)) {
            likeTweetRepository.likeTweet(tweetId, userId);
        } else {
            likeTweetRepository.removeLike(tweetId, userId);
        }
    }


    // this method is not used.
    public void removeLike(Integer tweetId, Integer userId) {
        likeTweetRepository.removeLike(tweetId, userId);

    }

    // this method is not used.
    public boolean isLiked(Integer tweetId, Integer userId) {
        return likeTweetRepository.isLiked(tweetId, userId);
    }

    public List<Tweet> getLikedTweetsByUser(Integer userId, Integer start, Integer end) {
        Optional<List<Tweet>> tweets = likeTweetRepository.findLikedTweetsByUser(userId, start, end);
        if (tweets.isPresent())
            return tweets.get();
        return null;
    }

    public Integer getLikesCount(Integer tweetId) {
        return likeTweetRepository.getLikesCount(tweetId);
    }
}
