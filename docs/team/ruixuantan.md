---
layout: page
title: Rui Xuan's Project Portfolio Page
---

## Overview
Tutor's Pet is a **desktop application for managing students and classes**, designed for teaching assistants in NUS
School of Computing.
Tutor's Pet is adapted from an existing desktop Java application
[Address Book (Level 3)](https://se-education.org/addressbook-level3/).
My team, consisting of 5 NUS Computer Science students, developed this application over the course of 6 weeks.

## Summary of Contributions
Here is a summary of my personal contributions to the project.
My code contribution can be found [here](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=ruixuantan).

## Major Enhancements
* Implement Attendance model. [#118](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/118)
  * **What it does**: Supports the tracking of student's attendance and participation points in class.
  * **Highlights**: There were choices to make regarding the data structures that were implemented.
    Consideration was given to:
      * the time complexity of operations in the data structures,
      * how the attendance records are to be retrieved and updated.
      
* Add `edit-lesson` command. [#143](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/143)
    * **What it does**: Allows the editing of lessons.
    * **Highlights**: There are multiple fields that can be edited in the lesson model.
      There are also fields that should not be edited, and effort was made to ensure that these fields are copied properly.
      
* Implement the toggle theme feature. [#109](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/109)
    * **What it does**: Allows users to switch between light, alternate and dark mode.
    * **Highlights**: There was a need to understand the css stylesheets to abstract out the common components and differentiate the colors between themes. 
      Analysis of the color choices was also required to design aesthetically pleasing themes.
    * **Extension**: I made a separate PR to ensure that the themes are saved into the `preferences.json` file upon exit of the application.
      [#195](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/195)
      
## Minor Enhancements
* Remove initial address field. [#20](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/20)
* Refactor phone to telegram field. [#53](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/53)
* Add lesson storage support. [#127](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/127)
* Remove code duplication in test cases. [#190](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/190)
* Fix tag color bug and improve testability. [#253](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/253)

## Other contributions
* Create application logo. [#220](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/220)
* Record video demonstration for v1.2.

* User Guide
  * Update sections on all student related commands.
  * Add section on `edit-lesson` command.

* Developer Guide
  * Update diagrams in Logic Component section.
  * Add implementation details for Attendance Model.
  * Add table of user stories.
  * Include use cases: UC03, UC05, UC12.
  
* PRs reviewed (with non trivial comments):
  [#75](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/75),
  [#95](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/95),
  [#112](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/112),
  [#152](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/152),
  [#184](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/184).

* A list of all PRs I have reviewed can be found [here](https://github.com/AY2021S1-CS2103T-T10-4/tp/pulls?q=is%3Apr+reviewed-by%3Aruixuantan)
