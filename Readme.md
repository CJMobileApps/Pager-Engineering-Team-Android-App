# Pager Engineering Team

This is an app that displays Pager.com engineering team. Each individual team member is displayed in a list. The list can either be periodically updated with a new member or a current team member status changes.

Time spent: approximately one week


## User Stories / Feature List

The following functionality is completed:

* [X] Create readme
* [X] User story 1: Display team members in a list
      * [X] Part 1: Make call to following end point: https://pager-team.herokuapp.com/team

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


      * [X] Part 2: Make call to following end point: https://pager-team.herokuapp.com/roles

        2) Roles
              ```
              {
                "1" : "Engineering Manager",
                "2" : "iOS Engineering",
                "3" : "Senior Software Engineer",
                "4" : "JS Engineering",
                "5" : "Backend Engineering",
                "6" : "Machine Learning Engineering",
                "99" : "Autogenerated"
              }
       ```


      * [X] Part 3: Make call to following end point: http://ios-hiring-backend.dokku.canillitapp.com

      3) Status updates

       ```
       {"event":"state_change","user":"marianoquevedo","state":"Reviewing PRs 👀"}

       ```

      * [X] Part 4: Have a hide and show progress dialog
      * [X] Part 5: Display information in Recycler View

Features to be implemented in the future:

* [ ] Add unit test

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://imgur.com/a/WEELQGu' title='Video Walkthrough' width='' alt='Video Walkthrough' />
