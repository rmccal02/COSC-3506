AlgomaU COSC 3506 - Final Project 
By: Robert Mccallum, Liam Stevens
Employment Application Review System’s Requirements and Specification
Introduction
Purpose
The purpose of the Employment Application Review System is to provide a software system which will enable users to review applicants for positions within the Department of Math and Computer Science in Algoma University.
Intended Audience
This system is intended to be used by members of Algoma University’s faculty members who may, or may not, have any experience with programming; it is expected that users have previous experience with course management systems like D2L Brightspace or Moodle.
Project Scope
The purpose of the Employment Application Review System is to provide a method to review applications to job positions in an easier manner than traditional methods. This system will have a database designed to store hundreds of applications to positions of the Departments of Math and Computer Science in Algoma University. Users of this system may also access and store information in this database. This system will be easier, faster, and more convenient compared to traditional methods of determining who to hire.

Description
Product Perspective
This system will allow users to store, view, and edit information of applications. This will include applicants’ profiles and assigned statuses along with all users’ comments and reviews. Additionally, administrators of the system will be able to add accounts with which users will be able to access the system with; users will be able to manage their own accounts.
Product Features
The major features of the Employment Application Review System will be demonstrated in the following use-case diagram.
Functional Requirements
Users will be able to perform 7 actions with this system. The first action a user must take before any series of actions is to login to the system; other than the administrator account we provide, any other user accounts must be added to the system by an administrator account. The remaining actions a user can perform after logging into the system is to:
Add profiles of applicants or faculty members
Profiles of applicants include users’ comments of applicants, statuses assigned by users to applicants, reviews by users of applicants, and information provided by the applicant to you.
Profiles of faculty members include their position within the departments, the dates of their employment and/or their unemployment, and any comments added by users.
View profiles of applicants or faculty members
Users are capable of viewing profiles of applicants or faculty members at any time so that users can properly collect information to make sound judgements on applicants.
Edit profiles of applicants or faculty members
If there were any mistakes made when adding a profile or if any profiles need to be updated, users are capable of editing these profiles at any time.
Search for profiles
This system will store a lot of information; a search function would speed up applicant reviewing. Users are capable of searching through profiles by matching user input to the several different categories of information stored by this system.
View user account information
Users will be able to view their own account information. Administrator users will be able to view all user account information.
Edit user account information
Users will be able to edit their own account information. Administrator users will be able to edit all user account information.
Add user accounts
Administrator users will be able to add user accounts to the system.
This system is a server-client system. This means that there is a server on one computer that client programs on multiple other computers communicate with. In this system, all information will be stored on one server computer which other client programs will communicate with when viewing profiles, adding profiles, etc.
This system has been designed to communicate across LAN and WAN types of networks.
Non-Functional Requirements
Due to the lightweight nature of this system’s workload, every action will take at most 5 seconds of time.
There will be no more than 2 seconds delay when users navigate this system’s interface.
This system will be able to hold hundreds of profiles and accounts in its database.
This system will periodically create copies of the database, making catastrophic failure less severe.
External Interface Requirements
User Interfaces
Front-end software: java
Back-end software: java
Software Interfaces
Operating System: Windows 10/11
Hardware Interfaces
Laptop computers or desktop computers. As a baseline for processing power, these computers should be able to run google chrome.
Communication Interfaces
This system will be using a Wide Area Network or Local Area Network for the server to communicate with users’ client programs. Network profiles on each computer connected to this system will need to be set to private for communication to be allowed.
