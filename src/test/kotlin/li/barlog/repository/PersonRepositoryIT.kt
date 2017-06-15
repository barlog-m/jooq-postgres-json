package li.barlog.repository

import li.barlog.App
import li.barlog.model.jooq.tables.Person.PERSON
import li.barlog.util.createPerson
import org.jooq.DSLContext
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.DefaultTransactionDefinition

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(App::class))
@Transactional
open class PersonRepositoryIT {
	@Autowired
	private lateinit var context: DSLContext

	@Autowired
	private lateinit var fooRepository: PersonRepository

	@Autowired
	private lateinit var transactionManager: PlatformTransactionManager

	@Test
	open fun select() {
		val personOneRaw = createPerson()
		val personOneId = fooRepository.insert(personOneRaw)
		val personOne = personOneRaw.copy(id = personOneId)

		val personTwoRaw = createPerson()
		val personTwoId = fooRepository.insert(personTwoRaw)
		val personTwo = personTwoRaw.copy(id = personTwoId)

		val result = fooRepository.select()
		assertTrue(result.contains(personOne))
		assertTrue(result.contains(personTwo))
	}

	@Test
	open fun insertAndSelectByIdTest() {
		val personRaw = createPerson()
		val id = fooRepository.insert(personRaw)
		val person = personRaw.copy(id = id)
		val personOptional = fooRepository.selectById(id)
		assertTrue(personOptional.isPresent)
		assertEquals(person, personOptional.get())
	}

	@Test
	open fun count() {
		context.truncate(PERSON).execute()

		fooRepository.insert(createPerson())
		fooRepository.insert(createPerson())
		fooRepository.insert(createPerson())

		assertEquals(3, fooRepository.count())
	}

	@Test
	@Transactional(propagation = Propagation.NEVER)
	open fun transaction() {
		val def = DefaultTransactionDefinition()
		val status = transactionManager.getTransaction(def)
		val id = fooRepository.insert(createPerson())
		transactionManager.rollback(status)

		val fileOpt = fooRepository.selectById(id)
		assertFalse(fileOpt.isPresent)
	}

	@Test
	open fun selectByEMail() {
		val email = "foo@bar.baz"
		val personRaw = createPerson().copy(email = email)
		val id = fooRepository.insert(personRaw)
		val person = personRaw.copy(id = id)
		val personOptional = fooRepository.selectByEMail(email)
		assertTrue(personOptional.isPresent)
		assertEquals(person, personOptional.get())
	}
}
