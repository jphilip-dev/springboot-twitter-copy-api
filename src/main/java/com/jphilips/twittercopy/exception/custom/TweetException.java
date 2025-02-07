package com.jphilips.twittercopy.exception.custom;

public class TweetException extends RuntimeException{

	private static final long serialVersionUID = 2424869382748592976L;
	
	public TweetException() {
		super();
	}
	
	public TweetException(String message) {
		super(message);
	}


}
