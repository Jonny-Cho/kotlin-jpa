package jpashop.kotlinjpa.repository

import jpashop.kotlinjpa.domain.Order
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : CrudRepository<Order, Long>
