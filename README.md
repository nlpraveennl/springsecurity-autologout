# springsecurity-autologout
Java config and XML config Spring Security login and auto logout implementation

#### This repository has Spring project developed using 
1. JDK 8
2. org.springframework-version - 5.1.8.RELEASE
3. spring-security.version - 5.1.5.RELEASE
4. hibernate.version - 5.4.3.Final
5. apache-tiles-version - 3.0.5
6. logback.version - 1.2.3

#### This project is mainly designed to use as a template which has following key features.
1. Spring mvc, Spring security with jdbc authentication and auto logout feature which displays timer when session is about to expire also it facilitates user to keep session alive while after session timeout timer is displayed in header. It will avoid losing filled form which will be lost if session expires before form is submitted.
2. Apaches tiles layout for defining application template and develop application with minimal effort.
3. Logback for logging.
4. i18n (Internationalization) configured project setup i.e, project can be localized for languages and cultures easily.
5. ThemeChangeInterceptor for customizing application theme in run time.

#### Key feature of this repository
1. This repository contains two project with same functionality implemented with XML and JAVA configuration. It makes easier to understand and learn by comparing XML and JAVA configuration.
2. Projects are developed with maven dependency and hence it will be easier to import and Run it.

Let's have look at application screenshot to understand features.
## 1. Locale configurations

![loginscreen](https://github.com/nlpraveennl/springsecurity-autologout/blob/master/screenshots/login-screen.png)
![loginscreen](https://github.com/nlpraveennl/springsecurity-autologout/blob/master/screenshots/locale_en.png)
![loginscreen](https://github.com/nlpraveennl/springsecurity-autologout/blob/master/screenshots/locale_hi_IN.png)

## 2. Theme configurations

![loginscreen](https://github.com/nlpraveennl/springsecurity-autologout/blob/master/screenshots/themes.png)

## 3. Auto logout configurations with session left timer

![loginscreen](https://github.com/nlpraveennl/springsecurity-autologout/blob/master/screenshots/session-left-timer-display.png)
![loginscreen](https://github.com/nlpraveennl/springsecurity-autologout/blob/master/screenshots/session-expired.png)

# Autologout functionality explained
Either it may be servlet or spring security it can only invalidate session and it can redirect only after arriving next request.
My requirement is if user is idle for time equal to maximumInactiveInterval set, without user intervention page should redirect to login page.
To implement auto logout functionality key requirement is 
### browser should come to know wether session expired in server or not. 
To identify session has expired or not a AJAX request(sessionCheck req) is required in background, it can get information of session status but problem is, if session is not expired sessionCheck request refreshes the current session(lastAccessTime of HttpSession is set).
For example
If sesion timeout = 10 Minutes and session check interval is 2 Mintes, And if user is idle for two minutes session check request will be fired and it refreshes session and even though user is idle for any time session will never expire. We can not calculate how much time left for session invalidation.

### Is there any usual way in spring, spring security to prevent session getting refreshed
If there is active session for each request it will get refreshes regardless of spring or spring security. If we think about servlet and filters, the answer is when request reaches servlet container if request has active session it will get refreshed without fail. It is tomcat default behavior in other words we can say session's default behavior.
So, we can't have page or url which avoids session refresh.

### Can i modify the lastAccessTime value of HttpSession object?
No. If it is possible to set lastAccessTime, for sessionCheck request we can avoid lastAccessTime being modified and can be set to its previous value. Which may mean session is not refreshed for sessionCheck request.
But it is not supported.

### May i delete old cookie and set a new one.
Overhead, it will be custom session management. And may be spring security will not work for such changes.

So we can not avoid session refresh, then how we can intimate browser about session expiry.
### Approach1. UI(Javascript) logic
Until user is alive keep session alive by heart beat(AJAX request), 
set timer of timeout equal to maximumInactiveInterval

a. if user activity(mouse move/click/doubleClick) is detected send heart beat and reset timer.

b. if timer expires kill session (i.e, make logout by location.href = "/logout")

Good idea, But it is one sided check(Browser sided control)
timeLeft in server timeLeft in browser may have difference over a large time of 30 minutes.(If system goes sleep or network failures for some time or server goes down for some time)
Need shared object coding such as localStorage to make it working for more than one tab.(Multitab access)

### Approach2. Save lastAccessTime in session attribute and calculate sesionTimeLeft and intimate browser periodically. so browser updates timer even though two tabs are open.
Steps to achieve auto logout functionality
1. Save lastAccessTime in session attribute
2. Add two filters before spring security's filter(DelegatingFilterProxy)

   a. sessionTimeoutCheckFilter - checks lastAccessTime and sends response with sessionTimeLeft.  Filter this request (Stop request flowing further and send response, map filter for only one request /api/sessionCheck).
   
   b. sessionLastAccessTimeUpdateFilter - last access time will be updated for every request except /sessionCheck, /login and 
   
   if sessionTimeLeft becomes minimumValue show timer.
   
   if sessionTimeLeft becomes 0 or -ve send logout request.
   
   Here inter tab communication is done through getting updated sessionTimeLeft value and resetting timer in browser.
   
   If you are filling a large form and then if the session is about to expire timer will be displayed and you can send keepSessionAlive request to refresh the session(update lastAccessTime). So in this way you can avoid losing data.
   