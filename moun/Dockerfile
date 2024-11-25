# 1. OpenJDK 17 이미지를 사용
FROM openjdk:17-jdk-slim


# 2. Gradle 빌드된 JAR 파일을 컨테이너로 복사
ARG JAR_FILE=build/libs/api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
# 3. 8080 포트를 외부에 노출
EXPOSE 8080
ENV PROFILE="test"

# 4. Spring Boot 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar","--spring.profiles.active=test"]

