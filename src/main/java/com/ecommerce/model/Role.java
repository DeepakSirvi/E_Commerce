package com.ecommerce.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {

	@Id
	private Integer id;
	@Column(unique = true, length = 30, nullable = false)
	@Enumerated(EnumType.STRING)
	private RoleName roleName;

	@Column(length = 2000)
	private String description;

	@Column(updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "role")
	private Set<UserRole> userRole = new HashSet<>();

	public Role(RoleName roleName) {
		this.roleName = roleName;
	}

	public Role(int i) {
		id = i;
	}
}
