package com.demo.woof.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.demo.woof.ValidationConst;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "stays")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Stay {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable=false, updatable=false)
	private long id;
	
	@NotNull(message=ValidationConst.FIELD_ERR_NOT_NULL)
	private LocalDate startDate;
	
	@NotNull(message=ValidationConst.FIELD_ERR_NOT_NULL)
	private LocalDate endDate;

	@Valid
	@NotNull(message=ValidationConst.FIELD_ERR_NOT_NULL)
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="sitter_id")
	private Sitter sitter;	
	
	@Valid
	@NotNull(message=ValidationConst.FIELD_ERR_NOT_NULL)
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="owner_id")
	private Owner owner;
	
	/*
	 * This value is not necessarily the same as the owner's dogs field
	 */
	@NotNull
	private String dogs;

	@Size(max=ValidationConst.REVIEW_CHAR_MAX,
			  message=ValidationConst.FIELD_ERR_REVIEW_TOO_LONG)
		private String review;

	@Min(value=ValidationConst.RATING_MIN,
			  message=ValidationConst.FIELD_ERR_INVALID_RATING)
	@Max(value=ValidationConst.RATING_MAX,
		  message=ValidationConst.FIELD_ERR_INVALID_RATING)
	private int rating;	
	
	
	public Stay() {}
	
	public Stay(LocalDate startDate, LocalDate endDate, Owner owner, String dogs, Sitter sitter, int rating) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.owner = owner;
		this.dogs = dogs;
		this.sitter = sitter;
		this.rating = rating;
	}
	
	public Stay(LocalDate startDate, LocalDate endDate, Owner owner, String dogs, Sitter sitter, int rating, String review) {
		this(startDate, endDate, owner, dogs, sitter, rating);
		this.review = review;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	public String getDogs() {
		return dogs;
	}
	public void setDogs(String dogs) {
		this.dogs = dogs;
	}
	public Sitter getSitter() {
		return sitter;
	}
	public void setSitter(Sitter sitter) {
		this.sitter = sitter;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
}
