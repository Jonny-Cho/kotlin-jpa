package jpashop.kotlinjpa.domain

import jpashop.kotlinjpa.domain.DeliveryStatus.COMP
import jpashop.kotlinjpa.domain.OrderStatus.CANCEL
import jpashop.kotlinjpa.domain.OrderStatus.ORDER
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.CascadeType.PERSIST
import javax.persistence.EnumType.STRING
import javax.persistence.FetchType.LAZY
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "orders")
class Order(id: Long = 0L, member: Member, delivery: Delivery, orderItems: MutableList<OrderItem> = mutableListOf(), orderDate: LocalDateTime = LocalDateTime.now(), status: OrderStatus = ORDER) {

	constructor(member: Member, delivery: Delivery, vararg orderItems: OrderItem) : this(0L, member, delivery) {
		orderItems.forEach {
			addOrderItem(it)
		}
	}

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

	@OneToMany(mappedBy = "order", cascade = [PERSIST])
	var orderItems = orderItems
		protected set

	@OneToOne(fetch = LAZY, cascade = [PERSIST])
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

	// 비즈니스 로직
	// 주문 취소
	fun cancel() {
		if (delivery.status == COMP) {
			throw IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.")
		}

		status = CANCEL
		orderItems.map { it.cancel() }
	}

	// 조회 로직
	fun getTotalPrice(): Int {
		return orderItems.fold(0) { total, item -> total + item.getTotalPrice() }
	}
}
