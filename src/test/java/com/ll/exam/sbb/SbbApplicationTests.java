package com.ll.exam.sbb;



import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void testJpa() {
		Question q3 = new Question();
		q3.setSubject("sbb가 무엇인가요?");
		q3.setContent("sbb에 대해서 알고 싶습니다!!");
		q3.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q3);  // 첫번째 질문 저장

	}

}
