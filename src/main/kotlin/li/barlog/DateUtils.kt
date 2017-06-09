package li.barlog

import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

fun date2local(date: Date): LocalDate =
	date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
