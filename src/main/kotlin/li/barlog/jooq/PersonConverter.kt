package li.barlog.jooq

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import li.barlog.model.Person
import org.jooq.Converter

class PersonConverter : Converter<Any, Person> {
	companion object {
		private val mapper = ObjectMapper()
			.registerModule(KotlinModule())
			.registerModule(JavaTimeModule())
	}

	override fun to(person: Person): Any =
		mapper.writeValueAsString(person)

	override fun from(databaseAny: Any): Person =
		mapper.readValue<Person>(databaseAny.toString(), Person::class.java)

	override fun fromType(): Class<Any> = Any::class.java

	override fun toType(): Class<Person> = Person::class.java
}
