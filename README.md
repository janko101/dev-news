## Welcome to Dev News API
A Spring API for a developers news site.
## Project Specifications
Users should be able to create articles, comment on them and post their reactions. The project
should be a Spring application, using Gradle, Spring Web, Spring JPA, and PostgreSQL.
## Author
[Janko Radakovic](https://github.com/janko101)

## Getting Started

This exercise can be run by first starting Docker:

`docker-compose up `

And then can be run by using the Gradle bootRun command:

`gradlew bootRun `

Following this, you can send commands via curl or Postman.

### Articles
Endpoints for the article API:

| HTTP Method | HTTP Path | Action |
| ------------|-----------|--------|
| `GET`    | `/articles`      | return all articles |
| `GET`    | `/articles/{id}` | return a specific article based on the provided id|
| `POST`   | `/articles`      | create a new article|
| `PUT`    | `/articles/{id}` | update the given article|
| `DELETE` | `/articles/{id}` | delete the given article|

### Comments

Endpoints for the comment API:

| HTTP Method | HTTP Path | Action |
| ------------|-----------|--------|
| `GET`    | `/articles/{articleId}/comments`    | return all comments on article given by `articleId` |
| `GET`    | `/comments?authorName={authorName}` | return all comments made by author given by `authorName` |
| `POST`   | `/articles/{articleId}/comments`    | create a new comment on article given by `articleId` |
| `PUT`    | `/comments/{id}`                    | update the given comment |
| `DELETE` | `/comments/{id}`                    | delete the given comment |

### Topics

Endpoints for the topic API:

| HTTP Method | HTTP Path | Action |
| ------------|-----------|--------|
| `GET`    | `/topics` | return all topics |
| `GET`    | `/articles/{articleId}/topics` | return all topics associated with article given by `articleId` |
| `POST`   | `/articles/{articleId}/topics` | associate the topic with the article given by `articleId`. If topic does not already exist, it is created |
| `POST`   | `/topics` | create a new topic |
| `PUT`    | `/topics/{id}` | update the given topic |
| `DELETE` | `/topics/{id}` | delete the given topic |
| `DELETE` | `/articles/{articleId}/topics/{topicId}` | delete the association of a topic for the given article. The topic & article themselves remain |

## Files & Directories

Within the main folder, there is a resource directory containing the Spring application properties file, and a java
project directory with three packages: API, Model, and Repository, which contain the classes and interfaces required.
Within the API directory is a package containing exception.

```
.src/main
├── resources
└── devnews
    ├── api
    │   └── exception
    ├── model
    └── repository
```


