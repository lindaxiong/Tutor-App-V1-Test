# TeachMe

TeachMe is to tutoring like Uber is for ride services. TeachMe is an android app that allows users to find and communicate with tutors. It will help tutees who want to get a little head start lectures, need a study plan to cram for next week’s Psychology exam, want to learn some basic C++ programming, help on a Chemistry homework problem, etc. TeachMe allow tutee to request tutoring appointments with tutor, send pictures and descriptions of what the tutee needs help on, and choose tutors from a list of tutors on the app. TeachMe will solve the problem for users who just want a quick tutoring session, in any format that the tutor and tutee agree on, rather than paying full tuition on a regular basis for formal academic setting or in expensive tutoring centers such as Kumon and C2 Education. TeachMe can also be a way for tutee to skip the long line at professor office hours. 

## TeachMe allows:
•	Tutee to send pictures and descriptions of the problem to their tutor.
•	Tutee to create appointments with their tutor.
•	Tutees and tutors to communicate with each other regarding problems the tutee want help on or information regarding future tutoring sessions.
•	Tutor to create a checklist of study steps that the tutee can mark off as being completed after completing that study task.


## Platform Justification - What are the benefits to the platform you chose?
We chose to write an Android application because, While Apple focuses on the premium smartphone market and customers with higher income, Android is allowing massive adoption. For instance, Android has a large advantage compared to Apple in emerging markets (i.e. Asia, Africa, South America), and these emerging markets are exactly the place where a tutoring app could help the community members. 

## Major Features/Screens - Include short descriptions of each (at least 3 of these)
1.	Login Screen: When the app loads, the user is prompted to login via his/her Google account. This calls the Google Sign In API client, which authenticates the user and retrieves the API key that is used to call the Places API.
2.	Profile and Logout Screen: On this screen, the user can view his/her current tutor or logout of the app. 
3.	Main Screen: Upon entering the app, shows a list of tutors based on the user’s location. There is also a calendar. In the list of tutors, each tutor information contains the tutor’s phone number. The tutee can click on the number to make a phone call. 
4.	Photo Screen: In the action bar, the user can choose between the Profile and the Camera activities. Photo Screen allows the user to take a picture of his/her problem and send it to the tutor via email or social media.


## Optional Features - Include specific directions on how to test/demo each feature and declare the exact set that adds up to ~60 pts
1.	(15) GPS/Location awareness - Tutee can find tutors nearby
o	How to test/demo: The tutor’s physical location is near the user. 
2.	(15) Camera: The user can take a picture of his/her problem and send it to the tutor. 
o	How to test/demo: Click on the tab, take a picture, and select a tutor to send the picture to. 
3.	(10) Consume a pre-built web service (Google Sign-in and Google Place API): The user can log in using the Google API and get locations using the Google API.
o	How to test/demo: can log in using Google account. Can retrieve locations
4.	(10) Data storage using SQLite (Android): Tuteeutee can select a primary tutor from a list of tutors. This primary tutor data will be saved upon selection and will load each time the tutee opens the app. 
o	How to test/demo: Select the primary tutor. The selected tutor should load when the app reopens.
5.	(10) Open shared activity / features - Tutee can send pictures and description of the problem to the tutor through emails. 
o	How to test/demo: Using the camera tab, send an email with a picture.

## April 16 Updates
### Major Feature
1. Login Screen: When the app loads, the user is prompted to login via his/her Google account. 
2. Logout Screen: User can logout by clicking the button on the main screen.

### Optional Features
1. Camera: The user can take a picture of his/her problem and send it to the tutor. 
2. Device Shake: Shake device at the main screen would show a dialog.
3. Third-party platform (Firebase): Use Google account to log in.
