## 10조 인앤아웃(I/O) - 링끌

<details>

<summary>System Architecture</summary>

<img width="951" alt="Linggle-architecture" src="https://user-images.githubusercontent.com/87016418/181003905-d0d09f8b-9dc0-472a-b373-30c202fbb5fa.png">


</details>

<details>
<summary>Dependency</summary>
 
* Spring Web
* Spring Security
* OAuth2 Client
* Oauth2 Resource Server
* Spring Data JPA
* Spring Data Redis
* Spring Configuration Processor
* MariaDB Driver
* Validation
 
</details>

<details>
<summary>Coding Convention</summary>
 
* 패키지 네이밍 규칙
  * lowerCamelCase
* 클래스 네이밍 규칙
  * UpperCamelCase(PascalCase)
* DB 네이밍 규칙
  * lower_snake_case
* Code Style
  * Google Java Style Guide 플러그인 적용
    * 추가 설정
    * Indent : 4
    * Tab Size : 4

</details>

<details>
<summary>Branch Strategy</summary>

* Respositories
  * upstream(Upstream Repository)
  * origin(Origin Repostiory)
  
* Branches
  * main : 제품으로 출시될 수 있는 브랜치
  * feature : 기능 단위 개발 브랜치
    * feature/feature-name
  * issue : 발생한 버그를 수정하는 브랜치
    * issue/issue-number 
 
 
</details>

<details>
 
<summary>Commit Convention</summary>

커밋 메세지는 두 단락으로 구성한다.

```
type: Subject

body
```

각 단락은 공백 한 줄로 구문한다. 

### Type: 

type은 커밋의 종류에 따라 사전에 정의한 단어를 사용한다.

사용 가능한 type은 아래와 같이 정의한다.

* `Add`: 클래스 파일 추가
* `Remove`: 코드 및 클래스 파일의 삭제
* `Feat`: 새로운 기능 추가
* `Test`: 테스트 코드 추가
* `Fix`: 버그 수정
* `Style`: 포맷팅, 오탈자 수정
* `Chore`: 라이브러리 추가 등의 설정 작업
* `Docs`: 문서 수정
* `Refactor`: 코드 리팩토링

### Subject:

Subject는 영문 대문자로 시작하며 명령문을 사용한다. 

마침표(.)를 찍지 않는다.

영문 30자를 넘지 않는다.

### Body:

body는 꼭 필요하지 않은 경우는 생략하되 적어야 할 경우 `무엇을, 왜`에 맞춰 작성한다. 

한글을 사용한다.

15개 내의 단어로 구성한다.
 
</details>
