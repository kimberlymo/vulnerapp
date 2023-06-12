package ch.bbw.m183.vulnerapp.datamodel;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "blogs")
public class BlogEntity {

	@Id
	UUID id;

	@Column
	@CreationTimestamp
	@PastOrPresent
	LocalDateTime createdAt;

	@Column(columnDefinition = "text")
	@NotBlank(message = "Title must be mandatory!")
	@Length(min = 3, max = 60, message = "Title must be min. 3 and max. 60 letters long")
	String title;

	@Column(columnDefinition = "text")
	@NotBlank(message = "Body must be mandatory!")
	@Length(min = 3, max = 1000, message = "Body must be min. 3 and max. 1000 letters long")
	String body;
}
