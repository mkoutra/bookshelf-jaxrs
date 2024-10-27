# Bookshelf 📚

**This project serves as a lightweight introduction to working with the JAX-RS API,
illustrating core RESTful principles with a minimalistic model.**

A **simple bookshelf application** built to demonstrate the basics of the JAX-RS API.

> **Note:** This implementation does not include a database of books or authors; users add individual books directly to their shelves.
Bookshelf enables users to add books to a virtual shelf with minimal information:

## Features

- **User Registration and Authorization**:
    - Secure user registration and login system to manage individual shelves.
    - MySQL database integration for storing user information and authorization details.

- **Add a Book**: Users can add a book by providing:
    - Title
    - Author
    - Release Year

## Usage

- Register a new user.
- Log in to start adding books to your personal shelf.
- Add a Book by providing its title, author, and release year.

## TODO

- Extend the data model for more complex interactions.
- Implement database persistence for books and authors.
- Add support for additional book fields (e.g., genre, description, ISBN).
- Implement user-specific shelf views and book management features (edit, delete).