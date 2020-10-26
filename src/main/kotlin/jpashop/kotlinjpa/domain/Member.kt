package jpashop.kotlinjpa.domain

import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
class Member(id: Long = 0L, name: String, address: Address, orders: MutableList<Order> = mutableListOf()) {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "member_id")
	var id = id
		protected set

	var name = name
		protected set

	@Embedded
	var address = address
		protected set

	@OneToMany(mappedBy = "member")
	var orders = orders
		protected set

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as Member

		if (id != other.id) return false
		if (name != other.name) return false
		if (address != other.address) return false

		return true
	}

	override fun hashCode(): Int {
		var result = id.hashCode()
		result = 31 * result + name.hashCode()
		result = 31 * result + address.hashCode()
		return result
	}
}
