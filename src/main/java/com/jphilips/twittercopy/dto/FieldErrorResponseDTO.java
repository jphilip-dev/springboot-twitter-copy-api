package com.jphilips.twittercopy.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class FieldErrorResponseDTO {

	private Map<String, String> errors;

	public void addError(String key, String value) {
		if (errors == null) {
			errors = new HashMap<String, String>();
		}
		errors.put(key, value);
	}
}
