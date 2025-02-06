package com.jphilips.twittercopy.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponseDTO {
	private LocalDateTime timeStamp;
	private int status;
	private String error;
	private String message;
}
