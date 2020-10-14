# Messaging Service Application
With this application, user can login or create new account. Users can create an account and login in the system. As long as users know each other's username, they can message. Users can access their historical messaging.  A user can block the other user who does not want to receive messages.

## Register User

### POST Request
**URL:**
``` http://localhost:8080/user/register```
```json
{
      "username": "burcinay",
      "email": "burcinaydogmus@mail.com",
      "password": "123456"
}
```

### Response
```json
{
    "status": "SUCCESS"
}
```
## Login User

### POST Request
**URL:**
``` http://localhost:8080/user/login```
```json
{
       "username": "burcinay",
       "password": "123456"
}
```

### Response
```json
{
    "status": "SUCCESS"
}
```

## Block User

### POST Request
**URL:**
``` http://localhost:8080/user/block```
```json
{
        "username": "burcinay"
        "blockedUserId": "1"
}
```

### Response
```json
{
    "status": "SUCCESS"
}
```

## Send Message

### POST Request
**URL:**
``` http://localhost:8080/send```
```json
{
        "message": "Hello",
        "user":{
        	    "username": "burcinay",
        	    "password": "123456",
                "email": "burcinaydogmus@mail.com"
            }
        "receiverName": "joe"
}
```

## Retrieve Messages

### GET Request
**URL:**
``` http://localhost:8080/messages/{id}```
```json
{
        "id" : "1"
}
```

### Response
```json
{
    "messageDtoList": [
        {
            "message": "Hello"
        },
        {
            "message": "How are you?"
        }
    ]
}
```
