# Java 17 JDK asosida
FROM eclipse-temurin:17-jdk-alpine

# Ishchi papka
WORKDIR /app

# Maven o‘rnatamiz
RUN apk add --no-cache maven

# Loyihani konteynerga nusxalaymiz
COPY . .

# Build (testlarsiz)
RUN mvn clean package -DskipTests

# Portni ochamiz
EXPOSE 8080

# Jar faylni ishga tushiramiz (target ichidagi)
CMD ["sh", "-c", "java -jar target/*.jar"]
