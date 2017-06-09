package li.barlog.util

import io.codearte.jfairy.Fairy
import li.barlog.date2local
import li.barlog.model.Person

fun createPerson(): Person {
	val fairy = Fairy.create()
	val fairyPerson = fairy.person()
	val person = Person(
		fullName = fairyPerson.fullName,
		dateOfBirth = date2local(fairyPerson.dateOfBirth.toDate()),
		email = fairyPerson.email
	)
	return person
}
