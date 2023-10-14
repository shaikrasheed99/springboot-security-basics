# Spring Security Basics application

## Gradle based spring boot application which provide below APIs of the users using test driven development.

## APIs of the Application

    - Signup
    - Authenticated
    - Get all users
    - Get user by username

## APIs

### Signup - `/signup`

* `NOTE` - this API can be accessed by everyone.

* Request

```
curl --location --request POST 'http://localhost:8080/signup' \
--header 'Content-Type: application/json' \
--data '{
    "username": "test_username",
    "password": "test_password",
    "role": "ROLE_user",
    "firstname": "test_firstname",
    "lastname": "test_lastname"
}'
```

* Response

```
{
    "code": "CREATED",
    "status": "success",
    "data": {
        "message": "user successfully signed up"
    }
}
```

### Authenticated - `/authenticated`

* `NOTE` - this API can be accessed by only authenticated users

* Request

```
curl --location --request GET 'http://localhost:8080/authenticated' \
--header 'Authorization: Basic Y2FwdGFpbjpwYXNzd29yZA=='
```

* Response

```
{
    "code": "OK",
    "status": "success",
    "data": {
        "message": "This API is accessed by only authenticated users"
    }
}
```

### Get all users - `/users`

* `NOTE` - this API can be accessed by authenticated users who are having admin role.

* Request

```
curl --location --request GET 'http://localhost:8080/users' \
--header 'Authorization: Basic aXJvbm1hbjpwYXNzd29yZA=='
```

* Response

```
{
    "code": "OK",
    "status": "success",
    "data": [
        {
            "username": "test_username",
            "role": "ROLE_user",
            "firstname": "test_firstname",
            "lastname": "test_lastname"
        }
    ]
}
```

### Get User by username - `/users/{username}`

* `NOTE` - this API can be accessed by authenticated users who are having admin and user roles.

* Request

```
curl --location --request GET 'http://localhost:8080/users/test_username' \
--header 'Authorization: Basic dGhvcjpwYXNzd29yZA=='
```

* Response

```
{
    "code": "OK",
    "status": "success",
    "data": {
        "username": "test_username",
        "role": "ROLE_user",
        "firstname": "test_firstname",
        "lastname": "test_lastname"
    }
}
```