package com.jphilips.twittercopy.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tweets")
public class Tweet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String tweet;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private MyUser myUser;
	
	@Column(nullable = false)
	private Integer score;
	
	@OneToOne(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
	private TweetMetrics metrics;
	
	public void setMetrics(TweetMetrics metrics) {
		this.metrics = metrics;
		
		// back-reference
		metrics.setTweet(this);
	}
	
	// helper methods
	public void addLike() {
		metrics.setLikeCount(metrics.getLikeCount() + 1);
	}
	public void removeLike() {
		metrics.setLikeCount(metrics.getLikeCount() - 1);
	}
	
	
	public void addComment() {
		metrics.setCommentCount(metrics.getCommentCount() + 1);
	}
	public void removeComment() {
		metrics.setCommentCount(metrics.getCommentCount() - 1);
	}
	
	
	public void addShare() {
		metrics.setShareCount(metrics.getShareCount() + 1);
	}
	public void removeShare() {
		metrics.setShareCount(metrics.getShareCount() - 1);
	}
	
}
