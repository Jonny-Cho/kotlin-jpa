package jpashop.kotlinjpa.domain

import jpashop.kotlinjpa.domain.item.Item
import javax.persistence.*
import javax.persistence.FetchType.LAZY
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "order_item")
class OrderItem(id: Long = 0L, item: Item, orderPrice: Int, count: Int) {

	constructor(item: Item, orderPrice: Int, count: Int) : this(0L, item, orderPrice, count) {
		item.removeStock(count)
	}

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
	lateinit var order: Order
		protected set

	var orderPrice = orderPrice
		protected set

	var count = count

	fun addOrder(order: Order) {
		this.order = order
	}

	// 비즈니스 로직
	fun cancel() {
		item.addStock(count)
	}

	// 조회 로직
	fun getTotalPrice(): Int {
		return orderPrice * count
	}
}
