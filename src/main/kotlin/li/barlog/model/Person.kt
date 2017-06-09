package li.barlog.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate

data class Person(
	@field:JsonIgnore
	val id: Long = -1,
	val fullName: String,
	val dateOfBirth: LocalDate,
	val email: String
)
