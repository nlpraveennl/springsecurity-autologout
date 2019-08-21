# springsecurity-autologout
Java config and XML config Spring Security login and auto logout implementation

This repository has Spring project developed using 
1. JDK 8
2. org.springframework-version - 5.1.8.RELEASE
3. spring-security.version - 5.1.5.RELEASE
4. hibernate.version - 5.4.3.Final
5. apache-tiles-version - 3.0.5
6. logback.version - 1.2.3

This project is mainly designed to use as a template which has following key features.
1. Spring mvc, Spring security with jdbc authentication and auto logout feature which displays timer when session is about to expire also it facilitates user to keep session alive while after session timeout timer is displayed in header. It will avoid losing filled form which will be lost if session expires before form is submitted.
2. Apaches tiles layout for defining application template and develop application with minimal effort.
3. Logback for logging.
4. i18n (Internationalization) configured project setup i.e, project can be localized for languages and cultures easily.
5. ThemeChangeInterceptor for customizing application theme in run time.

Key feature of this repository
1. This repository contains two project with same functionality implemented with XML and JAVA configuration. It makes easier to understand and learn by comparing XML and JAVA configuration.
2. Projects are developed with maven dependency and hence it will be easier to import and Run it.

Let's have look at application screenshot to understand features.
1. Locale configuration
