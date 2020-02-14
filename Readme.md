## This project is an example of push notifications using Spring Boot, Rest and JPA.

In this project i use OneSignal API [https://onesignal.com/].
You need to create an account on OneSignal to has a Rest API key
to manage your notifications.


This is Eclipse Maven Project. My DevTool is Spring Tools 4 for Eclipse. You can download from https://spring.io/tools
This project used JDK 8 and MariaDB 10.


All main servlet source is in src/main/java/com/valloon/pushnotification.
There are several controllers. APIController provides Rest API, ViewController provides Browsing History, PostController provides Sending questions, ReplyController provides answering, and so on.
And there are 2 models : NotificationHistory and Reply.
This project has 2 tables in DB, so each model is connected its table.
You can change Config.java to change DEVELOP MODE.


All frontend files are in src/main/webapp.
There are frontend libraries, css/javascript files and the jsp files.


You don't need to change the Other files.



Remember the API:
POST : /sendTo?id=...&time=(minutes)
POST : /result?message=Approved&id=...
POST : /result?message=Canceled&id=...

GET : /             Open the "Notification History" page.
GET : /post         Open the "Send a Question" page.
