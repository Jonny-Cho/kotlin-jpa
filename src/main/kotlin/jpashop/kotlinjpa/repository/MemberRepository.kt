package jpashop.kotlinjpa.repository

import jpashop.kotlinjpa.domain.Member
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : CrudRepository<Member, Long> {
	fun findByName(name: String): List<Member>
}
