# eTaskify

## Overview

eTaskify is a task management application designed to help organizations manage and track their daily tasks. The application allows users to create tasks, assign them to members, and monitor their progress.

## Features

- **Sign Up**: Enterprise customers can sign up as an admin user with a unique email address, password and organization name to create an organization account.
- **Sign In**: Users can sign in to their account with their email address and password.

- **User Management**: Admin users can manage users within the organization by creating, updating, and deleting users of their organization.
- **User Roles**: Created user have only user role by default. 

- **Task Creation**: Admin users can create tasks with details such as title, description, deadline, and status.
- **Task Assignment**: Tasks can be assigned to one or more users within the organization.
- **Task Tracking**: All users can view and track the status of tasks.
- **Task Update**: Admin users can update the details of tasks such as title, description, deadline, and status.
- **Email Sending**: Users will be notified upon task assignment.

## Getting Started

### Prerequisites

Before running eTaskify, ensure you have the following installed:
- Java JDK 17 or higher
- Gradle (for building the application)
- MySQL 

### Configuration

1. **Database Configuration**: Configure the database connection in `src/main/resources/application.yml`. Update the following properties with your database details:

    ```yaml
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/etaskify_db
        username: your_db_username
        password: your_db_password
    ```

2. **Mail Configuration**: Set up mail properties for email functionality in `application.yml`:

    ```yaml
    spring:
      mail:
        host: smtp.gmail.com
        port: 587
        username: your_email@gmail.com
        password: your_email_password
    ```
   
3. **JWT Configuration**: Set up your secret-key in `application.yml`:

    ```yaml
   security:
   secret-key: ${SECRET_KEY}
   ```
   


### Building the Application
You can build and run application via IDE using UI or command line.
To build in command line you need to add Gradle installation directory to the system's PATH environment variable and run the following command in the root directory of the project:

```bash
gradle bootRun
```

### Access the Application
- Once the application is running, you can access it via:
http://localhost:8086
- For API documentation and testing, visit: http://localhost:8086/swagger-ui/index.html

