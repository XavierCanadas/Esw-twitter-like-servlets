# Software Engineering of Web Applications - Course Project



## Overview
**Netopolis** is a social media web application developed as part of the Software Engineering course project. This platform provides a Twitter-like experience with community-based features.

**Key Features:**
- **User Management**: Registration, authentication, and profile management with customizable avatars
- **Tweet System**: Create, edit, delete, and view tweets with support for comments and threaded discussions
- **Social Interactions**: Like tweets, follow/unfollow users, and view user profiles.
- **Discovery**: View tweets and users in a feed, with separate experiences for logged-in and non-logged users
- **Responsive UI**: Interface features a theme inspired by Ancient Greece

**Architecture:**
- **Backend**: Java-based MVC architecture with Servlets and JSP
- **Database**: MySQL with Docker containerization
- **Pattern**: Repository and Service layer pattern for data access and business logic
- **Deployment**: Dockerized environment with Docker Compose for easy setup

## Setup Instructions
1. Open Docker Desktop and ensure it is running.
2. Run the build script to clean the maven project and build the package:
   ```bash
   ./build.sh
   ```
3. Start the Docker containers using Docker Compose:
   ```bash
   docker-compose up --build 
   ```
4. Access the application at http://localhost:8080.

## Team Members
- **Xavier Cañadas** - [GitHub](https://github.com/XavierCanadas)
- **Roman Atanasiu** - [GitHub](https://github.com/RomanAtanasiu)
- **Raúl Díaz** - [GitHub](https://github.com/rauldzzz)


## Demo
A live demo of the application is available at: [Demo in youyube](https://youtu.be/0MclMMCPUUg)

### Screenshots
![Screenshot 2025-10-06 at 23.29.54.png](screenshots/Screenshot%202025-10-06%20at%2023.29.54.png)
![Screenshot 2025-10-06 at 23.30.48.png](screenshots/Screenshot%202025-10-06%20at%2023.30.48.png)
![Screenshot 2025-10-06 at 23.30.57.png](screenshots/Screenshot%202025-10-06%20at%2023.30.57.png)

