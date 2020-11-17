package jpashop.kotlinjpa.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.dateFormat(pattern: String = "yyyy-mm-dd HH:mm"): String = this.format(DateTimeFormatter.ofPattern(pattern))
