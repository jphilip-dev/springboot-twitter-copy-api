package com.jphilips.twittercopy.entity.metrics;


import com.jphilips.twittercopy.entity.MyUser;
import com.jphilips.twittercopy.entity.Tweet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="metric_shares", uniqueConstraints = @UniqueConstraint(columnNames = {"tweet_id", "user_id"}))
public class Share {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "tweet_id")
	private Tweet tweet;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private MyUser myUser;
	

}
