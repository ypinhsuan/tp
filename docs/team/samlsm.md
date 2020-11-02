---
layout: page
title: Samantha Low's Project Portfolio Page
---

## Project: Tutor's Pet

## 1. Overview
Tutor's Pet is a **desktop application for managing students and classes**, designed for teaching assistants in NUS
School of Computing. Tutor's Pet aims to reduce the amount of time tutors spend on administrative tasks and it is
a one stop solution for users to keep track of all their classes, students, and their student's progress.

Tutor's Pet is morphed from an existing desktop Java application
[Address Book (Level 3)](https://se-education.org/addressbook-level3/) and several enhancements was made to it by my
team of 5 NUS Computer Science students over a period of 6 weeks.

The following sections document all the contributions that I have made to Tutor's Pet.

Given below are my contributions to the project.


## 2. Summary of Contributions
Here is a summary of my personal contributions to the team project. All my code contributions can be found
[here](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=samlsm).

### 2.1. Major Enhancements:
* Designed a new UI for tutors pet. [#108](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/108)
  * What it does: Display all information that a user would require in a clear and concise format.
  * Justification: The change in design was essential for Tutor's Pet because the original address book design was
    unable to meet the needs of Tutor's Pet. With the new design, users are able to see the classes and students they
    have immediately. With certain commands, they would also be able to filter the student list and see only students
    in a specific class.
  * Highlights: Designing the UI for Tutor's Pet was challenging because there was a lot of information we wanted to
    present in that small window without the UI looking too cluttered. Hence, it was necessary to do a deeper
    analysis to prioritise the importance of different information before deciding and coming up with a design.

* Introduced `stats` command. [#184](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/184)
  * What it does: This feature allows the user to see the overall performance of a student in a class. It computes the
    student's average participation score and attendance for the entire semester.
  * Justification: This feature is important because it allows users to see the overall performance of a student
    without the hassle of having to compute these statistics themselves. It satisfies one of Tutor's Pet goal which
    is to help tutors track a student's progress over time.
  * Highlights: Implementation of this enhancement was challenging because the direct implementation of it would
    break the law of demeter. Hence a more in-depth analysis of its implementations was necessary.

### 2.2. Minor Enhancements:
* Added a `edit-class` command. [#63](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/63)
  * This command allows users to edit the details of a class.

* Added a `find-class` command. [#92](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/92)
  * This command allows users to find classes which contains the specified keyword(s).

* Changed help window popup. [#94](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/94)
  * Address a bug that results in help window not appearing when minimised.

* Added a `delete-lesson` command. [#124](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/124)
  * This command allows users to delete a lesson and all its relevant data.

* Fix UI scaling. [#126](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/126)
  * Address a bug that results in UI not scailing equally when the window is being resized.

* Added a `add-attendance` command. [#142](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/142)
  * This command allows users to record the attendance of a specific student for a specific week's lesson.

* Added a `find-attendance` command. [#155](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/155)
  * This command allows users to search for a student's attendance in a specific week's lesson.

* Renamed base package. [#165](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/165)
  * Renamed the base package from `seedu.address` to `tutorspet`.

* Added a `display-venue` command. [#166](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/166)
  * This command allows users to obtain the venue of a specific lesson from the command result box. It was
    implemented because we notice that users are unable to copy venues directly from the displayed class list.


## 3. User Guide Contributions
Given below are sections I contributed to the User Guide. It illustrates my ability to write documentations
targeting end-users.

* Standardised all the screenshots.

* Added documentations for `edit-class`, `find-class`, `clear-class`, `delete-lesson`, `add-attendance`,
`find-attendance`, `display-venue` and `stats` commands.

* Refined documentations in **Section 3 Quick Start**.

## 4. Developer Guide Contributions
Given below are sections I contributed to the Developer Guide. It illustrates my ability to write technical
documentations and the technical depth of my contributions to the project.

* Refined UI class diagram in **Section 2.2.UI component**.

* Added documentations for the display statistics feature in **Section 5**. This section explains the implementation
  details of the `stats` command and design considerations taken into account when implementing it.

* Added documentations for the add attendance feature in **Section 6**. This section explains the implementation
  details of the `add-attendance` command and design considerations taken into account when implementing it.

* Added use cases `UC010` and `UC15`.

* Added instructions for manual testing of in **Section 6**.
  * This includes the manual testing of `delete-student`, `edit-class`, `find-class`, `clear-class`, `delete-lesson`,
    `display-venue`, `add-attendance`, `find-attendance` and `stats`.


## 5. Contributions to Team-Based Tasks
* A list of all PRs I have reviewed can be found
  [here](https://github.com/AY2021S1-CS2103T-T10-4/tp/pulls?q=is%3Apr+is%3Aclosed+reviewed-by%3Asamlsm).

* Managed release `v1.3` on GitHub.
