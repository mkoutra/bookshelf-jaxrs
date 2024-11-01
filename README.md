# Bookshelf

A **simple bookshelf application** built to demonstrate the basics of the JAX-RS API.

## Features

### Users
- **Registration** and **Authentication** using JWT.
- **Authorization**: Only users with the `ADMIN` role can access `/users/**` endpoints.
- Manage users: Get by ID, get all, and delete users.

### Books
- **Fields**: `title`, `author`, `releaseYear`.
- Full **CRUD operations**.
- Retrieve books using filters.

### Roles
- **ADMIN**: Can view and delete other users.
- **SIMPLE_USER**: Standard user role.

## API Endpoints

**Base URL**: `http://localhost:8080/bookshelf/api`

### Users
- **GET** `/users/{userId}` - Retrieve a user by their ID.
- **GET** `/users/` - Retrieve a list of all users.
- **DELETE** `/users/{userId}` - Delete a user by their ID.

### Auth
- **POST** `/auth/login` - Log in a user. Example:
  ```json
  {
    "username": "joeDoe",
    "password": "Aa1234567*"
  }
  ```
- **POST** `/auth/register` - Register a new user.
  ```json
  {
    "username": "joeDoe",
    "password": "Aa1234567*",
    "confirmPassword": "Aa1234567*",
    "role": "ADMIN"
  }
  ```

### Books

- **POST** `/books/` - Add a new book.
  ```json
  {
    "title": "The Snowman",
    "author": "Jo Nesbo",
    "releaseYear": "2007"
  }
  ```
- **DELETE** `/books/{bookId}` - Delete a book by its ID.
- **PUT** `/books/` - Update book details.
  ```json
  {
    "title": "The Snowman",
    "author": "J. Nesbo",
    "releaseYear": "2007"
  }
  ```
- **GET** `/books/{bookId}` - Retrieve a book by its ID.
- **GET** `/books/{optional filters}` - Retrieve books based on filters (title, author, releaseYear).

## Usage

### Database Setup
A MySQL database is used for this project.
Our database is called `bookshelfdb`, with a user called `bookkeeper` and with password `12345`.

Update `/src/main/resources/META-INF/persistence.xml` to set your database details:
```xml
<properties>
  .
  .
  .
  <property name="hibernate.hikari.dataSource.url" value="jdbc:mysql://localhost:3306/YOUR_DATABASE?serverTimezone=UTC" />
  <property name="hibernate.hikari.dataSource.user" value="YOUR_USERNAME" />
  <property name="hibernate.hikari.dataSource.password" value="YOUR_PASSWORD" />
  .
  .
</properties>
```

### JWT Secret Key
- The JWT secret key used to generate tokens is stored in the `config.properties` file located in the `/src/main/resources` directory.
- **Important**: Change the default secret key to a strong, unique value for security.
### Build and Deploy
- For Linux
  ```bash
  mvnw clean package
  ```
- For Windows
  ```bash
  mvnw.cmd clean package
  ```

### Deploy
Copy the `.war` file from the `/target` directory into the `webapps/` 
directory of your local Tomcat server.

### Using the API
- Use Postman or any other API testing tool to make requests.

- Register a new user.
- Log in to start adding books to your personal shelf.
- Add a Book by providing its title, author, and release year.

## TODO

- Integrate **Swagger** for API documentation.
- Add **Docker** support and a docker-compose.yml file.
- Extend the data model for more complex interactions.
- Implement database persistence for books and authors.
- Add support for additional book fields (e.g., genre, description, ISBN).