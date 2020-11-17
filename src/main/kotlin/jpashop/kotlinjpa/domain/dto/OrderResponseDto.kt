package jpashop.kotlinjpa.domain.dto

import jpashop.kotlinjpa.common.dateFormat
import jpashop.kotlinjpa.domain.Order

class OrderResponseDto(order: Order) {
	val orderId = order.id
	val name = order.member.name
	val orderDate = order.orderDate.dateFormat()
	val orderStatus = order.status
	val address = order.delivery.address
	val orderItems = order.orderItems.map{ orderItem ->
		OrderItemDto(orderItem)
	}
}
