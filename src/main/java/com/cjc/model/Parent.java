package com.cjc.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parent {
	@Id
	@NotNull(message = "Mobile Number is required")
	@Digits(integer = 10, fraction = 0, message = "Mobile Number Should be 10 Digit")
	private Long parentmobileno;

	@NotBlank(message = "Parent name is required")
	private String parentname;

	@NotBlank(message = "Address is required")
	private String adddress;

	@Email(message = "Put correct Email")
	private String mailid;

	@Column(unique = true)
	@NotBlank(message = "Username is required")
	private String username;

	@NotBlank(message = "Password is required")
	@Size(max = 6, message = "Password must be at least 6 characters long")
	private String password;

	@ManyToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Tutor> requestedTutors;

	@ManyToMany
	@JsonIgnore
	private List<Tutor> bookedTutors;

}
