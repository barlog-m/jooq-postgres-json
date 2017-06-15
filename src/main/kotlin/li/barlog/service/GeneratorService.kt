package li.barlog.service

import li.barlog.util.createPerson
import mu.KLogging
import org.springframework.boot.ApplicationArguments
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class GeneratorService(
	private val personService: PersonService,
	private val args: ApplicationArguments
) {
	companion object : KLogging()

	private val generate by lazy {
		args.containsOption("generate")
	}

	@EventListener(ContextRefreshedEvent::class)
	fun generate() {
		if (!generate) return
		logger.info { "generate: $generate" }

		logger.info { "start" }
		val start = System.currentTimeMillis()

		val count = 3000
		massiveFoo(count)

		val duration = System.currentTimeMillis() - start

		logger.info { "finish in ${duration2string(duration)} with ${duration2tps(duration, count)}" }
	}

	fun massiveFoo(size: Int) {
		for (i in 1..size) {
			personService.add(createPerson())
		}
	}

	fun duration2string(duration: Long): String {
		var remain = duration

		val hours = TimeUnit.MILLISECONDS.toHours(remain)
		remain -= TimeUnit.HOURS.toMillis(hours)
		val minutes = TimeUnit.MILLISECONDS.toMinutes(remain)
		remain -= TimeUnit.MINUTES.toMillis(minutes)
		val seconds = TimeUnit.MILLISECONDS.toSeconds(remain)
		remain -= TimeUnit.SECONDS.toMillis(seconds)

		return "$hours:$minutes:$seconds.$remain"
	}

	fun duration2tps(duration: Long, count: Int): String {
		if (duration > 0) {
			val r = count / (duration / 1000.0)
			return "tps: ${"%.2f".format(r)}"
		} else
			return "tps: ∞"
	}
}
