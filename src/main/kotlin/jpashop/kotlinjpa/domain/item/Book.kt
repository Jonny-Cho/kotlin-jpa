package jpashop.kotlinjpa.domain.item

import javax.persistence.Entity

@Entity
class Book(id: Long = 0L, name: String, price: Int, stockQuantity: Int, author: String, isbn: String) : Item(id, name, price, stockQuantity) {
	var author = author
		protected set
	var isbn = isbn
		protected set
}
