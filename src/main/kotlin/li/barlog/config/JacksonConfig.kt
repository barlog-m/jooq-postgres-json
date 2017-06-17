package li.barlog.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class JacksonConfig {
	@Bean
	open fun objectMapper(): ObjectMapper = ObjectMapper()
		.registerModule(KotlinModule())
		.registerModule(JavaTimeModule())
}

