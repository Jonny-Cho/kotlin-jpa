package jpashop.kotlinjpa.domain.item

import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
abstract class Item(id: Long = 0L, name: String, price: Int, stockQuantity: Int) {
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

}
