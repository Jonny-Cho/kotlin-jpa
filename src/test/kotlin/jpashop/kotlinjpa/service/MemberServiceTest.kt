package jpashop.kotlinjpa.service

import jpashop.kotlinjpa.domain.Address
import jpashop.kotlinjpa.domain.Member
import jpashop.kotlinjpa.repository.MemberRepository
import jpashop.kotlinjpa.service.MemberService.Companion.EXIST_MEMBER
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.lang.IllegalArgumentException

@SpringBootTest
internal class MemberServiceTest{

	@Autowired
	lateinit var memberService: MemberService
	@Autowired
	lateinit var memberRepo: MemberRepository

	@Test
	fun `회원가입`(){
		// given
		val member = Member(0L, "준2", Address("city", "street", "zipcode"))

		//when
		val saveId = memberService.join(member)

		//then
		val findMember = memberRepo.findById(saveId).get()
		assertThat(member).isEqualTo(findMember)
	}
	
	@Test
	fun `중복 회원 예외`(){
		//given
		val member1 = Member(
			name="조",
			address=Address("city", "street", "123")
		)
		
		val member2 = Member(
			name="조",
			address=Address("city", "street", "123")
		)
		
		//when
		memberService.join(member1)

		//예외발생 해야함
		assertThatThrownBy{memberService.join(member2)}
			.isInstanceOf(IllegalArgumentException::class.java)
			.hasMessage(EXIST_MEMBER)
	}

}
