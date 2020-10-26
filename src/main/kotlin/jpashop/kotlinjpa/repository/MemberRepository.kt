package jpashop.kotlinjpa.repository

import jpashop.kotlinjpa.domain.Member
import org.springframework.data.repository.CrudRepository

interface MemberRepository : CrudRepository<Member, Long> {
	fun findByName(name: String): List<Member>
}
