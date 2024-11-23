# Use an official Maven/Java image to build the app
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and download dependencies (for faster rebuilds)
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy the source code and package the app
COPY src /app/src
RUN mvn clean package -DskipTests

# Now we need to use a lighter image to run the app
FROM openjdk:17-slim

# Set the working directory
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port
EXPOSE 8080

# Command to run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
