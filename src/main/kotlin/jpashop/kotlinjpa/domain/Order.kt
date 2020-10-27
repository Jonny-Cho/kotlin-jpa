package jpashop.kotlinjpa.domain

import jpashop.kotlinjpa.domain.DeliveryStatus.COMP
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


	// 연관관계 메서드
	fun addMember(member: Member) {
		member.orders.add(this)
		this.member = member
	}

	fun addOrderItem(orderItem: OrderItem) {
		orderItems.add(orderItem)
		orderItem.addOrder(this)
	}

	fun addDelivery(delivery: Delivery) {
		this.delivery = delivery
		delivery.addOrder(this)
	}

	// 생성 메서드
//	constructor(member: Member, delivery: Delivery, vararg orderItems: OrderItem) : this(id=0L, member = member, delivery = delivery, orderitems = orderItems.toMutableList(), status = OrderStatus.ORDER, orderDate = LocalDateTime.now())

	fun createOrder(member: Member, delivery: Delivery, vararg orderitems: OrderItem): Order {
		val orderItemList = orderitems.toList()
		return Order(member = member, delivery = delivery, orderItems = (orderItems + orderItemList).toMutableList(), status = OrderStatus.ORDER, orderDate = LocalDateTime.now())
	}


	// 비즈니스 로직
	// 주문 취소
	fun cancel() {
		if (delivery.status == COMP) {
			throw IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.")
		}

		status = OrderStatus.CANCEL
		orderItems.map { it.cancel() }
	}

	// 조회 로직
	fun getTotalPrice(): Int {
		return orderItems.fold(0) { total, item -> total + item.orderPrice }
	}
}
