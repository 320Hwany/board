## board  

회원가입, 로그인 페이지를 구현하고 각 회원마다 게시글을 등록할 수 있다.

## 개발환경 

Spring Framework   

Project : Gradle project  
Language : Java 11                
Spring boot : 2.7.5     
Dependencies : Spring Web, Spring Data JPA, Lombok, Thymeleaf, H2 Database 

## trouble shooting

* 로그인을 할 때 @PostMapping에서 @ModelAttribute로 받을 때 Member가 아니라 id가 없는 MemberDto로 먼저 받아야 한다.  
* Member와 Post를 연관관계 지어줄 때 Many 쪽인 Post에 setMember로 연결했다. 그러므로 postService로 post를 저장하기 전에 먼저 setMember를 사용해야 연관관계가 완성된다. 
* Member에는 username으로 선언했는데 MemberRepository에 findByName() 메소드를 만들었다. findByUsername()으로 만들어야 JpaRepository를 상속받아 정상적으로 작동한다.
* Test코드를 작성할 때 @SpringBootTest를 해야 하는데 @SpringBootApplication이라고 해서 NPE가 계속 나왔다. 또한 이때는 DB를 연결하고 실행해야 한다.   
