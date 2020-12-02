package jpashop.kotlinjpa.service

import jpashop.kotlinjpa.domain.Member
import jpashop.kotlinjpa.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(val memberRepo: MemberRepository) {

	companion object {
		const val EXIST_MEMBER = "이미 존재하는 회원입니다."
		const val NOT_EXIST_MEMBER = "해당 멤버가 존재하지 않습니다."
	}

	//회원가입
	@Transactional // 변경
	fun join(member: Member): Long {
		validateDuplicatemember(member) // 중복 회원 검증
		memberRepo.save(member)
		return member.id
	}

	private fun validateDuplicatemember(member: Member) {
		val findMembers = memberRepo.findByName(member.name)
		if (findMembers.isNotEmpty()) {
			throw IllegalArgumentException(EXIST_MEMBER)
		}
	}

	// 전체 회원 조회
	fun findMembers(): List<Member> {
		return memberRepo.findAll()
			.toList()
	}

	fun findById(memberId: Long): Member {
		return memberRepo.findById(memberId)
			.orElseGet { throw IllegalArgumentException("$NOT_EXIST_MEMBER memberId = $memberId") }
	}

}
