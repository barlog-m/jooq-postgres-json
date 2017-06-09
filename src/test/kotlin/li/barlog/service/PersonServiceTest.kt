package li.barlog.service

import li.barlog.util.createPerson
import li.barlog.repository.PersonRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.test.context.ActiveProfiles
import java.util.Optional
import org.mockito.Mockito.`when` as whn

@RunWith(MockitoJUnitRunner::class)
@ActiveProfiles("test")
class PersonServiceTest {
	@Mock
	private lateinit var repository: PersonRepository

	@Test
	fun get() {
		val service = PersonService(repository)

		val id = 1L
		whn(repository.selectById(id)).thenReturn(Optional.of(createPerson()))
		service.get(id)
		Mockito.verify(repository, Mockito.times(1)).selectById(id)
	}

	@Test
	fun getAll() {
		val service = PersonService(repository)

		whn(repository.count()).thenReturn(1)
		service.count()
		Mockito.verify(repository, Mockito.times(1)).count()
	}

	@Test
	fun add() {
		val service = PersonService(repository)

		val person = createPerson()
		whn(repository.insert(person)).thenReturn(1L)
		service.add(person)
		Mockito.verify(repository, Mockito.times(1)).insert(person)
	}
}
