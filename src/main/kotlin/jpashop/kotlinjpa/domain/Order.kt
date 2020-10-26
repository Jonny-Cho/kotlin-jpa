package jpashop.kotlinjpa.domain

import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.EnumType.STRING
import javax.persistence.FetchType.LAZY
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "orders")
class Order(id: Long = 0L, member: Member, orderItems: MutableList<OrderItem> = mutableListOf(), delivery: Delivery, orderDate: LocalDateTime, status: OrderStatus) {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "order_id")
	var id = id
		protected set

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	var member = member
		// 연관관계 메서드
		protected set

	@OneToMany(mappedBy = "order")
	var orderItems = orderItems
		protected set

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "delivery_id")
	var delivery = delivery
		protected set

	var orderDate = orderDate
		protected set

	@Enumerated(STRING)
	var status = status
		protected set

	fun addMember(member:Member){
		member.orders.add(this)
		this.member = member
	}

	fun addOrderItem(orderItem:OrderItem){
		orderItems.add(orderItem)
		orderItem.addOrder(this)
	}

	fun addDelivery(delivery:Delivery){
		this.delivery = delivery
		delivery.addOrder(this)
	}
}
