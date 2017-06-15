package li.barlog.service

import li.barlog.util.createPerson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.boot.ApplicationArguments

class GeneratorServiceTest {
	private val fooService = mock(PersonService::class.java)
	private val args = mock(ApplicationArguments::class.java)
	private val generatorService = GeneratorService(fooService, args)

	@Test
	fun massiveFoo() {
		generatorService.massiveFoo(3)
		verify(fooService, Mockito.times(3)).add(any())
	}

	@Test
	fun foo() {
		val foo = createPerson()
		assertNotNull(foo)
	}

	@Test
	fun duration2string() {
		val s = generatorService.duration2string(10300)
		assertEquals("0:0:10.300", s)
	}

	@Test
	fun duration2tps() {
		val s = generatorService.duration2tps(1205000L, 10)
		assertEquals("tps: 0.01", s)
	}

	@Test
	fun `duration2tps with zero duration`() {
		val s = generatorService.duration2tps(0L, 10)
		assertEquals("tps: ∞", s)
	}

	// https://medium.com/@elye.project/befriending-kotlin-and-mockito-1c2e7b0ef791
	private fun <T> any(): T {
		Mockito.any<T>()
		return uninitialized()
	}
	private fun <T> uninitialized(): T = null as T
}
