package jpashop.kotlinjpa.domain

import javax.persistence.*
import javax.persistence.FetchType.LAZY
import javax.persistence.GenerationType.IDENTITY

@Entity
class Delivery(id: Long = 0L, address: Address, status: DeliveryStatus) {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "delivery_id")
	var id = id
		protected set

	@OneToOne(fetch = LAZY)
	lateinit var order: Order
		protected set

	var address = address
		protected set

	var status = status
		protected set

	fun addOrder(order: Order) {
		order.addDelivery(this)
		this.order = order
	}
}
