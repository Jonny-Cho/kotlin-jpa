package jpashop.kotlinjpa.domain.item

import jpashop.kotlinjpa.exception.NotEnoughStockException
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
abstract class Item(id: Long = 0L, name: String, price: Int, stockQuantity: Int) {
	companion object {
		const val NOT_ENOUGH_STOCK = "need more stock"
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "item_id")
	var id = id
		protected set

	var name = name
		protected set

	var price = price
		protected set

	var stockQuantity = stockQuantity
		protected set

	// 비즈니스 로직
	fun addStock(quentity: Int) {
		this.stockQuantity += quentity
	}

	fun removeStock(quentity: Int) {
		val restStock = this.stockQuantity - quentity
		if (restStock < 0) {
			throw NotEnoughStockException(NOT_ENOUGH_STOCK)
		}
		this.stockQuantity = restStock
	}

	fun update(name: String? = null, price: Int? = null, stockQuantity: Int? = null) {
		name?.also { this.name = it }
		price?.also { this.price = it }
		stockQuantity?.also { this.stockQuantity = it }
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as Item

		if (id != other.id) return false
		if (name != other.name) return false

		return true
	}

	override fun hashCode(): Int {
		var result = id.hashCode()
		result = 31 * result + name.hashCode()
		return result
	}

}
