package jpashop.kotlinjpa.service

import jpashop.kotlinjpa.domain.Address
import jpashop.kotlinjpa.domain.Member
import jpashop.kotlinjpa.domain.OrderStatus
import jpashop.kotlinjpa.domain.item.Book
import jpashop.kotlinjpa.domain.item.Item
import jpashop.kotlinjpa.domain.item.Item.Companion.NOT_ENOUGH_STOCK
import jpashop.kotlinjpa.exception.NotEnoughStockException
import jpashop.kotlinjpa.repository.OrderRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@SpringBootTest
@Transactional
internal class OrderServiceTest {

	@PersistenceContext
	lateinit var em: EntityManager

	@Autowired
	lateinit var orderService: OrderService

	@Autowired
	lateinit var orderRepo: OrderRepository

	// 상품 주문이 성공해야 한다
	// 상품을 주문할 때 재고 수량을 초과하면 안된다.
	// 주문 취소가 성공해야 한다.
	@Test
	fun `상품 주문이 성공해야 한다`() {
		//given
		val member = createMember()
		val book = createBook()
		val orderCount = 2

		//when
		val orderId = orderService.order(member.id, book.id, orderCount)

		//then
		val order = orderRepo.findById(orderId).get()

		// 주문 상태는 ORDER
		assertThat(order.status).isEqualTo(OrderStatus.ORDER)
		// 주문 상품 수 정확
		assertThat(order.orderItems.size).isEqualTo(1)
		// 주문 가격은 가격 * 수량이다
		assertThat(order.getTotalPrice()).isEqualTo(book.price * orderCount)
		// 주문 수량만큼 재고가 줄어야 한다.
		assertThat(book.stockQuantity).isEqualTo(10 - 2)
	}

	@Test
	fun `상품을 주문할 때 재고 수량을 초과하면 안된다`(){
		//given
		val member = createMember()
		val book = createBook()
		val orderCount = 10000

		//then
		assertThatThrownBy{orderService.order(member.id, book.id, orderCount)}
			.isInstanceOf(NotEnoughStockException::class.java)
			.hasMessage(NOT_ENOUGH_STOCK)
	}

	@Test
	fun `주문 취소가 성공해야 한다`(){
		//given
		val member = createMember()
		val book = createBook()
		val orderCount = 2

		//when
		val orderId = orderService.order(member.id, book.id, orderCount)
		val order = orderService.findById(orderId)

		//then
		assertThat(order.status).isEqualTo(OrderStatus.ORDER)
		assertThat(book.stockQuantity).isEqualTo(10 - 2)

		//when
		orderService.cancelOrder(orderId)

		//then
		assertThat(order.status).isEqualTo(OrderStatus.CANCEL)
		assertThat(book.stockQuantity).isEqualTo(10 - 2 + 2)
	}

	private fun createBook(): Book {
		val book = Book(0L, "어린왕자", 10000, 10, "쌩떽쥐베리", "isbn1")
		em.persist(book)
		return book
	}

	private fun createMember(): Member {
		val member = Member(0L, "member1", Address("city1", "street1", "123"))
		em.persist(member)
		return member
	}
}
