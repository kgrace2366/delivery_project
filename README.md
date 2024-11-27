# Delivery Project

## 📌 프로젝트 목적 및 상세

**음식 배달 서비스 관리 시스템**을 구현하였습니다.

### 주요 기능

- 사용자: 주문 요청 생성 및 상태 확인, 결제
- 관리자: 배달 상태 조회 및 수정
- 가게 주인 : 배달 상태 변경 및 주문 승인,거절

---

## 🛠️ 기술 스택

- **Backend**: Java Spring Boot, JPA, MariaDB
- **Deployment**: AWS EC2, Docker
- **Testing**: JUnit5, Mockito
- **Documentation**: Spring REST Docs

---

## 👥 팀원 역할분담

| 이름  | 역할     | 주요 담당 업무                |
|-----|--------|-------------------------|
| 김성호 | LEADER | 주문 관리, 리뷰 관리, 결제 서비스 관리 |
| 김예원 | MEMBER | 식당 서비스 관리, 카테고리 및 메뉴 관리 |
| 김민주 | MEMBER | 회원 관리, Security 관리, AI 연동 |

<details>
  <details>
      <summary> 김민주 담당 기능 </summary>
      1. 회원 관리 기능

      2. AI API 연동
      3. WebSecurityConfig 보안 설정
    ### 1. User Management

- **회원가입**: 사용자 계정 생성 기능. 사용자명, 비밀번호, 주소, 역할 등을 입력받아 저장합니다.
- **로그인 및 인증**: JWT 토큰 기반 인증을 통해 사용자 로그인 기능을 구현했습니다.
- **회원 정보 조회**: 사용자 개인 정보를 조회할 수 있습니다.
- **회원 정보 수정**: 사용자명, 주소, 역할 등을 업데이트할 수 있습니다.
- **회원 탈퇴**: 사용자를 삭제 상태로 변경(soft delete)하여 탈퇴 처리합니다.

### 2. AI 연동

- **AI API 연동**: Google AI API를 활용해 질문에 간결한 응답을 제공하며, 응답 데이터를 DB에 저장하는 기능을 구현했습니다.
- **비용 절감 처리**: 요청 시 글자 수 제한 및 간결한 답변 요청을 통해 API 호출 비용을 최적화했습니다.

### 3. WebSecurityConfig 보안 설정

- **Spring Security 설정**
    - CSRF 비활성화: REST API의 특성을 고려해 CSRF 보호를 비활성화했습니다.
    - 세션 사용 안 함: JWT 기반 인증 방식을 사용하여 세션을 생성하지 않고, `STATELESS`로 설정했습니다.

- **JWT 인증 및 권한 관리**
    - `JwtAuthenticationFilter`와 `JwtAuthorizationFilter`를 추가하여 로그인 시 JWT 토큰 생성 및 요청 인증을 처리합니다.
    - 사용자 권한(Role)에 따라 API 접근 권한을 분리했습니다.

- **API별 접근 권한 설정**
    - `requestMatchers`를 통해 API별로 필요한 권한을 정의했습니다.
    - Ex:
        - `/api/user/signup`, `/api/user/{username}`: 모든 사용자 접근 가능
        - `/api/order/`: `CUSTOMER` 권한 필요
        - `/api/review`: `CUSTOMER`, `OWNER` 등 다양한 권한 필요

- **필터 체인 관리**
    - `JwtAuthenticationFilter`는 로그인 요청 시 토큰을 발급하고,
      `JwtAuthorizationFilter`는 각 요청의 JWT 유효성을 검사합니다.

## 특이사항

- **권한 관리**: MANAGER, MASTER와 같은 특정 권한은 고유 토큰을 통해 부여받도록 구현했습니다.
- **보안 강화**: 비밀번호 암호화 및 JWT 인증 방식을 활용하여 사용자 데이터를 보호합니다.
  </details>
  <details>
    <summary> 김성호 담당 기능</summary>

  </details>
  <details>
    <summary> 김예원 담당 기능</summary>
  1. 카테고리 관리 기능
  2. 가게 관리 기능
  3. 메뉴 관리 기능

### 1. 카테고리 관리 기능

- **카테고리 생성** :
    - 관리자 권한(MANAGER, MASTER)을 확인한 후에 새로운 카테고리를 생성할 수 있습니다.
    - 카테고리 이름 중복 여부를 확인하여 중복 생성 방지 로직이 포함되어 있습니다.

- **카테고리 수정** :
    - 관리자 권한을 확인하여 카테고리 이름 및 활성화 상태를 업데이트할 수 있습니다.

- **카테고리 조회** : 누구나 접근 가능하며, 활성화된 모든 카테고리를 검색 및 조회할 수 있습니다.
    - 정렬 조건 : 생성일순, 수정일순

### 2. 가게 관리 기능

- **가게 생성** :
    - 관리자 권한(MANAGER, MASTER)을 확인한 후에 새로운 가게를 생성할 수 있습니다.
    - 생성 후 기본적으로 활성화 상태로 설정됩니다.
    - 생성 시 카테고리 유효성 확인 로직을 추가하여, 존재하지 않는 카테고리에 등록되지 않도록 구현했습니다.
- **가게 수정** : 관리자(MANAGER, MASTER) 또는 해당 가게 주인(OWNER)만 가게 정보를 업데이트할 수 있습니다.
- **가게 삭제** :
    - 삭제 권한은 관리자(MANAGER, MASTER) 또는 가게 주인(OWNER)에게만 허용됩니다.
    - 소프트 삭제(soft delete)로 구현되어 데이터는 유지되지만, 비활성화 처리됩니다.
- **가게 조회** :
    - 누구나 특정 가게를 조회할 수 있습니다. 전체 가게 조회, 카테고리별 가게 조회, 이름 키워드 검색이 가능합니다.
        - 정렬 조건 : 생성일순, 수정일순
        - 페이징: 기본적으로 페이지당 10개로 제한되며, 10개, 30개, 50개로 설정할 수 있습니다.
    - 평점 정보:
      조회된 가게에는 평점 정보가 포함되며, 가게 리뷰 데이터로부터 평균 평점을 계산하여 제공합니다.

### 3. 메뉴 관리 기능

- **메뉴 생성** :
    - 관리자(MANAGER, MASTER) 또는 해당 가게 주인(OWNER)만 메뉴를 등록할 수 있습니다.
    - 생성 후 기본적으로 활성화 상태로 설정됩니다.
    - 생성 시 가게 유효성 확인 로직을 추가하여, 존재하지 않는 가게에 등록되지 않도록 구현했습니다.
- **메뉴 수정** : 관리자(MANAGER, MASTER) 또는 가게 주인(OWNER)만 메뉴 정보를 업데이트할 수 있습니다.
- **메뉴 삭제** :
    - 삭제 권한은 관리자(MANAGER, MASTER) 또는 해당 가게 주인(OWNER)에게만 허용됩니다.
    - 소프트 삭제(soft delete)로 구현되어 데이터는 유지되지만, 비활성화 처리됩니다.
- **메뉴 조회** :
    - 특정 가게의 모든 메뉴를 조회하거나, 특정 이름의 메뉴를 검색하여 확인할 수 있습니다.
        - 정렬 조건 : 생성일순, 수정일순
        - 페이징: 기본적으로 페이지당 10개로 제한되며, 10개, 30개, 50개로 설정할 수 있습니다.
      </details>

</details>
---

## 🗂️ 테이블 명세서 및 ERD (Entity Relationship Diagram)

![image](https://github.com/user-attachments/assets/81911ac3-ec35-4a4f-bb98-e68b3e70a8d4)

<details>

### p_users 테이블

| 필드 이름        | 데이터 타입         | 설명                |
|--------------|----------------|-------------------|
| `id`         | `uuid`         | PRIMARY KEY       |
| `username`   | `VARCHAR(50)`  | 사용자 ID            |
| `password`   | `VARCHAR(50)`  | 사용자 비밀번호          |
| `role`       | `VARCHAR(20)`  | 사용자 권한            |
| `address`    | `VARCHAR(255)` | 사용자 주소            |
| `created_at` | `TIMESTAMP`    | 레코드 생성 시점         |
| `created_by` | `VARCHAR(255)` | 레코드 생성자(username) |
| `updated_at` | `TIMESTAMP`    | 레코드 수정 시점         |
| `updated_by` | `VARCHAR(255)` | 레코드 수정자(username) |
| `deleted_at` | `TIMESTAMP`    | 레코드 삭제 시점         |
| `deleted_by` | `VARCHAR(255)` | 레코드 삭제자(username) |

### p_categories 테이블

| 필드 이름        | 데이터 타입         | 설명                |
|--------------|----------------|-------------------|
| `id`         | `uuid`         | PRIMARY KEY       |
| `name`       | `VARCHAR(50)`  | 카테고리 이름           |
| `created_at` | `TIMESTAMP`    | 레코드 생성 시점         |
| `created_by` | `VARCHAR(255)` | 레코드 생성자(username) |
| `updated_at` | `TIMESTAMP`    | 레코드 수정 시점         |
| `updated_by` | `VARCHAR(255)` | 레코드 수정자(username) |
| `deleted_at` | `TIMESTAMP`    | 레코드 삭제 시점         |
| `deleted_by` | `VARCHAR(255)` | 레코드 삭제자(username) |

### p_ai_descriptions 테이블

| 필드 이름         | 데이터 타입         | 설명                |
|---------------|----------------|-------------------|
| `id`          | `uuid`         | PRIMARY KEY       |
| `ai_request`  | `text`         | AI 프롬프트에 입력한 내용   |
| `ai_response` | `text`         | AI 프롬프트에서 나온 결과물  |
| `created_at`  | `TIMESTAMP`    | 레코드 생성 시점         |
| `created_by`  | `VARCHAR(255)` | 레코드 생성자(username) |
| `updated_at`  | `TIMESTAMP`    | 레코드 수정 시점         |
| `updated_by`  | `VARCHAR(255)` | 레코드 수정자(username) |
| `deleted_at`  | `TIMESTAMP`    | 레코드 삭제 시점         |
| `deleted_by`  | `VARCHAR(255)` | 레코드 삭제자(username) |

### p_menus 테이블

| 필드 이름           | 데이터 타입         | 설명                      |
|-----------------|----------------|-------------------------|
| `id`            | `uuid`         | PRIMARY KEY             |
| `restaurant_id` | `uuid`         | 식당 ID, p_restaurants 참조 |
| `name`          | `VARCHAR(255)` | 메뉴 이름                   |
| `description`   | `text`         | AI API로 생성되는 메뉴 설명      |
| `price`         | `int`          | 메뉴 가격                   |
| `is_hidden`     | `boolean`      | 메뉴의 숨김 여부               |
| `created_at`    | `TIMESTAMP`    | 레코드 생성 시점               |
| `created_by`    | `VARCHAR(255)` | 레코드 생성자(username)       |
| `updated_at`    | `TIMESTAMP`    | 레코드 수정 시점               |
| `updated_by`    | `VARCHAR(255)` | 레코드 수정자(username)       |
| `deleted_at`    | `TIMESTAMP`    | 레코드 삭제 시점               |
| `deleted_by`    | `VARCHAR(255)` | 레코드 삭제자(username)       |

### p_order_items 테이블

| 필드 이름        | 데이터 타입         | 설명                        |
|--------------|----------------|---------------------------|
| `id`         | `uuid`         | PRIMARY KEY, 주문 항목 고유 식별자 |
| `order_id`   | `uuid`         | 주문 ID, p_orders 참조        |
| `menu_id`    | `uuid`         | 주문한 메뉴의 ID, p_menus 참조    |
| `quantity`   | `int`          | 메뉴 수량                     |
| `created_at` | `TIMESTAMP`    | 레코드 생성 시점                 |
| `created_by` | `VARCHAR(255)` | 레코드 생성자(username)         |
| `updated_at` | `TIMESTAMP`    | 레코드 수정 시점                 |
| `updated_by` | `VARCHAR(255)` | 레코드 수정자(username)         |
| `deleted_at` | `TIMESTAMP`    | 레코드 삭제 시점                 |
| `deleted_by` | `VARCHAR(255)` | 레코드 삭제자(username)         |

### p_reviews 테이블

| 필드 이름        | 데이터 타입         | 설명                 |
|--------------|----------------|--------------------|
| `id`         | `uuid`         | PRIMARY KEY        |
| `order_id`   | `uuid`         | 주문 id, p_orders 참조 |
| `rating`     | `int`          | 별점 (1~5)           |
| `comment`    | `text`         | 댓글                 |
| `created_at` | `TIMESTAMP`    | 레코드 생성 시점          |
| `created_by` | `VARCHAR(255)` | 레코드 생성자(username)  |
| `updated_at` | `TIMESTAMP`    | 레코드 수정 시점          |
| `updated_by` | `VARCHAR(255)` | 레코드 수정자(username)  |
| `deleted_at` | `TIMESTAMP`    | 레코드 삭제 시점          |
| `deleted_by` | `VARCHAR(255)` | 레코드 삭제자(username)  |

### p_restraunts 테이블

| 필드 이름         | 데이터 타입         | 설명                                  |
|---------------|----------------|-------------------------------------|
| `id`          | `uuid`         | PRIMARY KEY                         |
| `name`        | `VARCHAR(255)` | 가게 이름                               |
| `category_id` | `uuid`         | 가게가 속하고 있는 카테고리, p_categories.id 참조 |
| `owner_id`    | `INTEGER`      | 가게 소유자, p_users.id 참조               |
| `address`     | `VARCHAR(255)` | 가게 주소지                              |
| `is_hidden`   | `BOOLEAN`      | 가게의 숨김 처리 여부, default: `FALSE`      |
| `created_at`  | `TIMESTAMP`    | 레코드 생성 시점                           |
| `created_by`  | `VARCHAR(255)` | 레코드 생성자(username)                   |
| `updated_at`  | `TIMESTAMP`    | 레코드 수정 시점                           |
| `updated_by`  | `VARCHAR(255)` | 레코드 수정자(username)                   |
| `deleted_at`  | `TIMESTAMP`    | 레코드 삭제 시점                           |
| `deleted_by`  | `VARCHAR(255)` | 레코드 삭제자(username)                   |

### p_orders 테이블

| 필드 이름              | 데이터 타입         | 설명                      |
|--------------------|----------------|-------------------------|
| `id`               | `UUID`         | PRIMARY KEY             |
| `user_id`          | `INTEGER`      | 사용자 ID, p_users 참조      |
| `restaurant_id`    | `UUID`         | 식당 ID, p_restaurants 참조 |
| `order_type`       | `VARCHAR(20)`  | 온라인 / 오프라인 주문 식별        |
| `status`           | `VARCHAR(20)`  | 현재 주문 상태                |
| `total_price`      | `int`          | 주문의 총 가격                |
| `delivery_address` | `VARCHAR(255)` | 사용자 배송지                 |
| `delivery_request` | `text`         | 사용자 배송 요청사항             |
| `created_at`       | `TIMESTAMP`    | 레코드 생성 시점               |
| `created_by`       | `VARCHAR(255)` | 레코드 생성자(username)       |
| `updated_at`       | `TIMESTAMP`    | 레코드 수정 시점               |
| `updated_by`       | `VARCHAR(255)` | 레코드 수정자(username)       |
| `cancelled_at`     | `TIMESTAMP`    | 레코드 취소 시점               |
| `deleted_at`       | `TIMESTAMP`    | 레코드 삭제 시점               |
| `deleted_by`       | `VARCHAR(255)` | 레코드 삭제자(username)       |
|                    |                |                         |

### p_payments 테이블

| 필드 이름            | 데이터 타입         | 설명                                   |
|------------------|----------------|--------------------------------------|
| `id`             | `uuid`         | PRIMARY KEY                          |
| `order_id`       | `uuid`         | 결제와 연관된 주문 ID, p_orders 참조           |
| `amount`         | `int`          | 결제된 총 금액                             |
| `payment_method` | `VARCHAR(50)`  | 결제 방식을 나타내는 문자열 (카드 결제만 지원하므로 생략 가능) |
| `pg_response`    | `TEXT`         | 결제 게이트웨이(PG사)에서 받은 응답 저장             |
| `created_at`     | `TIMESTAMP`    | 레코드 생성 시점                            |
| `created_by`     | `VARCHAR(255)` | 레코드 생성자(username)                    |
| `updated_at`     | `TIMESTAMP`    | 레코드 수정 시점                            |
| `updated_by`     | `VARCHAR(255)` | 레코드 수정자(username)                    |
| `deleted_at`     | `TIMESTAMP`    | 레코드 삭제 시점                            |
| `deleted_by`     | `VARCHAR(255)` | 레코드 삭제자(username)                    |

</details>


---
