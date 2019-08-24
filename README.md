# springsecurity-autologout
Java config and XML config Spring Security login and auto logout implementation

#### This repository has Spring project developed using 
1. JDK 8
2. org.springframework-version - 5.1.8.RELEASE
3. spring-security.version - 5.1.5.RELEASE
4. hibernate.version - 5.4.3.Final
5. apache-tiles-version - 3.0.5
6. logback.version - 1.2.3

#### These projects are mainly designed to use as a starter template which has following key features.
1. Spring mvc, Spring security with jdbc authentication and auto logout feature which displays timer when session is about to expire also it facilitates user to keep session alive while after session timeout timer is displayed in header. It will avoid losing filled form which will be lost if session expires before form is submitted.
2. Apaches tiles layout for defining application template and develop application with minimal effort.
3. Logback for logging.
4. i18n (Internationalization) configured project setup i.e, project can be localized for languages and cultures easily.
5. ThemeChangeInterceptor for customizing application theme in run time.

#### Key feature of this repository
1. This repository contains two project with same functionality implemented with XML and JAVA configuration. It makes easier to understand and learn by comparing XML and JAVA configuration.
2. Projects are developed with maven dependency and hence it will be easier to import and Run it.
3. If you are interested only in autologout functionality in spring security please refer A_OnlyAutologoutByUsingJavaconfig or A_OnlyAutologoutByUsingXmlconfig project for referance.

Or

If you are keen to see mvctiles integration and i18n implementation please refer xmlconfig or javaconfig projects.
   

Let's have look at application screenshot to understand features.
## 1. Locale configurations

![loginscreen](https://github.com/nlpraveennl/springsecurity-autologout/blob/master/z_screenshots/login-screen.png)
![loginscreen](https://github.com/nlpraveennl/springsecurity-autologout/blob/master/z_screenshots/locale_en.png)
![loginscreen](https://github.com/nlpraveennl/springsecurity-autologout/blob/master/z_screenshots/locale_hi_IN.png)

## 2. Theme configurations

![loginscreen](https://github.com/nlpraveennl/springsecurity-autologout/blob/master/z_screenshots/themes.png)

## 3. Auto logout configurations with session left timer

![loginscreen](https://github.com/nlpraveennl/springsecurity-autologout/blob/master/z_screenshots/session-left-timer-display.png)
![loginscreen](https://github.com/nlpraveennl/springsecurity-autologout/blob/master/z_screenshots/session-expired.png)

<b>The below answer is for people who is searching for solution if there  Requirement is</b><br>
1. Page should redirect to login page if that page has not made any request to server from X minutes. Logout should happen without any user intervention.<br>
2. Application should display timer when it is near to expire and gives provision to refresh or keep session alive. The screen shot which gives rough idea and is given below.<br>

[![enter image description here][1]][1]

# Autologout problem explained

Either it may be servlet or spring security or servlet it can only invalidate session and it can redirect to login page only after arriving next request.<br>
My requirement is if user is idle for time equal to ```maximumInactiveInterval ``` set, without user intervention page should redirect to login page.<br><br>
To implement auto logout functionality key requirement is<br>
<b>browser should come to know whether session expired in server or not</b><br>

To identify session has expired or not, a AJAX request(session check request) is required in background, it can get information of session status but problem is, if session is not expired sessionCheck request refreshes the current session. Because we need to check session active or not by
```
if(request.getSession(false) == null)
...
```
But the moment  ``` request.getSession(false) ``` 
is executed session will get refreshed and lastAccessTime of HttpSession will be updated.<br>

In General, in a servlet container, if there is a active session, the moment ``` request.getSession(false) ``` is executed then session will get refreshed regardless of spring or spring security or even in servlet and filters.
<b>So, we can't have page or url which avoids session refresh and tell us wheter session is expired or not</b>.

<b>Atlest, can i get ```lastAccessTime```</b><br>
To get lastAccessTime
```
HttpSession session = req.getSession(false);
if(session != null)
{
	session.getLastAccessedTime();
}
```
Again this also refreshes session.

<b> So how browser can get sessionTimeLeft value periodically?</b><br>
<br>
<p>Save your own lastAccessTime in session, This should be updated only for application requests not for sessionValidityCheck request</p>

Steps to achieve auto logout functionality

1. Save ```lastAccessTime``` in session attribute

2. Add two filters before spring security's filter```(DelegatingFilterProxy)```

   a. sessionTimeoutCheckFilter - checks lastAccessTime and sends response with sessionTimeLeft.  Filter this request (Stop request flowing further and send response, map filter for only one request /api/sessionCheck).
   
   b. sessionLastAccessTimeUpdateFilter - last access time will be updated for every request except /sessionCheck, /login and 
   
   if sessionTimeLeft becomes minimumValue show timer.
   
   if sessionTimeLeft becomes 0 or -ve send logout request.
   
   Here inter tab communication is done through getting updated sessionTimeLeft value and resetting timer in browser.
   
   If you are filling a large form and then if the session is about to expire timer will be displayed and you can send keepSessionAlive request to refresh the session(update lastAccessTime). So in this way you can avoid losing data.
### Now as a formality let me explain how to download and run application.
1. <b>Download zip file and extract it ;)</b>
![loginscreen](https://github.com/nlpraveennl/springsecurity-autologout/blob/master/z_screenshots/github-download.png)
2. <b>Import as Existing Maven Projects (After import project structure)</b>
![loginscreen](https://github.com/nlpraveennl/springsecurity-autologout/blob/master/z_screenshots/imported-project.png)
3. <b>Maven -> Update project (After update project structure)</b>
![loginscreen](https://github.com/nlpraveennl/springsecurity-autologout/blob/master/z_screenshots/updated-project.png)
4. <b>Run Maven build as clean install</b>

![loginscreen](https://github.com/nlpraveennl/springsecurity-autologout/blob/master/z_screenshots/run-as-maven-build.png)

5. #### Configuration
   Configuration for Project <b>A_OnlyAutologoutByUsingJavaconfig</b> and <b>A_OnlyAutologoutByUsingXmlconfig</b>
   
   For projects which uses in memory authentication nod database connection is required.
   
   Configuration for Project <b>javaconfig</b> and <b>xmlconfig</b>
   
   But for projects which uses jdbc authentication
   
   a. configure databaseconnection.properties in resources folder
   
   b. Execute scripts from resources\mysql folder in project(1st execute tables.sql and later insertDefaults.sql)
   
6. <b>Refresh project and clean server and Run server</b>

7. <b>Login credentials</b>

   Username : <b>praveen</b>
   
   Password : <b>praveen@123#</b>
   

Thank you. :)
