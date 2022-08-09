package com.ll.exam.sbb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @GetMapping("/gugudan")
    @ResponseBody
    public String multiPly(int num, int limit) {

        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "%d * %d = %d".formatted(num, i, num * i))
                .collect(Collectors.joining("<br>"));
    }

    @GetMapping("/mbti/{name}")
    @ResponseBody
    public String mbti(@PathVariable String name){
        String rs = switch ( name ) {
            case "홍길동" -> "INFP";
            default -> "알수없음";
        };

        return rs;
    }

    @GetMapping("/saveSession/{name}/{value}")
    @ResponseBody
    public String saveSession(@PathVariable String name, @PathVariable String value, HttpServletRequest req){
        HttpSession session = req.getSession();

        session.setAttribute(name, value);

        return "세션변수 %s의 값이 %s로 설정되었습니다.".formatted(name, value);
    }

    @GetMapping("/getSession/{name}")
    @ResponseBody
    public String getSession(@PathVariable String name, HttpSession session){
        String value = (String)session.getAttribute(name);

        return "세션변수 %s의 값이 %s 입니다.".formatted(name, value);
    }

    private List<Article> articles = new ArrayList<>();
    @GetMapping("/addArticle")
    @ResponseBody
    public String addArticle(String title, String body) {
        Article article = new Article(title, body);
        articles.add(article);
        return "%d번 게시물이 생성되었습니다.".formatted(article.getId());
    }

    @GetMapping("/article/{id}")
    @ResponseBody
    public Article getArticle(@PathVariable int id){
        Article article = articles
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .get();

        return article;
    }

    @GetMapping("/modifyArticle/{id}")
    @ResponseBody
    public String modifyArticle(@PathVariable int id, String title, String body){
        Article article = articles
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);

        if(article == null){
            return "%d 번 게시물은 존재하지 않습니다.".formatted(id);
        }

        article.setTitle(title);
        article.setBody(body);
        return "%d번 게시물을 수정하였습니다.".formatted(id);
    }

    @GetMapping("/deleteArticle")
    @ResponseBody
    public String deleteArticle(int id) {

        for(Article article : articles) {
            if(article.getId() == id){
                articles.remove(article);
            }
        }

        return "%d번 게시물이 삭제되었습니다.".formatted(id);
    }

}

@AllArgsConstructor
@Getter
@Setter
class Article {

    private static int lastId = 0;
    private final int id;
    private  String title;
    private  String body;

    public Article(String title, String body) {
        this(++lastId, title, body);
    }
}
