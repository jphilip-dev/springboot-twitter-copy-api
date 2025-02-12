# Twitter API - Spring Boot Project

This is a REST api designed using Spring Boot to mimic basic funtionalities of Twitter ( now X ).

## Features
  - **User Registration and Login**: Users needs register and Login.
  - **JWT Authentication**: Most of the endpoints are secured and requires JWT which can be obtained throgh login endpoint.
  - **Encryption**: Users password are encrypted using BCrypt.
  - **Tweets Management**: Users are able to create, like, comment, and share tweets
  - **Pagination**: To optimize server performance.
  - **Exception Handling**: Custom exceptions handler for various errors, including validation and tweet-related issues.

## End Points
### Authentication
  - ***POST /api/auth/login*** : Returns the JWT if the credential is valid
    - Request Body
     ``` json
    {
      "username":"john123",
      "password":"123456"
    }
    ```
    - Response ( Valid )
    ``` json
    {
      "token":"eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJUd2l0dGVyQ29weSIsInN1YiI6ImpvaG4xMjMiLCJpYXQiOjE3MzkzNDY2MTYsImV4cCI6MTczOTM0ODQxNn0.eNhz0t_8Zla5CzhjUMvKkz_BYS2HVAnRmAR4oljPt1fGkazv0S23NzFVEDv6jGIc8vNl1touA2c7BgrjrT2K5Q"
    }
    ```
  - ***POST /api/auth/register*** : Returns **Created status** or field errors
    - Request Body
     ``` json
    {
      "username":"jane123",
      "password":"123456",
      "lastName":"Doe",
      "firstName":"Jane"
    }
    ```
    - Response ( Field Error )
    ``` json
    {
      "errors": {
          "username": "Username alreadt exists."
      }
    }
    ```
### Tweets
  - ***GET /api/tweets/my-tweets*** : Returns all the created tweets of the logged-in User.
  - ***GET /api/tweets/following-tweets*** : Returns all the tweets of Users that is followed by the logged-in User.
  - ***GET /api/tweets/{tweetId}*** : Returns the tweet detail of the Tweet id.
  - ***POST /api/tweets*** : Create a new Tweet.
  - ***DELETE /api/tweets/{tweetId}*** : Delete the Tweet by ID.
  - ***PUT /api/tweets/{tweetId}*** : Update the Tweet by ID. ( in progress )
  - ***PUT /api/tweets/{tweetId}/likes*** : Like the tweet using ID by the logged-in User.
  - ***PUT /api/tweets/{tweetId}/shares*** : Share the tweet using ID by the logged-in User. ( in progress )
  - ***PUT /api/tweets/{tweetId}/comments*** : Comment on the Tweet using the ID by the logged-in User. ( in progress )
  - ***GET /api/tweets/{tweetId}/likes*** : Get the List of Users who liked the Tweet.
  - ***GET /api/tweets/{tweetId}/shares*** : Get the List of Users who Shared the Tweet.
  - ***GET /api/tweets/{tweetId}/comments*** : Get the List of Users and comments who commented on the Tweet.


## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
