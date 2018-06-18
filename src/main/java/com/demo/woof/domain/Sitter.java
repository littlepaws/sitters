package com.demo.woof.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.demo.woof.ValidationConst;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "sitters")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Sitter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable=false, updatable=false)
	private long id;
	
	@NotBlank(message=ValidationConst.FIELD_ERR_NOT_BLANK)
	@Size(min=ValidationConst.NAME_CHAR_MIN,
		  max=ValidationConst.NAME_CHAR_MAX,
		  message=ValidationConst.FIELD_ERR_NUM_CHAR_RANGE)
	private String name;
	
	@NotBlank(message=ValidationConst.FIELD_ERR_NOT_BLANK)
	@Email(message=ValidationConst.FIELD_ERR_INVALID_EMAIL_FORMAT)
	private String email;

	@NotBlank(message=ValidationConst.FIELD_ERR_NOT_BLANK)
	@Pattern(regexp=ValidationConst.REGEX_PHONE,
			 message=ValidationConst.FIELD_ERR_INVALID_PHONE_FORMAT)
	private String phone;
	
	@Pattern(regexp=ValidationConst.REGEX_URL,
			 message=ValidationConst.FIELD_ERR_INVALID_URL_FORMAT)
	private String imageUrl;
	
	@Column(precision=3, scale=2)
	private BigDecimal score;
	
	@Column(precision=3, scale=2)
	private BigDecimal avgRating;
	
	@Column(precision=3, scale=2)
	private BigDecimal rank;
	
	public Sitter() {
		avgRating = new BigDecimal(0.00);
	}
	
	public Sitter(String name, String email, String phone) {
		this();
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	public Sitter(String name, String email, String phone, String imageUrl) {
		this(name, email, phone);
		this.imageUrl = imageUrl;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	public BigDecimal getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(BigDecimal avgRating) {
		this.avgRating = avgRating;
	}
	public BigDecimal getRank() {
		return rank;
	}
	public void setRank(BigDecimal rank) {
		this.rank = rank;
	}
	
}
