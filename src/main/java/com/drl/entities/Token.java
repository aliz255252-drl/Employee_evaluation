package com.drl.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "token")
public class Token {
	@Id
	@GeneratedValue
	public Long id;
	@Column(columnDefinition="TEXT")
	public String token;
	public boolean revoked;
	public boolean expired;

	public Integer userId;

	

}
