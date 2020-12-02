//package jpashop.kotlinjpa
//
//import jpashop.kotlinjpa.domain.*
//import jpashop.kotlinjpa.domain.item.Book
//import org.springframework.stereotype.Component
//import org.springframework.transaction.annotation.Transactional
//import javax.annotation.PostConstruct
//import javax.persistence.EntityManager
//
//@Component
//class InitDb(val initService: InitService) {
//
//	@PostConstruct
//	fun init() {
//		initService.dbInit1()
//		initService.dbInit2()
//	}
//
//	@Component
//	@Transactional
//	class InitService(val em: EntityManager){
//
//		fun dbInit1(){
//			val member = createMember("userA", "서울", "1", "1111")
//			em.persist(member)
//
//			val book1 = createBook("JPA1 BOOK", 10000, 100)
//			em.persist(book1)
//
//			val book2 = createBook("JPA2 BOOK", 20000, 200)
//			em.persist(book2)
//
//			val orderItem1 = OrderItem(book1, 10000, 1)
//			val orderItem2 = OrderItem(book2, 10000, 2)
//
//			val order = Order(member, createDelivery(member), orderItem1, orderItem2)
//			em.persist(order)
//		}
//
//		fun dbInit2(){
//			val member = createMember("userB", "진주", "2", "2222")
//			em.persist(member)
//
//			val book1 = createBook("SPRING1 BOOK", 20000, 200)
//			em.persist(book1)
//
//			val book2 = createBook("SPRING2 BOOK", 40000, 300)
//			em.persist(book2)
//
//			val orderItem1 = OrderItem(book1, 20000, 3)
//			val orderItem2 = OrderItem(book2, 40000, 4)
//
//			val order = Order(member, createDelivery(member), orderItem1, orderItem2)
//			em.persist(order)
//		}
//
//		private fun createDelivery(member: Member): Delivery {
//			return Delivery(member.address)
//		}
//
//		private fun createBook(name: String, price: Int, stockQuantity: Int): Book {
//			return Book(name, price, stockQuantity, "author", "isbn")
//		}
//
//		private fun createMember(name: String, city: String, street: String, zipcode: String): Member {
//			return Member(name, Address(city, street, zipcode))
//		}
//	}
//
//}
