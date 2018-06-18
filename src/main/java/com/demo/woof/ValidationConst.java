package com.demo.woof;

public class ValidationConst {
	
	/*
	 * Error messages/codes handling will be better designed for a real application. 
	 */
	
	public static final String FIELD_ERR_NOT_BLANK = "Field cannot be empty";
	public static final String FIELD_ERR_NOT_NULL = "Field required";
	public static final String FIELD_ERR_INVALID_EMAIL_FORMAT = "Invalid email format";
	public static final String FIELD_ERR_INVALID_PHONE_FORMAT = "Invalid phone format";
	public static final String FIELD_ERR_INVALID_URL_FORMAT = "Invalid URL format";
	public static final String FIELD_ERR_REVIEW_TOO_LONG = "Review exceeds number of characters allowed";	
	public static final String FIELD_ERR_NUM_CHAR_RANGE = "Number of characters not in range";	
	public static final String FIELD_ERR_INVALID_RATING = "Invalid rating. Expected: 1-5 inclusively";	
	
	public static final String REGEX_PHONE="\\+1\\d{10}";
	public static final String REGEX_URL="(http|https)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]+";
	
	public static final int REVIEW_CHAR_MAX = 1500;
	public static final int NAME_CHAR_MAX = 30;
	public static final int NAME_CHAR_MIN = 2;
	public static final int RATING_MAX = 5;
	public static final int RATING_MIN = 1;
	
	public static final String REQUEST_BODY_ERR = "Request Body Error";
	public static final String EMPTY_REQUEST_BODY_ERR = "Request body is missing";
	public static final String MESSAGE_BODY_UNREADABLE_ERR = "Message body unreadable";
}
