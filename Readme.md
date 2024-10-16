# Schedule Management Application (일정 관리 앱)

## Brief Description (소개)
“Schedule Management App” is a basic schedule planner that allows different registered users to read, modify and delete schedules they have created. They can also add, modify and delete comments on each other's posts.
'일정 관리 앱'은 서로 다른 등록된 사용자가 자신이 만든 일정을 읽고, 수정하고, 삭제할 수 있는 기본 일정 계획 도구입니다. 사용자는 서로의 게시물에 대해 댓글을 추가, 수정 및 삭제할 수 있는 간단한 앱입니다. 

## Project Environment 
- Project Type: Individual Project (개인 프로젝트)
- Spring Boot v3.3.4
- IDE: IntelliJ 2024.1.6
- Runtime version: 17.0.11+1-b1207.30 aarch64
- VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.

## Requirements (개발 조건)
- 모든 Table은 고유 식별자(ID)를 가집니다.
- 3 Layer Architecture 에 따라 각 Layer의 목적에 맞게 개발합니다.
- CRUD 필수 기능은 모두 Database 연결 및 JPA를 사용해서 개발합니다.
JDBC와 Spring Security는 사용하지 않습니다.
인증/인가 절차는 JWT를 활용하여 개발합니다.
JPA의 연관관계는 양방향으로 구현합니다.

## API Table (API 명세서)
