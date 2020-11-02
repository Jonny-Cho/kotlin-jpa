package jpashop.kotlinjpa.domain.item

import javax.persistence.Entity

@Entity
class Book(id: Long = 0L, name: String, price: Int, stockQuantity: Int, author: String, isbn: String) : Item(id, name, price, stockQuantity) {
	constructor(name: String, price: Int, stockQuantity: Int, author: String, isbn: String) : this(0L, name, price, stockQuantity, author, isbn)

	var author = author
		protected set
	var isbn = isbn
		protected set

}
