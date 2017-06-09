package li.barlog.service

import li.barlog.model.Person
import li.barlog.repository.PersonRepository
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
open class PersonService @Autowired constructor(
	private val personRepository: PersonRepository
) {
	companion object : KLogging()

	@Transactional(readOnly = true)
	open fun get(id: Long): Person {
		logger.trace { "id: $id" }
		val result = personRepository.selectById(id)
		if (result.isPresent) {
			return result.get()
		}
		throw IllegalArgumentException("not found with id: $id")
	}

	open fun add(foo: Person): Person {
		val foo = foo.copy(id = personRepository.insert(foo))
		logger.trace {"foo: $foo"}
		return foo
	}

	@Transactional(readOnly = true)
	open fun count(): Int = personRepository.count()
}
