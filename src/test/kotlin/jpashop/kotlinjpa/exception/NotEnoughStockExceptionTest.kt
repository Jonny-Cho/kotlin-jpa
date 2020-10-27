package jpashop.kotlinjpa.exception

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class NotEnoughStockExceptionTest{
	@Test
	fun notEnoughStock(){
		Assertions.assertThatThrownBy{throw NotEnoughStockException("need more stock")}
			.isInstanceOf(NotEnoughStockException::class.java)
	}
}
