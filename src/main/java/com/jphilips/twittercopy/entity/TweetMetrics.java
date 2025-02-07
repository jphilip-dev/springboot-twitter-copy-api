package com.jphilips.twittercopy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tweets_metrics")
public class TweetMetrics {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "tweet_id")
	private Tweet tweet;
	
	@Column(nullable = false)
	private Long likeCount;
	
	@Column(nullable = false)
	private Long commentCount;
	
	@Column(nullable = false)
	private Long shareCount;
	
	
}
