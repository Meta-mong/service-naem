# 메타넷 인턴십 3조

# 프로젝트 설명
## 주제
응모 방식의 티켓 판매 대행 플랫폼

## 기술 스택
- front-end:
- back-end: Java/spring boot
- DB
- DBMS
- infra

# 요구사항 분석
### 서비스 핵심 기능
  - 사용자는 티켓 응모 기간에 응모할 수 있다.
  - 티켓 응모 기간이 지나면 추첨을 한다.
  - 당첨된 사용자에게 이메일로 결제 페이지 링크를 제공한다.
  - 결제 페이지를 받은 사용자는 결제를 진행한다.
  - 결제가 확인되면 온라인 티켓을 발부한다.

### 사용자
  - 사용자는 회원가입시 사진, 메일 주소, 이름, 전화번호, 나이를 입력하고 메일 인증을 받는다.
  - 사용자는 마이페이지에서 최근 3개월 안에 응모한 내역을 볼 수 있다.
  - 사용자는 마이페이지에서 온라인 티켓을 확인할 수 있다.
  - 사용자는 티켓을 찜할 수 있다.
  - 사용자는 공연 티켓을 검색할 수 있다.
  - 사용자는 마이페이지에서 정보수정 및 탈퇴할 수 있다.
  - 사용자는 마이페이지에서 티켓을 취소할 수 있다.
  - 사용자는 결제 수단을 등록할 수 있다.

### 관리자
  - 관리자는 모든 회원정보를 조회할 수 있다.
  - 관리자는 티켓을 사이트에 등록할 수 있다.
  - 관리자는 티켓 정보를 수정 및 삭제할 수 있다.
  - 관리자는 Q&A 답변을 할 수 있다.

### 티켓
  - 관리자는 티켓 정보를 입력하여 티켓을 등록한다.
  - 티켓 정보에는 다음이 있다.
  - (+)티켓 고유 번호, 팜플렛, 설명, 상세정보(공연 일자, 장르, 등급, 장소, 주최측, 취소 환불), 가격, 문의, 등록일자
  - 티켓은 장르별 인기순으로 메인페이지에 노출된다.
  - 티켓은 온라인으로 발부된다.

### 응모
  - 사용자는 응모 기간에 티켓에 응모할 수 있다.
  - 사용자는 하나의 아이디로 공연당 한번의 응모만 가능하다.
  - 정해진 기간 내에 결제하지 않으면 구매 권한이 취소된다.
  - 결제 취소 발생시 다음 대기순위의 사용자에게 구매 권한이 부여된다.

### 결제
  - 당첨자에게 결제 페이지 링크를 이메일로 보낸다.
  - 결제 내역을 이메일로 전송한다.

<hr>

# Frontend 시안
https://meta-mong.github.io/Meta-Ticket-front-end/

# UX/UI 설계
https://ovenapp.io/project/9ke1DcKtgQjhFHKBUcqQ8RW39taDwhVR#1CVRg

# ERD 설계
https://www.erdcloud.com/d/TjXC5msmtJwNw5WRm
<br><br><br><br>
<hr>

## - Git Convention
### git 용어
- git branch : 현재 브랜치 목록 확인
- git branch feature/9 : "feature/9"라는 이름을 가진 브랜치 생성
- git checkout feature/9 : 현재 브랜치에서 "feature/9"브랜치로 이동
- git add -A : 현재까지 작업한 변경 사항 모두 추가(커밋 준비)
- git commit -m "메시지 내용" : 커밋
- git push : 원격 저장소로 push

### 순서
1. 오늘 할 작업 생각 후 issue 생성 -> project 현황판에 card를 in progress로(issue는 약속된 형식으로 작성)
2. "git branch" 명령어 수행(작업 하기전 터미널에서 branch 확인하기 위해)<br>
2.1 현재 branch가 "develop"이 아니면<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-> "git checkout develop" 명령어로 develop 브랜치로 이동한다. 그리고 "git pull"(원격 저장소에서 모든 것 다 내려받기)<br>
2.2 현재 branch가 "develop"이면<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-> 바로 "git pull"
4. "git branch feature/이슈번호"로 본인에 이슈번호에 맞게끔 branch 생성 
5. "git checkout feature/이슈번호"로 본인이 생성한 branch로 이동(이동한 후 git branch 명령어로 잘 이동했나 확인)
6. 오늘 할 작업 코딩
7. 모든 작업이 다 끝나면 "git add -A"로 현재 작업한 것들 모두 추가
8. git commit -m "메시지 내용" 명령어로 커밋
9. git push 명령어로 원격 저장소로 이동
10. 그러면 github 프로젝트 메인 페이지에 초록색 버튼으로 "compare & pull request" 이 생성되는데 이걸 클릭
11. 팀원들에게 카톡 등으로 "~~작업 했어요 코드 리뷰 해요" 알리기
12. 다같이 변경 사항 확인 후 오류가 없으면 merge 버튼 클릭(대장 branch인 develop에 변경사항 합치는 작업)
13. 프로젝트 현황판에서 card를 "Done"에 옮기기


1. 응모 누르기
2. 내 응모내역 확인
3. 시간지나서 당첨되는거(내 응모내역 화면에서 확인)
4. 결제 이메일 확인
5. 결제 진행~~
6. 결제 완료 후 내역 확인
