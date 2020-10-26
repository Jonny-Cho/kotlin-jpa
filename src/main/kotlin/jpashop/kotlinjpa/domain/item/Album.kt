package jpashop.kotlinjpa.domain.item

import javax.persistence.Entity

@Entity
class Album(id: Long = 0L, name: String, price: Int, stockQuantity: Int, artist: String, etc: String) : Item(id, name, price, stockQuantity) {
	var artist = artist
		protected set
	var etc = etc
		protected set
}
