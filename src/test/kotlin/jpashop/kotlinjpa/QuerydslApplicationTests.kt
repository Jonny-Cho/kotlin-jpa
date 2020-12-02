package jpashop.kotlinjpa

import com.querydsl.jpa.impl.JPAQueryFactory
import jpashop.kotlinjpa.domain.Address
import jpashop.kotlinjpa.domain.Member
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jpa.repository.query.JpaQueryCreator
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
    }

}
