package com.jphilips.twittercopy.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jphilips.twittercopy.enums.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class MyUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, updatable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private boolean isActive;

	@OneToMany(mappedBy = "myUser", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MyUserRole> roles;

	// helper method

	public void addRole(UserRole role) {
		if (role == null) {
			throw new NullPointerException();
		}

		if (roles == null) {
			roles = new ArrayList<MyUserRole>();
		}

		MyUserRole newRole = new MyUserRole();
		newRole.setRole(role);

		// set back-reference
		newRole.setMyUser(this);

		roles.add(newRole);

	}

	public void removeRole(UserRole role) {
		if (role == null) {
			throw new NullPointerException();
		}

		if (roles != null) {
			// Use iterator to avoid ConcurrentModificationException
			Iterator<MyUserRole> iterator = roles.iterator();
			while (iterator.hasNext()) {
				MyUserRole userRole = iterator.next();
				if (userRole.getRole() == role) { // Compare enum directly
					iterator.remove();
					userRole.setMyUser(null); // Clear back-reference
				}
			}
		}

	}
}
