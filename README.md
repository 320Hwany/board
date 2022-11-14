## CRUD 게시판 만들기

회원가입, 로그인 페이지를 구현하고 각 회원마다 게시글을 등록할 수 있다. 각 회원은 정보를 수정하거나 탈퇴할 수 있고 동일한 아이디로는 가입할 수 없다.  
모든 게시글 보기를 하면 모든 회원이 작성한 게시글이 나오며 몇번째 글이고 작성자가 누구인지 확인할 수 있다.

## API 설계  

|기능|URL|Method|                      
|---|---|---|                                               
|회원 가입 폼| /signup| Get|            
|회원 가입 | /signup| Post| 
|로그인 폼| /login| Get|
|로그인|/login|Post|
|홈 화면|/home/{id}|Get|
|회원 삭제 폼|/deleteMember/{id}|Get|
|회원 삭제|/deleteMember/{id}|Post|
|회원 수정 폼|/updateMember/{id}|Get|
|회원 수정|/updateMember/{id}|Post|
|게시글 등록 폼|/home/{id}/registration|Get|
|게시글 등록|/home/{id}/registration|Post|
|전체 게시글 보기|/home/postList|Get|
|검색 게시글 찾기|/home/findPosts|Post|

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
* 회원 수정 기능을 만들 때 member에 @Setter없이 하기 위해서 Member 클래스 안에 updateMember를 만들어주었다. 
```
public Member updateMember(String username, String password) {
    this.username = username;
    this.password = password;
    return this;
}
```
또한 회원 수정은 JPA에서 따로 지원하는 기능은 없고 저장하고자 하는 Entity의 PK 값이 있으면 업데이트고 없으면 저장할 수 있다.
