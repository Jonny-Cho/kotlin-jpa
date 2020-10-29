package jpashop.kotlinjpa.service

import jpashop.kotlinjpa.domain.Address
import jpashop.kotlinjpa.domain.Delivery
import jpashop.kotlinjpa.domain.DeliveryStatus.READY
import jpashop.kotlinjpa.domain.Order
import jpashop.kotlinjpa.domain.OrderItem
import jpashop.kotlinjpa.repository.MemberRepository
import jpashop.kotlinjpa.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrderService(val memberRepo: MemberRepository, val orderRepo: OrderRepository, val itemService: ItemService) {
	companion object {
		const val NOT_EXIST_MEMBER = "해당 멤버가 존재하지 않습니다."
		const val NOT_EXIST_ORDER = "해당 주문이 존재하지 않습니다."
	}

	fun findById(memberId: Long) = orderRepo.findById(memberId).orElseGet { throw IllegalArgumentException(NOT_EXIST_ORDER) }

	// 주문
	@Transactional
	fun order(memberId: Long, itemId: Long, count: Int): Long {
		// 고객/상품 정보 조회
		val member = memberRepo.findById(memberId).orElseGet { throw IllegalArgumentException("$NOT_EXIST_MEMBER memberId = $memberId") }
		val item = itemService.findOne(itemId)

		// 배송정보 생성
		val delivery = Delivery(0L, Address("city", "street", "12345"), READY)

		// 주문 상품 생성
		val orderItem = OrderItem(item, item.price, count)

		// 주문 생성
		val order = Order(member, delivery, orderItem)

		// 주문 저장
		orderRepo.save(order)

		// 상품 재고 감소
		item.removeStock(count)

		return order.id
	}

	// 주문 취소
	@Transactional
	fun cancelOrder(orderId: Long) {
		// 주문 엔티티 조회
		val order = orderRepo.findById(orderId).orElseGet { throw IllegalArgumentException("주문이 존재하지 않습니다. orderId = $orderId") }
		order.cancel()
	}
}
