package jpashop.kotlinjpa

import com.querydsl.jpa.impl.JPAQueryFactory
import jpashop.kotlinjpa.domain.Address
import jpashop.kotlinjpa.domain.Member
import jpashop.kotlinjpa.domain.QMember.member
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class QuerydslApplicationTests {

	@Autowired
	lateinit var em: EntityManager

	@Test
	fun contextLoads() {
		val newMember = Member("member1", Address("city", "street", "12345"))
		em.persist(newMember)

		val query = JPAQueryFactory(em)

		val result = query.selectFrom(member)
			.where(member.name.eq("member1"))
			.fetchOne()!!

		assertThat(result).isEqualTo(newMember)
		assertThat(result.name).isEqualTo(newMember.name)
	}

}
