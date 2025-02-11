package com.jphilips.twittercopy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.jphilips.twittercopy.dto.TweetCommentRequestDTO;
import com.jphilips.twittercopy.dto.TweetCommentResponseDTO;
import com.jphilips.twittercopy.dto.TweetLikeResponseDTO;
import com.jphilips.twittercopy.dto.TweetRequestDTO;
import com.jphilips.twittercopy.dto.TweetResponseDTO;
import com.jphilips.twittercopy.dto.TweetShareResponseDTO;
import com.jphilips.twittercopy.dto.mapper.TweetCommentMapper;
import com.jphilips.twittercopy.dto.mapper.TweetLikeMapper;
import com.jphilips.twittercopy.dto.mapper.TweetMapper;
import com.jphilips.twittercopy.dto.mapper.TweetShareMapper;
import com.jphilips.twittercopy.entity.MyUser;
import com.jphilips.twittercopy.entity.Tweet;
import com.jphilips.twittercopy.entity.TweetMetrics;
import com.jphilips.twittercopy.entity.metrics.Comment;
import com.jphilips.twittercopy.entity.metrics.Like;
import com.jphilips.twittercopy.entity.metrics.Share;
import com.jphilips.twittercopy.exception.custom.TweetException;
import com.jphilips.twittercopy.exception.custom.TweetNotFoundException;
import com.jphilips.twittercopy.repository.CommentRepository;
import com.jphilips.twittercopy.repository.LikeRepository;
import com.jphilips.twittercopy.repository.ShareRepository;
import com.jphilips.twittercopy.repository.TweetRepository;

import jakarta.transaction.Transactional;

@Service
public class TweetService {

	private TweetRepository tweetRepository;
	private LikeRepository likeRepository;
	private CommentRepository commentRepository;
	private ShareRepository shareRepository;

	public TweetService(TweetRepository tweetRepository, LikeRepository likeRepository,
			CommentRepository commentRepository, ShareRepository shareRepository) {
		this.tweetRepository = tweetRepository;
		this.likeRepository = likeRepository;
		this.commentRepository = commentRepository;
		this.shareRepository = shareRepository;
	}

	public Page<TweetResponseDTO> getAllMyTweets(Authentication authentication, Pageable pageable) {
		Page<Tweet> tweetsPage = tweetRepository.findByMyUserOrderByIdAsc(findByAuth(authentication), pageable);

		return tweetsPage.map(tweet -> TweetMapper.toDto(tweet));
	}

	public TweetResponseDTO getTweetById(Long id) {
		return TweetMapper.toDto(findById(id));
	}

	public Page<TweetResponseDTO> getTweetsFromFollowing(Authentication authentication, Pageable pageable) {
		MyUser myUser = findByAuth(authentication);

		Page<Tweet> tweetsPage = tweetRepository.findTweetsFromFollowing(myUser.getId(), pageable);

		return tweetsPage.map(tweet -> TweetMapper.toDto(tweet));

	}

	@Transactional
	public void addTweet(Authentication authentication, TweetRequestDTO tweetRequestDTO) {
		MyUser myUser = findByAuth(authentication);

		Tweet tweet = new Tweet();
		TweetMetrics metrics = new TweetMetrics();
		metrics.setCommentCount(0L);
		metrics.setLikeCount(0L);
		metrics.setShareCount(0L);

		tweet.setMyUser(myUser);
		tweet.setTweet(tweetRequestDTO.getTweet());
		tweet.setMetrics(metrics);
		tweet.setScore(0);

		tweetRepository.save(tweet);
	}

	@Transactional
	public void deleteTweet(Authentication authentication, Long id) {
		MyUser myUser = findByAuth(authentication);

		Tweet tweet = findById(id);

		if (tweet.getMyUser() != myUser) {
			throw new TweetException("You do not have permission to delete this tweet");
		}

		tweetRepository.delete(tweet);

	}

	@Transactional
	public void likeTweet(Authentication authentication, Long id) {
		MyUser myUser = findByAuth(authentication);

		Like likeExist = likeRepository.findByTweet_idAndMyUser(id, myUser).orElse(null);

		if (likeExist == null) {
			Like like = new Like();

			Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new TweetNotFoundException());
			tweet.addLike();

			like.setMyUser(myUser);
			like.setTweet(tweet);
			likeRepository.save(like);

		}
	}

	@Transactional
	public void commentTweet(Authentication authentication, Long id, TweetCommentRequestDTO tweetCommentRequestDTO) {
		MyUser myUser = findByAuth(authentication);

		Comment comment = new Comment();

		Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new TweetNotFoundException());
		tweet.addComment();

		comment.setMyUser(myUser);
		comment.setTweet(tweetRepository.findById(id).orElseThrow(() -> new TweetNotFoundException()));
		comment.setComment(tweetCommentRequestDTO.getComment());

		commentRepository.save(comment);
	}

	@Transactional
	public void shareTweet(Authentication authentication, Long id) {
		MyUser myUser = findByAuth(authentication);

		Share share = shareRepository.findByTweet_idAndMyUser(id, myUser).orElse(null);

		if (share == null) {
			Share newShare = new Share();

			Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new TweetNotFoundException());
			tweet.addShare();

			newShare.setMyUser(myUser);
			newShare.setTweet(tweetRepository.findById(id).orElseThrow(() -> new TweetNotFoundException()));

			shareRepository.save(newShare);
		}
	}

	public Page<TweetLikeResponseDTO> getLikes(Authentication authentication, Long id, Pageable pageable) {
		//MyUser myUser = findByAuth(authentication);

		Page<Like> likes = likeRepository.findByTweet_idOrderByIdAsc(id, pageable);
		
		return likes.map(like -> TweetLikeMapper.toDto(like));

	}
	
	public Page<TweetCommentResponseDTO> getComments(Authentication authentication, Long id, Pageable pageable) {
		//MyUser myUser = findByAuth(authentication);

		Page<Comment> comments = commentRepository.findByTweet_idOrderByIdAsc(id, pageable);
		
		return comments.map(comment -> TweetCommentMapper.toDto(comment.getMyUser(), comment));

	}
	
	public Page<TweetShareResponseDTO> getShares(Authentication authentication, Long id, Pageable pageable) {
		//MyUser myUser = findByAuth(authentication);

		Page<Share> shares = shareRepository.findByTweet_idOrderByIdAsc(id, pageable);
		
		return shares.map(share -> TweetShareMapper.toDto(share));

	}
	

	// helper methods

	private Tweet findById(Long id) {
		return tweetRepository.findById(id).orElseThrow(() -> new TweetNotFoundException());
	}

	private MyUser findByAuth(Authentication authentication) {
		return (MyUser) authentication.getPrincipal();
	}

}
