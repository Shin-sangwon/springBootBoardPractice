package com.ll.exam.sbb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@BeforeEach
	public void posts_save(){
		Question q1 = new Question();
		q1.setSubject("Q1 Subject");
		q1.setContent(("Q1 Content"));
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("Q2 Subject");
		q2.setContent(("Q2 Content"));
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

	@AfterEach
	public void tearDown(){
		//questionRepository.truncateQuestion();
	}

	@Test
	void Question_저장된다() {
		List<Question> allPost = questionRepository.findAll();
		assertEquals(allPost.size(), 2);

		Question q = allPost.get(0);
		assertEquals("Q1 Content", q.getContent());

	}
	@Test
	void Question_삭제된다() {
		assertEquals(2, this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(2);
		Question q = oq.get();
		questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
	}

	@Test
	void Question_수정된다() {
		Optional<Question> oq = this.questionRepository.findById(1);
		Question q = oq.get();
		q.setContent("modify content");
		this.questionRepository.save(q);

	}

	@Test
	void findBySubject() {
		Question q = questionRepository.findBySubject("Q1 Subject");

		assertEquals(1, q.getId());
	}

	@Test
	void findBySubjectAndContent() {
		Question q = questionRepository.findBySubjectAndContent("Q1 Subject", "Q1 Content");

		assertEquals(1, q.getId());
	}

	@Test
	void findBySubjectLike() {
		List<Question> qList = this.questionRepository.findBySubjectLike("Q1%");

		Question q = qList.get(0);

		assertEquals("Q1 Subject", q.getSubject());

	}



}
