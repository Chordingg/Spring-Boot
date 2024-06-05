<div align= "center">
    <img src="https://capsule-render.vercel.app/api?type=rounded&color=0:a5bbe9,100:a4dbc6&height=180&text=Spring%20Boot%20&animation=fadeIn&fontColor=000000&fontSize=40" />
    <div align= "center"> 
     </div>
</div>

***

<h2>1. DB설정(mysql) </h2>

```db
create database member default character set utf8 collate utf8_general_ci;
```

<h2>2. 프로젝트 설정 </h2>

<h3>1</h3>

![start01](https://github.com/Chordingg/Spring-Boot/assets/157094467/eff53b49-3603-4464-ac43-3306f4e96227)

<h3>2</h3>

![start02](https://github.com/Chordingg/Spring-Boot/assets/157094467/6ed63e2c-66f3-4de0-8fa4-6f69882afbca)
<h3>3</h3>

![start03](https://github.com/Chordingg/Spring-Boot/assets/157094467/5154383a-0fe0-4e91-9b47-923ee820fcba)

<h3>application.properties 추가</h3>

```java
#MySQL 연결 설정
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/member?serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=1234

#실행되는 쿼리 콘솔 출력
spring.jpa.properties.hibernate.show_sql=true


#콘솔창에 출력되는 쿼리를 가독성이 좋게 포맷팅
spring.jpa.properties.hibernate.format_sql=true

#쿼리에 물음표로 출력되는 바인드 파라미터 출력
logging.level.org.hibernate.type.descriptor.sql=trace

spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

<h3>4</h3>

![start04](https://github.com/Chordingg/Spring-Boot/assets/157094467/da7e3168-b37e-4815-9df4-782afb7e6121)
<h3>5</h3>

![start05](https://github.com/Chordingg/Spring-Boot/assets/157094467/84032f80-7fc5-43e0-a97f-75f6f559a9b0)
<h3>6</h3>

![start06](https://github.com/Chordingg/Spring-Boot/assets/157094467/197a4711-7438-43c1-92a7-2de4516d5fc5)

<h3>7 프로젝트 폴더 및 파일 생성</h3>

![start07](https://github.com/Chordingg/Spring-Boot/assets/157094467/81df4f04-e392-4216-bce2-85c3182fccbc)

<h2>3. 코드 구현 </h2>

<h4>1. entity 폴더 Member</h4>

```java
package com.member.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private int age;

    private String phone;
    private String address;

}


```

<h4>2. 생성된 DB를 mysql 확인</h4>

![start08](https://github.com/Chordingg/Spring-Boot/assets/157094467/487713f3-6d56-47ff-983e-a16a5ee8bc36)


<h2>4. CRUD 구현 및 TEST </h2>

<h4>1. repository -> MemberRepository </h4>

```java
package com.member.repository;

import com.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}

```

<h4>2. lombok 추가</h4>

![start09](https://github.com/Chordingg/Spring-Boot/assets/157094467/1e542382-ac83-4507-8d52-612c42fd3566)

<h6>build.gradle  추가하면됨</h6>

![start10](https://github.com/Chordingg/Spring-Boot/assets/157094467/2e89129b-a301-4e62-90da-2071beb179ab)
<br>


<h4>3. service - > MemberService</h4>

```java
package com.member.service;

import com.member.entity.Member;
import com.member.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

		//CRUD => Create
    public void register(Member member){
        memberRepository.save(member);
    }
}

```

<h4>4. build.gradle -> lombok test환경 추가</h4>

```xml
// https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'

    testCompileOnly 'org.projectlombok:lombok'
    testAnnotationProcessor 'org.projectlombok:lombok'
```

<h4>5. MemberServiceTest 추가</h4>

![start11](https://github.com/Chordingg/Spring-Boot/assets/157094467/6b0fab76-9db9-4686-a8b1-88e31123d312)
![start12](https://github.com/Chordingg/Spring-Boot/assets/157094467/48a9cb7e-211f-47e4-8177-b3be8987519f)
![start13](https://github.com/Chordingg/Spring-Boot/assets/157094467/42d3453a-092b-4d66-b9af-32c8e071d57c)

```java
package com.member.service;

import com.member.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
@Log4j2
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void testInsert(){
        Member member = new Member();
        member.setAge(20);
        member.setName("까미");
        member.setAddress("수원시");

        memberService.register(member);

    }
}
```

<h4>5. 실행 및 mysql 추가 확인</h4>

![start14](https://github.com/Chordingg/Spring-Boot/assets/157094467/b24942f6-2f20-4b96-9afd-ee7b46145c4f)
![start15](https://github.com/Chordingg/Spring-Boot/assets/157094467/af52fade-9ad5-4a4f-bda6-7a9093e1a472)

<h4>5. CRUD -> update</h4>

현재 데이타 조회해서 수정이 되는지 확인

![start16](https://github.com/Chordingg/Spring-Boot/assets/157094467/2f355805-943a-403f-99f1-e29f4df93c4b)

```java
  class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    //CRUD -> update
    @Test
    public void testupdate(){
        Member member = new Member();
        member.setId(1L);
        member.setAge(28);
        member.setName("까미");
        member.setAddress("안산시");

        memberService.register(member);
    }
```
![start18](https://github.com/Chordingg/Spring-Boot/assets/157094467/63e4bd38-c9b3-4abe-8b32-457304908c78)
![start17](https://github.com/Chordingg/Spring-Boot/assets/157094467/09f1e6c1-8926-4b38-97a1-ec578b10b450)


<h4>5. CRUD -> read</h4>

```java
public class MemberService {

    private final MemberRepository memberRepository;

    //CRUD => Read
    public Member read(Long memberId){
        //memberId 값을 DB에서 조회해서  없으면 예외 발생
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found with id: " + memberId));
        return  member;
    }

```

```java
@SpringBootTest
@Log4j2
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    //CRUD -> read
    @Test
    public void testRead(){
        Long memberId = 1L;

        Member team = memberService.read(memberId);
        log.info("team : " + team);
    }
```
![start20](https://github.com/Chordingg/Spring-Boot/assets/157094467/137b2506-c739-4744-9958-501574e94461)
![start21](https://github.com/Chordingg/Spring-Boot/assets/157094467/ec8d83ff-95dd-46f4-a19e-51390078fb02)

<h4>5. CRUD -> read All(전체데이타가져오기)</h4>

```java
public class MemberService {

    private final MemberRepository memberRepository;

    //CRUD => Read All
    public List<Member> readAll(){

        List<Member> members = memberRepository.findAll();
        return members;
    }
```

```java
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    //CRUD -> read All
    @Test
    public void testReadAll(){

        List<Member> members = memberService.readAll();
        members.forEach(member -> log.info(member));
    }
```

![start22](https://github.com/Chordingg/Spring-Boot/assets/157094467/08569605-631a-40cf-a1b0-77698c068a2a)

<h4>6. CRUD -> delete</h4>

```java
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //CRUD => delete
    public void delete(Long memberId){
        memberRepository.deleteById(memberId);
    }
```

```java
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    //CRUD -> delete
    @Test
    public void testDelete(){
        Long memberId = 2L;
        memberService.delete(memberId);
    }
```
![start23](https://github.com/Chordingg/Spring-Boot/assets/157094467/f91fdd68-5a80-4438-bb5b-534d32523960)


<h2>5. Controller 구현 및 HTML 화면 구현 </h2>

<h4>1. MemberContrller -> list</h4>

```java
package com.member.controller;

import com.member.entity.Member;
import com.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Log4j2
public class MemberContoller {

    private final MemberService memberService;

    @GetMapping("/list")
    public String list(Model model){
        List<Member> members = memberService.readAll();
        model.addAttribute("members", members);
        log.info(members);
        return "member/list";

    }

}
```

<h4>2. list.html</h4>

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Member Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<div class="container">
    <h2>Member List</h2>
    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>AGE</th>
            <th>PHONE</th>
            <th>ADDRESS</th>
        </tr>
        </thead>
        <tbody>
        <tr th:each="member : ${members}">
            <td th:text="${member.id}"></td>
            <td th:text="${member.name}"></td>
            <td th:text="${member.age}"></td>
            <td th:text="${member.phone}"></td>
            <td th:text="${member.address}"></td>
        </tr>
        </tbody>
    </table>
</div>

</body>
</html>

```

<h4>1. MemberContrller -> 새글 작성</h4>

<h6>list.html 추가</h6>

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Member Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<div class="container">
    <h2>Member List</h2>
    <div>
        <a th:href="@{/member/new}" class="btn btn-outline-dark float-right ">New Member</a>
    </div>
    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>AGE</th>
            <th>PHONE</th>
            <th>ADDRESS</th>
            <th>ACTIONS</th>
        </tr>
        </thead>
        <tbody>
        <tr th:each="member : ${members}">
            <td th:text="${member.id}"></td>
            <td th:text="${member.name}"></td>
            <td th:text="${member.age}"></td>
            <td th:text="${member.phone}"></td>
            <td th:text="${member.address}"></td>3
            <td>
                <a th:href="@{/member/edit/{id}(id=${member.id})}">수정</a>&nbsp;&nbsp;
                <form th:action="@{/member/delete/{id}(id=${member.id})}" method="post" style="display:inline;">
                    <input type="hidden" name="_method" value="delete" />
                    <button type="submit">삭제</button>
                </form>
            </td>
        </tr>
        </tbody>
    </table>
</div>

</body>
</html>
```

<h6>MemberContrller -> 추가</h6>

```java
@GetMapping("/new")
    public String createForm(Model model){
        log.info("create.............");
        model.addAttribute("member", new Member());
        return "member/newForm";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("member") Member member){
        log.info("------------------------");
        memberService.register(member);
        return "redirect:list";
    }
```
<h6>newForm.html 추가</h6>

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>New Member</title>
</head>
<body>
<h1>Add New Member</h1>
<form th:action="@{/member/new}" th:object="${member}" method="post">
    <div >
        <label for="name">Name:</label>
        <input type="text" id="name" th:field="*{name}"  />
    </div>
    <div >
        <label for="age">age:</label>
        <input type="text" id="age" th:field="*{age}"  />
    </div>

    <div >
        <label for="phone">phone:</label>
        <input type="text" id="phone" th:field="*{phone}"  />
    </div>

    <div >
        <label for="address">address:</label>
        <input type="text" id="address" th:field="*{address}"  />
    </div>
    
    <div>
        <button type="submit">Save</button>
    </div>
</form>
</body>
</html>

```

<h4>2. MemberContrller -> 멤버 수정</h4>

<h6>MemberController 추가</h6>

```java
@GetMapping("/edit/{id}")
    public String updateForm(@PathVariable("id") String id,Model model){
        Long memberId = Long.parseLong(id);
        Member member = memberService.read(memberId);
        model.addAttribute("member", member);
        return "member/updateForm";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Member member){
       log.info("update..........." + member);
        memberService.register(member);
        return "redirect:list";
    }
```

<h6>updateForm 생성</h6>

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>New Member</title>
</head>
<body>
<h1>Update Member Form</h1>
<form th:action="@{/member/update}" th:object="${member}" method="post">
    <input type="hidden" th:field="*{id}">
    <div >
        <label for="name">Name:</label>
        <input type="text" id="name" th:field="*{name}"  />
    </div>
    <div >
        <label for="age">age:</label>
        <input type="text" id="age" th:field="*{age}"  />
    </div>

    <div >
        <label for="phone">phone:</label>
        <input type="text" id="phone" th:field="*{phone}"  />
    </div>

    <div >
        <label for="address">address:</label>
        <input type="text" id="address" th:field="*{address}"  />
    </div>
    <div>
        <button type="submit">Save</button>
    </div>
</form>
</body>
</html>

```

![start24](https://github.com/Chordingg/Spring-Boot/assets/157094467/c9d44717-37be-4cd8-97db-e246320e3cef)
![start25](https://github.com/Chordingg/Spring-Boot/assets/157094467/5060a909-c65d-4ea4-99af-35c51de4ff33)

<h4>3. MemberContrller -> 멤버 삭제</h4>

<h6>MemberController 추가</h6>

```java
  @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id){
        Long memberId = Long.parseLong(id);        
        memberService.delete(memberId);        
        return "redirect:/member/list";

    }
```

<h2>6. 페이징처리 </h2>

<h6>MemberService 추가</h6>

```java
 public Page<Member> readAllPage(Pageable pageable){
        Page<Member> memberPage = memberRepository.findAll(pageable);
        return memberPage;

    }
```

<h6>MemberController 추가</h6>

```java
@GetMapping("/list2")
    public String list2(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "3") int size, Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Member> memberPage = memberService.readAllPage(pageable);
        model.addAttribute("memberPage", memberPage);
        return "member/listPage";
    }
```

<h6>listPage.html 추가</h6>

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Member List</title>
</head>
<body>
<h1>Member List</h1>
<div>
    <a th:href="@{/member/new}" class="button">Insert New Member</a>
</div>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Age</th>
        <th>Phone</th>
        <th>Address</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <tr th:each="member : ${memberPage.content}">
        <td th:text="${member.id}"></td>
        <td th:text="${member.name}"></td>
        <td th:text="${member.age}"></td>
        <td th:text="${member.phone}"></td>
        <td th:text="${member.address}"></td>
        <td>
            <a th:href="@{/member/edit/{id}(id=${member.id})}">수정</a>
            <form th:action="@{/member/delete/{id}(id=${member.id})}" method="post" style="display:inline;">
                <input type="hidden" name="_method" value="delete" />
                <button type="submit">삭제</button>
            </form>
        </td>
    </tr>
    </tbody>
</table>
<div>
      <span th:each="i : ${#numbers.sequence(0, memberPage.totalPages - 1)}">
            <a th:href="@{/member/list2(page=${i})}" th:text="${i + 1}">1</a>
        </span>
</div>
</body>
</html>

```

![start26](https://github.com/Chordingg/Spring-Boot/assets/157094467/62d2a02b-0366-490c-b285-71c6058a5f0b)

