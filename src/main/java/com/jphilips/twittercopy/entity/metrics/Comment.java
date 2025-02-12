package com.jphilips.twittercopy.entity.metrics;

import com.jphilips.twittercopy.entity.MyUser;
import com.jphilips.twittercopy.entity.Tweet;

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
@Table(name="metric_comments")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "tweet_id")
	private Tweet tweet;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private MyUser myUser;
	
	@Column(nullable = false)
	private String comment;
	

}
