---
layout: page
title: Yen Pin Hsuan's Project Portfolio Page
---

## Project: Tutor's Pet

Tutor’s Pet is a desktop application for managing students and classes, designed for teaching assistants in NUS School of Computing.
Tutor's Pet was adapted from an existing desktop Java application [Address Book (Level 3)](https://se-education.org/addressbook-level3/).
My team, comprising 5 NUS Computer Science undergraduates, developed the application over the course of 6 weeks.

The following sections document the contributions that I have made to Tutor's Pet.

## Summary of Contributions
Here is a summary of my personal contributions to the team project. All my code contributions can be found
[here](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=ypinhsuan).

### Major Enhancements:
* Implement `Lesson` model. [#112](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/112)
  * **What it does:** Supports organising lessons into classes.
  * **Justification:** This feature allows us to implement commands for lessons.
  * **Highlights:** This feature required addition of a new component to the model of the application. There were 2 designs considered:
    * Store `Lesson` objects directly in `ModueClass`.
    * Create `UUID` field for `Lesson` and store uuid in `ModuleClass`.
* `edit-attendance` command feature. [#157](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/157)
  * **What it does:** This feature allows users to edit the attendance of a student.
  * **Justification:** Users do not have to delete the attendance and re-add a new one if they made any errors when inputting attendance.
  * **Highlights:** Implementation of this feature was complicated as retrieving `attendance` in the `edit-attendance` command will break the law of demeter.
  Hence the utility methods for editing attendance `Attendance` are in `ModuleClassUtil`, `LessonUtil` and `AttendanceRecordListUtil`.
  This is also to avoid handling logic in `Model` component.

### Minor Enhancements:
* Renamed `AddressBook` and `Person` to `Tutorspet` and `Student` respectively. [#55](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/55)
* Updated student-related commands and parsers. [#56](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/56)
* Updated `list` command to list all students and classes. [#93](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/93)
* Added a `reset` command that allow users to clear all data in Tutor's Pets [#106](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/106)
* Fixed the bug that start time of lesson can be later than end time. [#173](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/173)
* Designed UI for `Lesson`. [#194](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/194)
* Fixed lesson timing overlap bug. [#259](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/259)

### Other Contributions

* Project Management:
  * Managed release [v1.3.trial](https://github.com/AY2021S1-CS2103T-T10-4/tp/releases/tag/v1.3.trial) on GitHub.
  * Record video demonstration for v1.3.

* Documentation:
  * User Guide
    * Added sections on `help`, `exit`, `reset`, `list`, `edit-attendance` commands.
  * Developer Guide
    * Added the following use cases: `Add a student (UC01)`, `List students (UC02)`, `List students within a class (UC09)` and `List students and classes (UC14)`.
    * Added implementation details for `Lesson` model.
    * Updated documentation and diagram for Architecture and Commons section.

* Team-Based Tasks & Community:
  * PRs reviewed (with non-trivial review comments):
    [\#35](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/35)
    [\#82](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/82)
    [\#110](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/110)
    [\#129](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/129)
    [\#190](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/190)
    [\#272](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/272)

  * A list of all PRs I have reviewed can be found
    [here](https://github.com/AY2021S1-CS2103T-T10-4/tp/pulls?q=is%3Apr+is%3Aclosed+reviewed-by%3Aypinhsuan).
