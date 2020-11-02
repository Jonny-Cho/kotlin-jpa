package jpashop.kotlinjpa.service

import jpashop.kotlinjpa.domain.item.Item
import jpashop.kotlinjpa.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ItemService(val itemRepo: ItemRepository) {

	@Transactional
	fun saveItem(item: Item) {
		itemRepo.save(item)
	}

	fun findItems() = itemRepo.findAll().toList()

	fun findOne(itemId: Long) = itemRepo.findById(itemId).get()

	fun updateItem(id: Long, name: String, price: Int) {
		val item = itemRepo.findById(id).get()
		item.update(name = name, price=price)
	}

}
