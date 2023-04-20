package hello.hellospring.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import hello.hellospring.domain.Member;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemoryMemberRepositoryTest {

	MemoryMemberRepository repository = new MemoryMemberRepository();

	/*
	테스트는 순서에 구애받지 않음
	알아서 실행되기 때문에 테스트 한 동작이
	끝나면 clear 해주어 객체를 지워줘야함
	 */

	@AfterEach // 테스트 동작이 끝나면 해당 메서드 호출
	public void afterEach() {
		repository.clearStore();
	}

	@Test
	@DisplayName("member 저장이 잘 되는 지")
	public void save() {
		Member member = new Member();
		member.setName("spring");

		repository.save(member);

		Member result = repository.findById(member.getId()).get();
		assertThat(member).isEqualTo(result);
	}

	@Test
	@DisplayName("member 이름으로 잘 찾아 지는지")
	public void findByName() {
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);

		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);

		Member result = repository.findByName("spring1").get();

		assertThat(result).isEqualTo(member1);

	}

	@Test
	@DisplayName("member list가 잘 불러와지는 지")
	public void findAll() {
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);

		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);

		List<Member> members = repository.findAll();

		assertThat(members.size()).isEqualTo(2);

	}

}