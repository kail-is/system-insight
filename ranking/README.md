# 게임 TOP 10 순위 조회

🎯 게임 TOP 10 순위 조회 API

### ✅ 목적

사용자들이 게임 플레이 후 점수를 등록하면, 해당 데이터를 기반으로 **최상위 10명의 플레이어 랭킹 정보를 조회**할 수 있도록 한다. 이 API는 리더보드 화면 등에서 활용되어, 경쟁 심리를 유도하고 게임의 몰입도를 높이는 데 기여한다.

---

### 📥 점수 등록 요청 (사전 조건) - 100명

- 사용자는 게임 플레이 후 다음 정보를 포함하여 점수를 등록한다:

```json
POST /game/scores
{
  "nickname": "PlayerOne",
  "userId": "khs"
  "profileImageUrl": "https://cdn.example.com/images/player1.png",
  "score": 150
}

```

- 서버는 닉네임, 이미지 주소, 점수, 그리고 플레이 시간을 저장한다.
- 동일 사용자가 여러 번 등록할 수 있으며, 점수는 누적 또는 최근 기록 기준으로 계산할 수 있음 (정책에 따라 결정).

---

### 📤 게임 TOP 10 순위 조회

```
GET /game-rankings/top10
```

### ✔ 응답 필드

| 필드명 | 설명 |
| --- | --- |
| `rank` | 플레이어의 순위 (1~10) |
| `nickname` | 사용자 닉네임 |
| `profileImageUrl` | 사용자 프로필 이미지 URL |
| `totalScore` | 누적 점수 또는 최고 점수 (정책에 따라) |
| `lastPlayedAt` | 마지막으로 플레이한 시간 (ISO 8601 형식) |

### 📦 응답 예시

```json

[
  {
    "rank": 1,
    "nickname": "DragonSlayer",
    "profileImageUrl": "https://cdn.example.com/images/dragon.png",
    "totalScore": 980,
    "lastPlayedAt": "2025-03-27T12:45:00Z"
  },
  {
    "rank": 2,
    "nickname": "PixelWarrior",
    "profileImageUrl": "https://cdn.example.com/images/pixel.png",
    "totalScore": 920,
    "lastPlayedAt": "2025-03-27T11:30:00Z"
  }
  // ...
]

```

성능 테스트는 - ngrinder

- [https://velog.io/@gjwjdghk123/nGrinder-설치하기-및-테스트-해보기](https://velog.io/@gjwjdghk123/nGrinder-%EC%84%A4%EC%B9%98%ED%95%98%EA%B8%B0-%EB%B0%8F-%ED%85%8C%EC%8A%A4%ED%8A%B8-%ED%95%B4%EB%B3%B4%EA%B8%B0)

성능 테스트 기준 ( TPS 측정 )

1. vuser99, process 3, thread 33
2. vuser1000,  process 10, thread 100
3. vuser3000, process 10, thread 3000