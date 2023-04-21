package hello.hellospring.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;

	@Test
	@DisplayName("회원가입이 잘되는 지")
	void join() {
		// given
		Member member = new Member();
		member.setName("hello1");

		// when
		Long saveId = memberService.join(member);

		// then
		Member findMember = memberService.findOne(saveId).get();
		Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
	}

	@Test
	@DisplayName("중복 회원 예외처리가 잘 되는 지")
	public void duplicateMember() {
		// given
		Member member1 = new Member();
		member1.setName("spring");

		Member member2 = new Member();
		member2.setName("spring");

		// when
		memberService.join(member1);
//		try {
//			memberService.join(member2);
//			fail();
//		} catch (IllegalStateException e) {
//			Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//		}
		// try catch가 아니라 assertThrows를 사용하여 예외 처리를 확인할 수 있음
		IllegalStateException e = assertThrows(IllegalStateException.class,
				() -> memberService.join(member2));

		Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

		// then
	}

}