# 🐶 MungTique - 애견샵 예약 웹사이트

> 사이드 프로젝트 (2024.04.06 ~ )  
> 반려견과 보호자를 위한 **애견샵 예약 플랫폼**  
> 마이크로서비스 아키텍처 기반의 실전형 풀스택 웹 프로젝트

---

## 🧩 프로젝트 개요

**MungTique**는 미용, 용품, 병원 등 다양한 애견샵 정보를 한 곳에서 검색하고,  
실시간으로 편하게 예약하는 **반려동물 전문 예약 플랫폼**입니다.  
Spring Boot 기반 **MSA 아키텍처**로 구성되며, **CI/CD 및 클라우드 배포**를 적용해  
실무에 가까운 개발 환경을 구현했습니다.

---

## 🚀 주요 기능

- 회원가입 및 소셜 로그인 (Kakao, Naver OAuth2)
- 애견샵 검색 및 상세 정보 조회
- 실시간 예약 및 예약 내역 확인
- 보호자 프로필 / 반려견 정보 등록 및 수정
- JWT 기반 인증 및 마이크로서비스 인가 흐름

---

## ⚙️ 기술 스택

| 영역           | 기술 스택                                   |
|----------------|----------------------------------------------|
| **Frontend**   | React, TypeScript, Tailwind CSS             |
| **Backend**    | Spring Boot, Spring Security, JWT, OAuth2, JPA |
| **Database**   | PostgreSQL, Redis                           |
| **Infra**      | AWS EC2, Docker, Jenkins               |
| **CI/CD**      | Jenkins, Docker Compose      |
| **Message Queue** | Apache Kafka                           |
| **Architecture** | MSA, Hexagonal Architecture               |

---

## 📊 ERD *(예정)*

- `user` : 회원 정보
- `dog` : 반려견 정보
- `mungshop` : 애견샵 정보
- `reservation` : 예약 내역
- `payment` : 결제 정보

---

## 📁 프로젝트 구조
<pre>
/backend    
├── service/ # 도메인별 마이크로서비스 
│ ├── user-service/ # 사용자 관련 기능 
│ ├── dog-service/ # 반려견 정보 관리 
│ ├── mungshop-service/ # 애견샵 정보 및 검색 
│ ├── reservation-service/ # 예약 처리 
│ └── payment-service/ # 결제 처리 
├── gateway/ 
│ ├── gateway/ # API Gateway 
│ └── eureka/ # 서비스 디스커버리 
├── infra/ # Kafka, Redis, DB 설정 등 
└── monitoring/ # Prometheus, Grafana 등 

/frontend 
└── mungtique/ # React 기반 프론트엔드 
</pre>



## 🖥️ 주요 화면 *(예정)*

- 로그인 / 회원가입
- 뭉샵 검색 및 상세 페이지
- 예약 등록 / 내역 조회 / 취소
- 마이페이지 (보호자 정보, 반려견 정보 등)

---

## 🙋🏻‍♀️ 개발자 한마디

> 비전공자로서 실무 중심의 구조와 기술 스택을 학습하고자  
> **MSA + CI/CD + 클라우드 인프라**까지 적용한 풀스택 프로젝트를 설계했습니다.  
> 기능뿐 아니라 아키텍처 설계와 인프라 구성까지 경험하며,  
> 대규모 시스템에서도 빠르게 적응할 수 있는 개발자가 되기 위한 발판을 마련했습니다.
