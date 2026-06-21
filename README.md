# Docker Learning Notes

A practical reference of Docker commands learned while containerizing a Spring Boot + MySQL application.

---

## 1. Basic Docker Commands

```bash
docker --version          # Check Docker version installed
docker ps                 # List running containers
docker ps -a              # List all containers (including stopped ones)
docker ps -q              # List only container IDs of running containers
docker images             # List all locally available images
```

---

## 2. Running Containers

```bash
# Run a container (exits immediately if no process keeps it alive)
docker run ubuntu

# Run in detached mode (background)
docker run -d ubuntu

# Run interactively with a terminal (stay inside the container)
docker run -it ubuntu

# Run with port mapping (host_port:container_port)
docker run -p 8023:8023 survey

# Run in detached mode with port mapping
docker run -d -p 8023:8023 survey

# Run with a named volume mounted
docker run -v volname:/app -d -p 8023:8023 survey
```

---

## 3. Building Images

```bash
# Build from Dockerfile in current directory (no tag)
docker build .

# Build with a name and tag
docker build -t spring-survey:0.1 .
docker build -t spring-survey:0.2 .
docker build -t survey .
docker build -t survay:1.1 .
```

> **Note:** `-t` stands for tag. Format is `name:version`. Always run from the directory containing the Dockerfile.

---

## 4. Starting and Stopping Containers

```bash
docker start <container_id_or_name>    # Start a stopped container
docker stop <container_id_or_name>     # Stop a running container
```

---

## 5. Executing Commands Inside a Running Container

```bash
# Open an interactive bash shell inside a running container
docker exec -it <container_id> bash

# Example with short container ID
docker exec -it 87a68e4afa31 bash
```

> **Tip:** You only need the first few characters of the container ID — enough to be unique.

---

## 6. Cleaning Up

```bash
# Remove all stopped containers
docker container prune

# Remove a specific image (force)
docker rmi -f <image_id>

# List all volumes
docker volume ls

# Remove a specific volume
docker volume rm survey_my-vol
docker volume rm volname
```

---

## 7. Networking

```bash
# List all Docker networks
docker network ls
```

---

## 8. Docker Compose

```bash
# Start all services defined in docker-compose.yml
docker compose up

# Start in detached (background) mode
docker compose up -d

# Start only a specific service
docker compose up mysql -d

# Stop and remove containers (keeps volumes)
docker compose down
```

---

## 9. Common Error & Fix

### Error: `Connection refused` when app tries to reach MySQL

**Cause:** The app container was trying to connect to `mysql:3306` but MySQL wasn't running or reachable.

**Fix:** Use Docker Compose so both services share the same network. The app can then reach MySQL using the service name `mysql` as the hostname.

```yaml
# docker-compose.yml
services:
  mysql:
    image: mysql:8.0
    ports:
      - "3307:3306"   # expose to host on 3307 (3306 was taken by local MySQL)
  app:
    build: .
    ports:
      - "8023:8023"
```

```properties
# application.properties (running app locally)
spring.datasource.url=jdbc:mysql://localhost:3307/learning

# application.properties (running app inside Docker)
spring.datasource.url=jdbc:mysql://mysql:3306/learning
```

---

## 10. Build & Run Workflow (Full Stack)

```bash
# 1. Build the JAR
./mvnw clean package -DskipTests

# 2. Build Docker image
docker build -t survey .

# 3. Start services with Docker Compose
docker compose up -d

# 4. To rebuild after code changes
docker compose down
./mvnw clean package -DskipTests
docker compose up -d
```

---

## 11. Run MySQL in Docker, App Locally

```bash
# Start only MySQL
docker compose up mysql -d

# Run the Spring Boot app locally
./mvnw spring-boot:run
```

Connect to MySQL from MySQL Workbench or any GUI:
- **Host:** `127.0.0.1`
- **Port:** `3307`
- **User:** `root`
- **Password:** `password`
- **Database:** `learning`
