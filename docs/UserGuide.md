---
layout: page
title: User Guide
---

Tutor's Pet is a **desktop application for managing students and classes, optimised for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI).
If you can type fast, Tutor's pet can get your class management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `tutorspet.jar`.

3. Copy the file to the folder you want to use as the _home folder_ for your Tutor's Pet.

4. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all students.

   * **`add-student`**`n/John Doe t/@johndoe e/johnd@example.com tag/student` : Adds a student named `John Doe` to the application.

   * **`delete-student`**`3` : Deletes the 3rd student shown in the current list.

   * **`clear-student`** : Deletes all students.

   * **`exit`** : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add-student n/NAME`, `NAME` is a parameter which can be used as `add-student n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [tag/TAG]` can be used as `n/John Doe tag/student` or as `n/John Doe`.

* Items with `…​` after them can be used multiple times including zero times.<br>
  e.g. `[tag/TAG]…​` can be used as ` ` (i.e. 0 times), `tag/student`, `tag/average tag/TA candidate` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME t/TELEGRAM_HANDLE`, `t/TELEGRAM_HANDLE n/NAME` is also acceptable.

</div>

## Command overview

| Action                          | Format, Examples                                                                                                                            |
|---------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------|
| **Help**                        | `help`                                                                                                                                      |
| **Add Student**                 | `add-student n/NAME t/TELEGRAM_HANDLE e/EMAIL [tag/TAG]…​` <br> e.g., `add-student n/John Doe t/@johndoe e/johnd@example.com tag/student`|
| **List All Students**           | `list`                                                                                                                                      |
| **Edit Student**                | `edit-student INDEX [n/NAME] [t/TELEGRAM_HANDLE] [e/EMAIL] [tag/TAG]…​`<br> e.g., `edit-student 2 n/James Lee e/jameslee@example.com`    |
| **Find Student**                | `find-student KEYWORD [MORE_KEYWORDS]`<br> e.g., `find-student James Jake`                                                                  |
| **Delete Student**              | `delete-student INDEX`<br> e.g., `delete-student 3`                                                                                         |
| **Clear All Students**          | `clear-student`                                                                                                                             |
| **Add Class**                   | `add-class n/CLASS_NAME`<br> e.g., `add-class n/CS2103T Tutorial T10`                                                                       |
| **List Students In A Class**    | `list-students c/INDEX`<br> e.g., `list-students c/3`                                                                                       |
| **Edit Class**                  | `edit-class INDEX n/CLASS_NAME` <br> e.g., `edit-class 1 n/CS2103T Tutorial T10`                                                                  |
| **Find Class**                  | `find-class KEYWORD [MORE_KEYWORDS]`<br> e.g., `find-class CS2103T`                                                                         |
| **Delete Class**                | `delete-class INDEX` e.g., `delete-class 2`                                                                                                 |
| **Clear All Classes**           | `clear-class`                                                                                                                               |
| **Linking Student To A Class**  | ``link s/STUDENT_INDEX c/CLASS_INDEX``<br> e.g., `link s/1 c/2`                                                                  |
| **Unlink Student From A Class** | `unlink s/STUDENT_INDEX c/CLASS_INDEX` <br> e.g., `unlink s/1 c/2`                                                                          |
| **Exit**                        | `exit`                                                                                                                                      |

### Viewing help : `help`

Shows a message explaining how to access the help page.

Format: `help`

### Managing Students

#### Adding a student : `add-student`

Adds a student to the list of students.

Format: `add-student n/NAME t/TELEGRAM_HANDLE e/EMAIL [tag/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A student can have any number of tags (including 0)
</div>

Examples:
* `add-student n/John Doe t/@johndoe e/johnd@example.com`
* `add-student n/Betsy Crowe t/@betsycrowe e/betsycrowe@example.com tag/student`

#### Listing all students : `list`

Shows a list of all students in the application.

Format: `list`

#### Editing a student : `edit-student`

Edits an existing student in the application.

Format: `edit-student INDEX [n/NAME] [t/TELEGRAM_HANDLE] [e/EMAIL] [tag/TAG]…​`

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the student will be removed i.e adding of tags is not cumulative.
* You can remove all the student’s tags by typing `tag/` without
    specifying any tags after it.

Examples:
*  `edit-student 1 t/@johndoe e/johndoe@example.com` Edits the telegram handle and email address of the 1st student to be `@johndoe` and `johndoe@example.com` respectively.
*  `edit-student 2 n/Betsy Crower tag/` Edits the name of the 2nd student to be `Betsy Crower` and clears all existing tags.

#### Finding student by name : `find-student`

Finds students whose names contain any of the given keywords.

Format: `find-student KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Students matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find-student John` returns `john` and `John Doe`
* `find-student alex david` returns `Alex Yeoh`, `David Li`<br>

#### Deleting a student : `delete-student`

Deletes the specified student from the application.

Format: `delete-student INDEX`

* Deletes the student at the specified `INDEX`.
* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete-student 2` deletes the 2nd student in the application.
* `find-student Betsy` followed by `delete-student 1` deletes the 1st student in the results of the `find-student` command.

#### Clearing all students : `clear-student`

Clears all students from the application.

Format: `clear-student`

### Managing Classes : [coming soon]

#### Adding a class : `add-class`

Adds a class to the application.

Format: `add-class n/CLASS_NAME`

Examples:
* `add-class n/CS2103T Tutorial T10`

#### Listing all students within a class : `list-students`

Shows a list of all students within a particular class in the application.

Format: `list-students c/INDEX`

* The index refers to the index number shown in the displayed class list.

Examples:
* `list-students c/3`

#### Editing a class : `edit-class`

Edits an existing class in the application.

Format: `edit-class INDEX n/CLASS_NAME`

* Edits the class at the specified `INDEX`.
* The index refers to the index number shown in the displayed class list.
* The index **must be a positive integer** 1, 2, 3, …​
* A new class name must be provided.

Examples:
*  `edit-class 1 n/CS2103T Tutorial T10` Edits the class name of the 1st class to be `CS2103T Tutorial T10`.

#### Finding class by name : `find-class`

Finds classes whose names contain any of the given keywords.

Format: `find-class KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `cs2103t` will match `CS2103T`
* The order of the keywords does not matter. e.g. `lab CS2100` will match `CS2100 lab`
* Only the name is searched.
* Only full words will be matched e.g. `CS` will not match `CS2100`
* Students matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `CS2103T lab` will return `CS2103T tut`, `CS2100 lab`

Examples:
* `find-class CS2100` returns `CS2100 tut` and `CS2100 lab`
* `find-class tut CS2030` returns `CS2103T tut`, `CS2030 lab`<br>

#### Deleting a class : `delete-class`

Deletes the specified class from the application.

Format: `delete-class INDEX`

* Deletes the class at the specified `INDEX`.
* The index refers to the index number shown in the displayed class list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `delete-class 2` deletes the 2nd class in the application.

#### Clearing all classes : `clear-class`

Clears all classes from the application.

Format: `clear-class`

#### Linking a student to a class : `link`

Links an existing student to an existing class in the application.

Format: `link s/STUDENT_INDEX c/CLASS_INDEX`

* Links the student at the specified `STUDENT_INDEX` to the class at the specified `CLASS_INDEX`.
* `STUDENT_INDEX` refers to the index number shown in the displayed student list.
* `CLASS_INDEX` refers to the index number shown in the displayed class list.
* The indexes **must be positive integers** 1, 2, 3, …​

Examples:
* `list` followed by `link s/1 c/2` links the 1st student in the application to the 2nd class in the application.
* `find-student Betsy` followed by `link s/1 c/2` links the 1st student in the results of the `find-student` command to the 2nd class in the application.

#### Unlinking a student from a class : `unlink`

Unlinks an existing student from an existing class in the application.

Format: `unlink s/STUDENT_INDEX c/CLASS_INDEX`

* Unlinks the student at the specified `STUDENT_INDEX` from the class at the specified `CLASS_INDEX`.
* `STUDENT_INDEX` refers to the index number shown in the displayed student list.
* `CLASS_INDEX` refers to the index number shown in the displayed class list.
* The indexes **must be positive integers** 1, 2, 3, …​

Examples:
*  `unlink s/1 c/2` Unlinks the 1st student from the 2nd class in the respective lists.

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

Tutor's Pet automatically saves your data to your hard disk after any changes are made.
There is no need to save manually.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Tutor's Pet home folder.

--------------------------------------------------------------------------------------------------------------------
