package jpashop.kotlinjpa.service

import jpashop.kotlinjpa.domain.*
import jpashop.kotlinjpa.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
@Transactional(readOnly = true)
class OrderService(val memberService: MemberService, val orderRepo: OrderRepository, val itemService: ItemService) {
	companion object {
		const val NOT_EXIST_ORDER = "해당 주문이 존재하지 않습니다."
	}

	@PersistenceContext
	lateinit var em: EntityManager

	fun findOrders(orderSearch: OrderSearch): List<Order> {
		//language=JPAQL
		var jpql = "select o From Order o join o.member m"
		var isFirstCondition = true

		//주문 상태 검색
		orderSearch.orderStatus?.let {
			if(isFirstCondition){
				jpql += " where"
				isFirstCondition = false
			} else {
				jpql += " and"
			}
			jpql += " o.status = :status";
		}

		//회원 이름 검색
		if(orderSearch.memberName.isNotEmpty()) {
			if(isFirstCondition){
				jpql += " where"
				isFirstCondition = false
			} else {
				jpql += " and"
			}
			jpql += " m.name like :name";
		}

		var query = em.createQuery(jpql, Order::class.java).setMaxResults(1000) //최대 1000건

		if (orderSearch.orderStatus != null) {
			query = query.setParameter("status", orderSearch.orderStatus)
		}
		if (orderSearch.memberName.isNotEmpty()) {
			query = query.setParameter("name", orderSearch.memberName)
		}

		return query.getResultList().toList()
	}

	fun findById(memberId: Long) = orderRepo.findById(memberId).orElseGet { throw IllegalArgumentException(NOT_EXIST_ORDER) }

	// 주문
	@Transactional
	fun order(memberId: Long, itemId: Long, count: Int): Long {
		// 고객/상품 정보 조회
		val member = memberService.findById(memberId)
		val item = itemService.findOne(itemId)

		// 배송정보 생성
		val delivery = Delivery(Address("city", "street", "12345"))

		// 주문 상품 생성
		val orderItem = OrderItem(item, item.price, count)

		// 주문 생성
		val order = Order(member, delivery, orderItem)

		// 주문 저장
		orderRepo.save(order)

		return order.id
	}

	// api/v3/orders -> 1:n fetch 조인시 페이징 불가능
	@Transactional
	fun findAllWithItem(): List<Order> {
		return em.createQuery(
			"select distinct o from Order o" +
				" join fetch o.member m" +
				" join fetch o.delivery d" +
				" join fetch o.orderItems oi" +
				" join fetch oi.item i", Order::class.java
		).resultList
	}

	@Transactional
	fun findAllWithMemberDelivery(offset:Int, limit:Int): List<Order> {
		return em.createQuery(
			"select distinct o from Order o" +
				" join fetch o.member m" +
				" join fetch o.delivery d", Order::class.java
		)
			.setFirstResult(offset)
			.setMaxResults(limit)
			.resultList
	}

	// 주문 취소
	@Transactional
	fun cancelOrder(orderId: Long) {
		// 주문 엔티티 조회
		val order = orderRepo.findById(orderId).orElseGet { throw IllegalArgumentException("주문이 존재하지 않습니다. orderId = $orderId") }
		order.cancel()
	}

}
