package li.barlog.repository

import li.barlog.model.Person
import li.barlog.model.jooq.tables.Person.PERSON
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
open class PersonRepository @Autowired constructor(
	private val context: DSLContext
) {
	open fun select(): List<Person> = context
		.selectFrom(PERSON)
		.fetch { it.data.copy(id = it.id) }

	open fun selectById(id: Long): Optional<Person> = context
		.selectFrom(PERSON)
		.where(PERSON.ID.eq(id))
		.fetchOptional { it.data.copy(id = it.id) }

	open fun insert(person: Person): Long = context
		.insertInto(PERSON)
		.set(PERSON.DATA, person)
		.returning(PERSON.ID)
		.fetchOne()
		.getValue(PERSON.ID)

	open fun count(): Int = context
		.selectCount()
		.from(PERSON)
		.fetchOne(0, Int::class.java)

	open fun selectByEMail(email: String): Optional<Person> = context
		.selectFrom(PERSON)
		.where("data->>'email' = ?", email)
		.fetchOptional { it.data.copy(id = it.id) }
}
