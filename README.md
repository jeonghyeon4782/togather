# 📜일정관리부터 정산까지 TOGATHER!
<img src="https://github.com/user-attachments/assets/a0698fe7-3549-4231-9de2-5ea21cef329f" width="300"/>

<br/>
<br/>

## 📆 프로젝트 진행기간
- 2024.07.02 ~ 2024.08.16 (6W)
- SSAFY 11기 2학기 공통프로젝트 - Togather
- 🏆 공통 프로젝트 우수상

<br/>
<br/>

## 🙍 팀원 소개
![image](https://github.com/user-attachments/assets/892e0011-862d-4722-b354-70b5a503dc1d)

<br/>
<br/>

## 💬 서비스 소개
###  😮 어떤 사람들을 위한 서비스일까요?
- 각자 가고 싶은 장소를 한 번에 볼 수 없어서 불편한 사람! 
- 일일이 계산하지 않고 한 번에 정산하고 싶은 사람!
- 개인별 정산을 원하는 사람!
### 🙆‍♂ 무슨 서비스일까요?
- 친구들과 여행을 갈 때, 일정부터 정산까지 한번에 수행할 수 있어요!
- 나만의 페이머니를 통해, 별명을 설정하고, 친구들과 송금할 수 있어요!

<br/>
<br/>

## ✨ 주요 기능
### 📅 일정 관리
- 원하는 모임과 일정을 만들어서, 초대코드를 통해 사용자를 추가할 수 있어요!
- 원하는 일정 내에서, 드래그 앤 드랍을 통해 원하는대로 일정을 조율해요!
### 📃 영수증 관리
- 일정에서 정산할 내용이 발생했다면, OCR을 통해 영수증 등록을 손 쉽게 할 수 있어요!
- 기록된 영수증은 Togather 알아서 계산하니, 등록만 하면 정산 기능을 이용할 수 있어요!
- 영수증 등록에는 더치페이, 개별 정산, 몰아주기 방식이 존재해요!
### 💰 정산 기능
- 일정이 모두 종료됐다면, 정산 기능을 통해 사용자들에게 정산 알림을 보낼 수 있어요!
- 혹시나 잘못된 계산이 생겼다면 언제든 이의신청을 통해, 조정할 수 있어요!
### 💳 나만의 지갑
- 나만의 페이머니를 만들고, 연동된 계좌에서 필요한 만큼 출금하고, 다시 계좌로 넣을 수도 있어요!
- 친구들과의 송금은 페이머니를 통해 정산이 아니더라도 언제든 송금이 가능해요!
  
<br/>
<br/>

## 🔧 시스템 아키텍쳐
![아키텍처](https://github.com/user-attachments/assets/9033efe3-5dde-448e-b9e1-e378de75046c)

<br/>
<br/>

## 📝 ERD
![ERD](https://github.com/user-attachments/assets/a5ed9dc3-2599-4e7b-861f-cef10650afa3)

<br/>
<br/>

## ✨ 서비스 구현 화면
### 1. 계정 관련 기능 및 알림 기능 및 모임 관리

| <div align="center">**로그인**</div> | <div align="center">**알림 페이지**</div> | <div align="center">**모임 생성**</div> | <div align="center">**모임 참여**</div> | <div align="center">**모임 참여 요청 수락**</div> |
| --- | --- | --- | --- | --- |
| <div align="center"><img src="https://github.com/user-attachments/assets/56e1b19c-6eab-4635-9588-48796c88c3da" width="200" height="400"/></div> | <div align="center"><img src="https://github.com/user-attachments/assets/8de208b0-295f-4e6e-8f71-30b2cbcd25ac" width="200" height="400"/></div> | <div align="center"><img src="https://github.com/user-attachments/assets/0f7e0783-ed50-48a9-a182-577ddf771e2c" width="200" height="400"/></div> | <div align="center"><img src="https://github.com/user-attachments/assets/8c4e82d4-9577-4e98-9b60-8867f7bc19f2" width="200" height="400"/></div> | <div align="center"><img src="https://github.com/user-attachments/assets/22660a46-fcc2-47f8-acb0-f214f948692c" width="200" height="400"/></div> |

### 2. 일정 관리

| <div align="center">**일정 생성**</div> | <div align="center">**장소 추가**</div> | <div align="center">**장소 날짜 변경**</div> | <div align="center">**장소 순서 변경**</div> | <div align="center">**공동 작업**</div> |
| --- | --- | --- | --- | --- |
| <div align="center"><img src="https://github.com/user-attachments/assets/fe48a175-0619-41bd-b3e2-fd6909937753" width="200" height="400"/></div> | <div align="center"><img src="https://github.com/user-attachments/assets/25606f97-38b2-4891-8fb6-6c2545bdc76b" width="200" height="400"/></div> | <div align="center"><img src="https://github.com/user-attachments/assets/2cf49088-bde0-4a98-931d-df691a8c4a47" width="200" height="400"/></div> | <div align="center"><img src="https://github.com/user-attachments/assets/06f6080c-a7c2-4567-b73d-2123137acb8a" width="200" height="400"/></div> | <div align="center"><img src="https://github.com/user-attachments/assets/20c23a8f-9f29-402e-8f5f-7293e6dcfafb" width="200" height="400"/></div> |

### 3. 정산 및 송금 관리

| <div align="center">**영수증 등록**</div> | <div align="center">**영수증 동의 송금**</div> | <div align="center">**충전하기**</div> | <div align="center">**송금하기**</div> | <div align="center">**몰아주기게임**</div> |
| --- | --- | --- | --- | --- |
| <div align="center"><img src="https://github.com/user-attachments/assets/8e6021a5-65e5-408b-b820-8bd04afeb1aa" width="200" height="400"/></div> | <div align="center"><img src="https://github.com/user-attachments/assets/d9870126-ae8b-4543-ad6e-c0c38061b80d" width="200" height="400"/></div> | <div align="center"><img src="https://github.com/user-attachments/assets/d74fd0b0-44f5-4c56-b712-b53357548de3" width="200" height="400"/></div> | <div align="center"><img src="https://github.com/user-attachments/assets/63bd0fa8-f36f-42c3-beeb-42d2283d270b" width="200" height="400"/></div> | <div align="center"><img src="https://github.com/user-attachments/assets/bb0ecdaf-72e4-4e1b-8714-67a1bfba495a" width="200" height="400"/></div> |
