package com.jphilips.twittercopy.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jphilips.twittercopy.enums.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class MyUser implements UserDetails  {

	private static final long serialVersionUID = 4939788519809163533L;

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
	

	@OneToMany(mappedBy = "myUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<MyUserRole> roles;

/* 
 ******* HELPER METHODS *******
 */

 	public void addRole(UserRole role) {
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 return roles.stream()
	                .map(role -> new SimpleGrantedAuthority(role.getRole().toString()))
	                .collect(Collectors.toList());
	}
}
