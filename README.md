

# Moun API V2
<img width="1431" alt="Screenshot 2024-11-19 at 21 06 27" src="https://github.com/user-attachments/assets/36f68cda-c80b-49d3-9699-172d74f30468">

## Table of Contents
1. [Project Overview](#1-project-overview)  
2. [Features](#2-features)  
3. [Architecture](#3-architecture)  
4. [Technology Stack](#4-technology-stack)  
5. [Domain-Driven Design (DDD) Implementation](#5-domain-driven-design-ddd-implementation)  
6. [Installation and Setup](#6-installation-and-setup)  
7. [API Endpoints](#7-api-endpoints)  
8. [Usage Example](#8-usage-example)  
9. [Contribution Guidelines](#9-contribution-guidelines)  
10. [License](#10-license)

---

## 1. Project Overview
Moun API V2 is a backend service powering a music auction and sales platform. It enables users to bid on and purchase unique music assets. Designed with Domain-Driven Design (DDD), the API ensures a robust, scalable, and maintainable architecture.

---

## 2. Features
- **Music Auctions**: Create and manage auctions with real-time bidding.  
- **User Management**: Authentication and profile handling.  
- **Payment Integration**: Secure payment processing for purchases.  
- **Notification System**: Alerts for bidding updates and auction results.  

---

## 3. Architecture
- **Domain-Driven Design (DDD)**: Emphasizes modularity and separation of business logic.  
- **Layered Architecture**: Divided into Application, Domain, and Infrastructure layers.  
- **Microservice Ready**: Flexible enough for future modularization.  

---

## 4. Technology Stack
- **Language**: Java
- **Framework**: Spring Boot 
- **Database**: MySQL with an ORM (JPA/Hibernate)  
- **Authentication**: JWT-based security + OAuth2.0
- **Deployment**: Docker and CI/CD pipelines  

---

## 5. Domain-Driven Design (DDD) Implementation
- **Core Domains**:
  - *Auctions*: Bidding, auction creation, and status tracking.  
  - *Members*: Registration, authentication, and profiles.  
  - *Songs*: Upload, Remove, Get Query songs.
- **Aggregates and Entities**: Central objects encapsulating logic.  
- **Repositories**: Persistent storage abstractions.  

---

## 6. Installation and Setup
1. Clone the repository:  
   ```bash
   git clone https://github.com/moun-io/moun.api.v2.git
   cd moun.api.v2
   ```  
2. Configure environment variables in `.env`.  
3. Build and run the application:  
   ```bash
   ./gradlew bootRun
   ```  

---

## 7. API Endpoints

### Authentication
| Endpoint          | Method | Description               |
|-------------------|--------|---------------------------|
| `/auth/check`  | POST   | check if the token is valid |
| `/auth/login`     | POST   | Authenticate a user (sign in) |

### Member
| Endpoint                        | Method | Description                            |
|---------------------------------|--------|----------------------------------------|
| `/members`                      | POST   | Create a new member + Sign-up                  |
| `/members`                      | GET    | Get all members                        |
| `/members?position=x`           | GET    | Get members by position                |
| `/members/{id}`                 | GET    | Get member by ID                       |
| `/members/{id}`                 | PUT    | Update artist information             |
| `/members/{id}`                 | DELETE | Delete artist                          |

### Song/Auction
| Endpoint                        | Method | Description                            |
|---------------------------------|--------|----------------------------------------|
| `/songs/{id}`                   | GET    | Get song by ID                         |
| `/songs`                        | GET    | Get songs by date                      |
| `/songs?genre=x&title=x&is_desc=false` | GET    | Get songs by query (genre, title, desc) |
| `/songs?sortby=views&limit=4`   | GET    | Get top 4 songs by popularity          |
| `/songs?sortby=expired_date&limit=4&isdesc=true` | GET    | Get top 4 songs by expired date |
| `/songs`                        | POST   | Create a new song                      |
| `/songs/{id}`                   | PUT    | Update song                            |
| `/songs/{id}`                   | DELETE | Delete a song                          |

### Bids
| Endpoint                        | Method | Description                            |
|---------------------------------|--------|----------------------------------------|
| `/bids`                         | GET    | Get all your bids                      |
| `/bids/songs/{songid}`          | POST   | Create a bid on a song                 |
| `/bids/{id}`                    | DELETE | Delete your bid from a song            |

--- 

You can copy and paste this into your README file. Let me know if you'd like further adjustments!
---

## 8. Usage Example
```bash
# Example: Create a new auction
curl -X POST -H "Authorization: Bearer <token>" \
     -d '{"title":"New Song", "startingPrice":100}' \
     http://localhost:8080/auctions
```

---

## 9. Contribution Guidelines
We welcome contributions to improve the platform! Please follow these steps:  
1. Fork the repository.  
2. Create a feature branch.  
3. Submit a pull request with a detailed description.  

---

## 10. License
This project is licensed under the MIT License. See the `LICENSE` file for details.

---

This structure ensures clarity and provides comprehensive documentation for developers. Let me know if you'd like me to flesh out any specific sections!
