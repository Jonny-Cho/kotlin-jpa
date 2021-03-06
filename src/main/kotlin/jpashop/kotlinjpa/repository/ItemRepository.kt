package jpashop.kotlinjpa.repository

import jpashop.kotlinjpa.domain.item.Item
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : CrudRepository<Item, Long>
