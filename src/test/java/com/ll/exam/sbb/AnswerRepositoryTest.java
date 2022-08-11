package com.ll.exam.sbb;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AnswerRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

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
        questionRepository.truncateQuestion();
        answerRepository.truncateAnswer();
    }

    @Test
    void Answer_저장된다() {
        Question q = questionRepository.findById(2).get();

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q);
        a.setCreateDate(LocalDateTime.now());
        answerRepository.save(a);

    }

}