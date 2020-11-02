---
layout: page
title: Jun Long's Project Portfolio Page (PPP)
---

## Project: Tutor's Pet


## 1. Overview
Tutor's Pet is a **student management application** for teaching assistants in NUS Computing.
Tutor's Pet reduces the amount of time you spend on administrative tasks as a teaching assistant.
It is a one stop solution to keep track of your classes, students, and their progress.

Tutor's Pet was developed in the module **CS2103T Software Engineering** in AY2020/2021 Semester 1
as part of the coursework requirements for all NUS Computer Science undergraduates.
My team comprised of 5 NUS Computer Science students. We developed Tutor's Pet from an existing Java application called
[Address Book (Level 3)](https://se-education.org/addressbook-level3/) over a period of six weeks.

You can find out more about Tutor's Pet functionalities
[here](https://ay2021s1-cs2103t-t10-4.github.io/tp/UserGuide.html).

Given below are my contributions to Tutor's Pet.


## 2. Code Contributions
You can find samples of code I have written
[here](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=junlong4321).


## 3. Enhancements Implemented
This section documents both major and minor enhancements I have made to Tutor's Pet.
* **Major Enhancements**
  * Introduced Student Universally Unique Identifier (UUID) into Student model.
  [#58](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/58)
    * A `UUID` is a unique 128-bit number to identify a unique `Student` within Tutor’s Pet.
      In Tutor’s Pet, every `Student` upon construction is assigned a randomly generated `UUID` that is used to
      uniquely identify a `Student` across `Classes` and `Lessons`. This is important because we are dealing with
      `Student` data not just in the `Student` model itself, but also `Classes` and `Lessons`. Using a
      `Student` `UUID` will help to ensure referential integrity of `Student` data across different models when
      `Student` data is modified by the user. It is challenging to introduce a `UUID` fields as it breaks many
      existing test cases. Time and effort has to be allocated to fix these test cases.
  * Introduced ModuleClass to Storage. [#75](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/75)
    * Introduced the Java classes `JsonAdaptedUuid` and `JsonAdaptedModuleClass` into Storage. The two Java classes are
      required in Tutor's Pet so that we can allocate `Student`s to the respective tutorial classes that they are
      enrolled in. Integration of these 2 Java classes into Storage and writing test cases for them took quite
      some time.
  * Introduced `Add Lesson` and `Delete AttendanceRecord` commands.
  [#129](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/129)
  [#152](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/152)
    * These commands are required so that we can add lessons to tutorial classes and delete attendance records of
      students. It is a big enhancement as it involves the creation of many new Java classes, and because
      a lot of test cases have to be written to thoroughly test these two commands.
  * Cascade Deletion of `Student`s. [#182](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/185)
    * Fixed a major bug where a `Student`'s data can still be found in `Lesson`s and `AttendanceRecord`s even after
      the `Student` has been deleted from the application. There was a need to refactor a large part of the deletion
      code to introduce the behaviour.
* **Minor Enhancements**
  * Change Token Delimiter.
  [#167](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/167)
    * Fixed a minor bug by changing token delimiter from backward to forward slash so that parsing of
    command is not rendered faulty when a URL is added to `Lesson` venue. Simple to fix actual code but troublesome
    to fix test cases as we had to extract all the test case command strings out into variables to prevent test cases
    from breaking again in the event that we change the token delimiter again.


## 4. User Guide Contributions
* Reformatted User Guide and tailored it to Tutor's Pet Project
(Changed addressbook address entries to students, /t to /tag, Changed tag description, Removed AB3 address,
Add cover page).

* Refined documentation for `list-students` and `clear-class` commands.

* Added documentation for `add-lesson` and `delete attendance` commands.

* Consolidated and organised all commands in lexicographical order.


## 5. Developer Guide Contributions
* Refined storage class diagram in Section 2.5 - Storage Component.

* Documented Section 3.1 - Student Model and Student Universally Unique Identifier (UUID) and
Section 3.2 - ModuleClass Model. These sections detail how the team implemented Student and ModuleClass models
with the use of Java UUIDs.

* Added use cases `find`, `clear`, `lesson`, `attendance`-related commands - UC04, UC06, UC10, UC12, UC17 to UC23.

* Consolidated and organised all use cases and user stories.


## 6. Contributions to Team-Based Tasks
* PRs reviewed (with non-trivial review comments):
[#53](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/53)
[#57](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/57)
[#63](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/63)
[#93](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/93)
[#118](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/118).

* A list of all PRs I have reviewed can be found
[here](https://github.com/AY2021S1-CS2103T-T10-4/tp/pulls?q=is%3Apr+is%3Aclosed+reviewed-by%3Ajunlong4321).


## 7. Contributions Beyond the Team Project
This section showcases my contributions to CS2103T beyond the team project.

* Assisted in module bug hunting in issue [#356](https://github.com/nus-cs2103-AY2021S1/forum/issues/356).

* Publicly clarified conceptual questions covered in the module topics in issues
[#162](https://github.com/nus-cs2103-AY2021S1/forum/issues/162)
and
[#179](https://github.com/nus-cs2103-AY2021S1/forum/issues/179).

* Assisted the team [TaskMania](https://github.com/AY2021S1-CS2103T-W10-3/tp/) in testing and reporting
bugs found in their product [here](https://github.com/junlong4321/ped/issues).
