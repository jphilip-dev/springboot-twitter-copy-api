package com.jphilips.twittercopy.exception.custom;

public class TweetNotFoundException extends TweetException{

	private static final long serialVersionUID = 8502044832830959577L;
	
	public TweetNotFoundException() {
		super("Tweet Not Found"); 
	}

}
