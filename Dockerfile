# ==============================================
# STAGE 1: BUILD
# Usa a imagem oficial do Maven com Java 17 para compilar o projeto.
# O Maven baixa as dependências e gera o arquivo .jar executável.
# ==============================================
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copia o pom.xml primeiro e baixa as dependências separadamente.
# Isso aproveita o cache do Docker: se o pom.xml não mudar,
# o Docker não precisa re-baixar as libs a cada build.
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Agora copia o código-fonte e compila o projeto.
# -DskipTests pula os testes para agilizar o build no servidor.
COPY src ./src
RUN mvn clean package -DskipTests


# ==============================================
# STAGE 2: RUN
# Usa uma imagem mínima com apenas o Java Runtime (JRE).
# Isso mantém o container final pequeno (~200MB vs ~600MB do Maven).
# ==============================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia somente o .jar gerado no stage anterior
COPY --from=build /app/target/smartlab-monitor-0.0.1-SNAPSHOT.jar app.jar

# Informa ao Docker que o container usa a porta 8080
EXPOSE 8080

# Comando de inicialização da aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
