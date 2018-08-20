# Pager Challenge

The main features of the application are completed and tested, the use of websockets & API call are
completed, you must implement your own UI and implement the Update Status feature with the same structure.

The main goal of the test is to see the skills of next areas:

- UI Implementation
- Clean code
- Architecture
- Completeness
- Design patterns
- Use of Rx
-Please create a list of the things you did not liked and can be improved, we will be very happy to know your feedback!

### Required features

- App must connect to an API server and fetch data from the following endpoints:
	`https://pager-team.herokuapp.com/team`
	`https://pager-team.herokuapp.com/roles`

- Have a "detail" view when tapping on an item of the list.

- Have a way to send a status update for a particular user. That status update should be sent through the socket even if it will not persist and can potentially fail.

- Unit tests: No need to go grazy here, tests are important, having 100% coverage not always is.

- Display updated data through a socket server at: `ws://104.236.4.78:8080`

1) Full users or updated users
```
{
        "event": "user_new",
        "user": {
            "name": "Emiliano Viscarra",
            "avatar": "https://www.dropbox.com/s/p1qr5zqnjy4du03/emi.png?dl=1",
            "github": "chompas",
            "role": 1,
            "gender": "Male",
            "languages": ["en", "es"],
            "tags": ["Objective-C", "Management"],
            "location": "us"
        }
    }
 ```

 2) Status updates

 ```
 {"event":"state_change","user":"marianoquevedo","state":"Reviewing PRs 👀"}
 ```

### UX/UI

- As a guide, here's a [screenshot](https://www.dropbox.com/s/f77zgc3bqevp44n/Screenshot%202017-05-30%2011.43.27.png?dl=0) of the iOS version of the app and a [video](https://www.dropbox.com/s/t7nxmb7rp7a4msj/exercise_ios.mov?dl=0) of the app working. UI is really up to you.

- Do you think you can improve it? Any extra is more than welcome :-)


### Submitting

- Your code goes into a public Github repository.

- APK can be delivered in any way that let us install the app in a device.

Have any doubts about your assignment? Please feel free to contact Emiliano (emiliano@pager.com).

Happy coding!

![wow](http://i3.kym-cdn.com/photos/images/newsfeed/000/582/577/9bf.jpg)