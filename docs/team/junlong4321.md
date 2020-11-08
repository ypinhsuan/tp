---
layout: page
title: Jun Long's Project Portfolio Page
---
### Overview - Project: Tutor's Pet
Tutor's Pet is a **student management application** for teaching assistants in NUS Computing.
It is a one stop solution to keep track of your classes, students, and their progress.

Tutor's Pet was developed in the module **CS2103T Software Engineering** in AY2020/2021 Semester 1
as part of the coursework requirements for all NUS Computer Science undergraduates.
My team comprised of 5 NUS Computer Science students. We developed Tutor's Pet from an existing Java application called
[Address Book (Level 3)](https://se-education.org/addressbook-level3/) over a period of six weeks.

You can find out more about Tutor's Pet functionalities
[here](https://ay2021s1-cs2103t-t10-4.github.io/tp/UserGuide.html). Given below are my contributions to Tutor's Pet.

### Code Contributions
You can find samples of code I have written
[here](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=junlong4321).

### Enhancements Implemented
* **Major Enhancements**
  * Introduced Student Universally Unique Identifier (UUID) into Student model.
  [#58](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/58)
    * In Tutor’s Pet, every `Student` is assigned a randomly generated `UUID` to ensure referential integrity of
      `Student` data across different models. Time was spent on `UUID` integration and fixing broken test cases.
  * Introduced ModuleClass to Storage. [#75](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/75)
    * Introduced the Java classes `JsonAdaptedUuid` and `JsonAdaptedModuleClass` into Storage so that we could
      allocate `Student`s to the respective tutorial classes that they were enrolled in. Time was spent integrating
      the two Java classes into Storage and writing test cases for these 2 classes.
  * Introduced `Add Lesson` and `Delete AttendanceRecord` commands.
  [#129](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/129)
  [#152](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/152)
    * These commands were required so that we could add lessons to tutorial classes and delete attendance records of
      students. It involved the creation of many new Java classes, and also a lot of test cases had to be written to
      thoroughly test these two commands.
  * Cascade Deletion of `Student`s. [#182](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/185)
    * Fixed a major bug where a `Student`'s data could still be found in `Lesson`s and `AttendanceRecord`s even after
      the `Student` had been deleted from the application. There was a need to refactor a large part of the deletion
      code.
* **Minor Enhancements**
  * Change Token Delimiter.
  [#167](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/167)
    * Fixed a bug by changing token delimiter so that the parsing of command was not rendered faulty when a URL
    was added to `Lesson` venue. Time had to be allocated to fix test cases. Existing test case commands `String`s
    had to be abstracted into variables to prevent test cases from breaking in the event that the delimiter was
    changed again.

### User Guide Contributions
* Refined documentation for `list-students` and `clear-class` commands.
* Added documentation for `add-lesson` and `delete attendance` commands.
* Consolidated and organised all commands in lexicographical ordering.

### Developer Guide Contributions
* Refined storage class diagram in Section 2.5 - Storage Component.
* Documented Section 3.1 - Student Model and Student Universally Unique Identifier (UUID) and
Section 3.2 - ModuleClass Model. These sections detail how the team implemented Student and ModuleClass models
with the use of Java UUIDs.
* Added use cases `find`, `clear`, `lesson`, `attendance`-related commands - UC04, UC06, UC10, UC12, UC17 to UC23.

### Contributions to Team-Based Tasks
* PRs reviewed (with non-trivial review comments):
[#53](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/53)
[#57](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/57)
[#63](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/63)
[#93](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/93)
[#118](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/118).
* A list of all PRs I have reviewed can be found
[here](https://github.com/AY2021S1-CS2103T-T10-4/tp/pulls?q=is%3Apr+is%3Aclosed+reviewed-by%3Ajunlong4321).

### Contributions Beyond the Team Project
* Assisted in module bug hunting in issue [#356](https://github.com/nus-cs2103-AY2021S1/forum/issues/356).
* Publicly clarified conceptual questions covered in the module topics in issues
[#162](https://github.com/nus-cs2103-AY2021S1/forum/issues/162)
and
[#179](https://github.com/nus-cs2103-AY2021S1/forum/issues/179).
* Assisted the team [TaskMania](https://github.com/AY2021S1-CS2103T-W10-3/tp/) in testing and reporting
bugs found in their product [here](https://github.com/junlong4321/ped/issues).
