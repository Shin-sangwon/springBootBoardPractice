package com.ll.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    private int increaseValue = -1;
    @RequestMapping("/sbb")
    // 아래 함수의 리턴값을 그대로 브라우저에 표시
    // 아래 함수의 리턴값을 문자열화 해서 브라우저 응답의 바디에 담는다.
    @ResponseBody
    public String index() {
        // 서버에서 출력
        System.out.println("Hello");
        // 먼 미래에 브라우저에서 보여짐
        return "안녕하세요.";
    }

    @GetMapping("/page1")
    @ResponseBody
    public String showPage1() {
        return """
                <form method="POST" action="/page2">
                    <input type="number" name="age" placeholder="나이" />
                    <input type="submit" value="page2로 POST 방식으로 이동" />
                </form>
                """;
    }

    @PostMapping("/page2")
    @ResponseBody
    public String showPage2Post(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요, POST 방식으로 오셨군요.</h1>
                """.formatted(age);
    }

    @GetMapping("/page2")
    @ResponseBody
    public String showPage2Get(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요, POST 방식으로 오셨군요.</h1>
                """.formatted(age);
    }

    @GetMapping("/plus")
    @ResponseBody
    public String plus(int i1, int i2){
        return """
                <h1>입력된 값 : %d, %d</h1>
                <h1>합은 : %d 입니다.</h1>
                """.formatted(i1, i2, i1+i2);
    }

    @GetMapping("/minus")
    @ResponseBody
    public String minus(int i1, int i2){
        return """
                <h1>입력된 값 : %d, %d</h1>
                <h1>차는 : %d 입니다.</h1>
                """.formatted(i1, i2, i1-i2);
    }

    @GetMapping("/increase")
    @ResponseBody
    public String increase(){
        increaseValue++;
        return """
                <h1>%d</h1>
                """.formatted(increaseValue);
    }

}
