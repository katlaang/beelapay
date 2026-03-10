# Jira Backlog View (Phase 1 MVP)

## Scope Snapshot
- Total stories: 35
- Sprint 1: 7 stories, 49 points
- Sprint 2: 11 stories, 61 points
- Sprint 3: 11 stories, 70 points
- Sprint 4: 6 stories, 33 points

## Sprint 1 (Foundation)
| Story ID | Summary | Priority | Points |
| --- | --- | --- | --- |
| BLP-101 | Master registration with OTP | P0 | 5 |
| BLP-102 | KYC workflow by residency | P0 | 8 |
| BLP-103 | PEP and sanctions screening | P0 | 5 |
| BLP-111 | Create attached account | P0 | 5 |
| BLP-121 | Create wallet and balance model | P0 | 8 |
| BLP-122 | Double-entry ledger posting engine | P0 | 13 |
| BLP-181 | SMS OTP service | P0 | 5 |

## Sprint 2 (Core Transaction Layer)
| Story ID | Summary | Priority | Points |
| --- | --- | --- | --- |
| BLP-104 | KYC operations queue | P1 | 5 |
| BLP-112 | Permission profiles for attached users | P0 | 3 |
| BLP-113 | Spending limits for attached users | P0 | 5 |
| BLP-123 | Ledger holds and release | P1 | 5 |
| BLP-131 | Rate ingestion adapter | P1 | 5 |
| BLP-132 | Quote service | P0 | 8 |
| BLP-141 | Wallet-to-wallet transfer API | P0 | 8 |
| BLP-142 | Master to attached transfer flow | P0 | 3 |
| BLP-151 | USSD session framework | P0 | 8 |
| BLP-182 | Transaction confirmation alerts | P0 | 3 |
| BLP-202 | Audit event store | P0 | 8 |

## Sprint 3 (Pilot Capability)
| Story ID | Summary | Priority | Points |
| --- | --- | --- | --- |
| BLP-124 | Reversal and correction workflow | P1 | 8 |
| BLP-133 | Conversion posting integration | P1 | 5 |
| BLP-143 | Transaction history endpoints | P1 | 5 |
| BLP-152 | USSD balance and mini-statement | P0 | 5 |
| BLP-153 | USSD send money flow | P0 | 8 |
| BLP-161 | Merchant onboarding workflow | P1 | 5 |
| BLP-162 | Merchant payment authorization | P0 | 8 |
| BLP-171 | Payout rail adapter (first rail) | P0 | 13 |
| BLP-172 | Partner callback handler | P0 | 5 |
| BLP-183 | Master alert on attached spend | P1 | 3 |
| BLP-191 | Transaction search console | P0 | 5 |

## Sprint 4 (Operational Hardening)
| Story ID | Summary | Priority | Points |
| --- | --- | --- | --- |
| BLP-163 | Merchant settlement status | P1 | 5 |
| BLP-173 | Payout exception management | P1 | 5 |
| BLP-192 | Daily ledger reconciliation job | P0 | 8 |
| BLP-193 | Suspicious activity rules | P1 | 5 |
| BLP-201 | MFA for master accounts | P1 | 5 |
| BLP-203 | Operational monitoring and alerting | P1 | 5 |

## Critical Dependency Chain
1. BLP-101 -> BLP-102 -> BLP-111 -> BLP-112 -> BLP-113
2. BLP-121 -> BLP-122 -> BLP-141 -> BLP-162
3. BLP-131 -> BLP-132 -> BLP-133
4. BLP-141 -> BLP-171 -> BLP-172 -> BLP-173
5. BLP-122 -> BLP-192 and BLP-202

## Import Notes
- File for Jira import: `docs/jira-backlog.csv`
- All stories include: epic, priority, sprint, story points, dependencies, and acceptance criteria.
- If your Jira project requires specific field IDs, map columns during import and keep `Story ID` as external key.
