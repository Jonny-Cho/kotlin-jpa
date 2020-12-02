package jpashop.kotlinjpa.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MemberTest {

	@Test
	fun memberTest() {
		val member1 = Member(1L, "준희", Address("city", "street", "12345"))
		val member2 = Member(1L, "준희", Address("city", "street", "12345"))

		assertThat(member1).isEqualTo(member2)
	}

}
