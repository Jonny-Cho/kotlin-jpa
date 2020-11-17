package jpashop.kotlinjpa.domain.dto

import jpashop.kotlinjpa.domain.OrderItem

class OrderItemDto(orderItem: OrderItem) {
	val itemName = orderItem.item.name
	val orderPrice = orderItem.orderPrice
	val count = orderItem.count
}
