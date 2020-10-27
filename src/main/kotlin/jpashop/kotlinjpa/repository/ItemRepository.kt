package jpashop.kotlinjpa.repository

import jpashop.kotlinjpa.domain.item.Item
import org.springframework.data.repository.CrudRepository

interface ItemRepository : CrudRepository<Item, Long>
