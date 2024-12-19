package com.api.cv.enums;

import lombok.Data;

public enum ErrorCode {
	
	A500("ERROR SYSTEM"),
	A400 ("BAD REQUEST"),
	A403 ("FORBIDEN");

	
	String value;
	
	ErrorCode(String value) {
		this.value=value;
	}

	public String getValue() {
		return value;
	}

}
