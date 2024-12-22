package com.api.cv.enums;

public enum OfferStatus {
	
	ACTIVE("1","active");
	
	private String code;
	
	private String value;
	
	private OfferStatus(String code,String value) {
		// TODO Auto-generated constructor stub
		this.code = code;
		this.value = value;
	}

}
