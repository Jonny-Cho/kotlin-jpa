package jpashop.kotlinjpa.domain

import jpashop.kotlinjpa.domain.item.Item
import javax.persistence.*
import javax.persistence.FetchType.LAZY
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "order_item")
class OrderItem(id: Long = 0L, item: Item, order: Order, orderPrice: Int, count: Int) {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "order_item_id")
	var id = id
		protected set

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "item_id")
	var item = item
		protected set

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "order_id")
	var order = order
		protected set

	var orderPrice = orderPrice
		protected set

	var count = count

	fun addOrder(order: Order) {
		this.order = order
		order.addOrderItem(this)
	}
}
