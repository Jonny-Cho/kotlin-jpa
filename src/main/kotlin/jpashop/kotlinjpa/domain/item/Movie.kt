package jpashop.kotlinjpa.domain.item

import javax.persistence.Entity

@Entity
class Movie(id: Long = 0L, name: String, price: Int, stockQuantity: Int, director: String, actor: String) : Item(id, name, price, stockQuantity) {
	var director = director
		protected set
	var actor = actor
		protected set
}
