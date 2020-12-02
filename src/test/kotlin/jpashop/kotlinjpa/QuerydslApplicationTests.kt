package jpashop.kotlinjpa

import com.querydsl.jpa.impl.JPAQueryFactory
import jpashop.kotlinjpa.domain.Address
import jpashop.kotlinjpa.domain.Member
import jpashop.kotlinjpa.domain.QMember
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
		val member = Member("member1", Address("city", "street", "12345"))
		em.persist(member)

		val query = JPAQueryFactory(em)
		val qMember = QMember("m")

		val result = query.selectFrom(qMember).fetchOne()!!

		assertThat(result).isEqualTo(member)
		assertThat(result.name).isEqualTo(member.name)
	}

}
