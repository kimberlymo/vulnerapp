package ch.bbw.m183.vulnerapp.datamodel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@Email
	@NotBlank(message = "Username must be mandatory!")
	String username;

	@Column
	@NotBlank(message = "Fullname must be mandatory!")
	@Length(min = 3, max = 30, message = "Fullname must be min. 3 and max. 30 letters long")
	String fullname;

	@Column
	@NotBlank(message = "Password must be mandatory!")
	@Length(min = 8, max = 1000, message = "Password must be min. 8 and max. 30 letters long")
	String password;

	@Column
	@NotBlank(message = "Role must be mandatory!")
	String role;

	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<>();

		list.add(new SimpleGrantedAuthority(role));

		if (role.equals("ADMIN")){
			list.add(new SimpleGrantedAuthority("USER"));
		}

		return list;
	}
}
