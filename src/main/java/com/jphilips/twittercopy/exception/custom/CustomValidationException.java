package com.jphilips.twittercopy.exception.custom;

import org.springframework.validation.BindingResult;

public class CustomValidationException extends RuntimeException {
	
	private static final long serialVersionUID = 4273559820249280329L;
	
	private final BindingResult bindingResult;

    public CustomValidationException(BindingResult bindingResult) {
        super("Validation failed");
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}

