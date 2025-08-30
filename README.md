# Loan Management System - Backend API

A comprehensive loan management system backend that simulates real-world loan application processing with secure authentication, multi-step approval workflows, and role-based access control.

## 🎯 What This Project Does

This Spring Boot application provides:
- **Secure User Authentication** - JWT-based login/registration system
- **Loan Application Management** - Users can submit and track loan applications
- **Multi-step Approval Workflow** - Configurable approval process with different steps
- **Role-based Access Control** - Admin and User roles with different permissions
- **RESTful APIs** - Complete CRUD operations for loan management
- **Database Integration** - PostgreSQL database with JPA/Hibernate

## 🏗️ Architecture

- **Backend Framework**: Spring Boot 3.5.5
- **Security**: Spring Security with JWT tokens
- **Database**: PostgreSQL (Neon DB)
- **ORM**: Spring Data JPA with Hibernate
- **Build Tool**: Maven
- **Java Version**: 17

## 📋 Features

### Authentication & Authorization
- User registration and login
- JWT token-based authentication
- Role-based access control (ADMIN, USER)
- Secure password encryption with BCrypt

### Loan Management
- Submit loan applications
- View loan application status
- Track approval workflow progress
- Admin can view all loan applications

### Approval Workflow
- Multi-step approval process
- Admin can update approval steps
- Configurable workflow stages
- Real-time status tracking

## 🚀 Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- PostgreSQL database (or use Neon DB)
- Git

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd assignment
   ```

2. **Create local configuration**
   Create `src/main/resources/application-local.properties`:
   ```properties
   # Local Development Database Configuration
   spring.datasource.url=jdbc:postgresql://your-host:5432/your-database
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   
   # JPA Configuration for local development
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   ```

3. **Run with local profile**
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=local
   ```
   Or using Maven:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=local
   ```

4. **Build the application**
   ```bash
   ./mvnw clean install
   ```

### Production Deployment (Railway)

1. **Set Environment Variables** in Railway dashboard:
   ```
   DATABASE_URL=jdbc:postgresql://your-neon-host/database
   DATABASE_USERNAME=your-username
   DATABASE_PASSWORD=your-password
   SPRING_PROFILES_ACTIVE=prod
   ```

2. **Deploy** - Railway will automatically build and deploy

## 📝 API Endpoints

### Authentication Endpoints
```
POST /api/auth/register     - Register new user
POST /api/auth/login        - User login
```

### Loan Management Endpoints
```
POST /api/loans/apply       - Submit loan application (USER)
GET  /api/loans/my          - Get user's loan applications (USER)
GET  /api/loans/all         - Get all loan applications (ADMIN)
```

### Approval Management Endpoints
```
PUT  /api/approval/update-step    - Update approval step (ADMIN)
GET  /api/approval/step/{id}      - Get approval step details (ADMIN)
```

### Health Check
```
GET  /actuator/health       - Application health status
```

## 🗄️ Database Schema

### Users Table
- `id` (Primary Key)
- `username` (Unique)
- `email` (Unique)
- `password` (Encrypted)
- `role` (USER/ADMIN)

### Loan Applications Table
- `id` (Primary Key)
- `user_id` (Foreign Key)
- `amount`
- `purpose`
- `status`
- `application_date`

### Approval Steps Table
- `id` (Primary Key)
- `loan_application_id` (Foreign Key)
- `step_name`
- `status`
- `comments`

## 🔧 Configuration

### Application Profiles
- **default**: Uses environment variables (for production)
- **local**: Uses `application-local.properties` (for development)
- **prod**: Production configuration with optimizations

### Security Configuration
- JWT token expiration: Configurable
- CORS enabled for frontend integration
- Actuator endpoints secured except health check

### Database Configuration
- Connection pooling with HikariCP
- Automatic schema updates
- PostgreSQL dialect optimizations

## 📦 Build & Deployment

### Local Build
```bash
./mvnw clean package
java -jar target/assignment-0.0.1-SNAPSHOT.jar --spring.profiles.active=local
```

### Production Build
```bash
./mvnw clean package -Pprod
java -jar target/assignment-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## 🌐 Frontend Integration

This backend supports CORS for the following origins:
- `http://localhost:*` (local development)
- `https://twinline-loan-management.vercel.app` (production frontend)

## 📂 Project Structure

```
src/
├── main/
│   ├── java/com/twinline/assignment/
│   │   ├── config/          # Security & configuration classes
│   │   ├── controller/      # REST API controllers
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── entity/         # JPA entities
│   │   ├── filter/         # JWT authentication filter
│   │   ├── repository/     # Data access layer
│   │   ├── service/        # Business logic layer
│   │   └── util/           # Utility classes
│   └── resources/
│       ├── application.properties
│       ├── application-local.properties (create manually)
│       └── application-prod.properties
```

## 🔐 Security Features

- JWT token-based authentication
- BCrypt password hashing
- CORS configuration
- Role-based authorization
- SQL injection prevention via JPA
- XSS protection headers

## 📊 Monitoring

- Spring Boot Actuator health checks
- Application metrics via `/actuator/info`
- Database connection monitoring
- Custom logging configuration

## 🚨 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify environment variables are set correctly
   - Check database URL format and credentials

2. **JWT Authentication Issues**
   - Ensure JWT secret is properly configured
   - Check token expiration settings

3. **CORS Issues**
   - Verify frontend URL is whitelisted
   - Check request headers and methods

4. **Health Check Failing**
   - Ensure actuator endpoints are exposed
   - Check database connectivity

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is created for interview/assessment purposes.

## 📞 Support

For questions or support, please contact me via email.