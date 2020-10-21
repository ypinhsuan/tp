---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<img src="images/ArchitectureDiagram.png" width="450" />

The ***Architecture Diagram*** given above explains the high-level design of the App. Given below is a quick overview of each component.

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/se-edu/addressbook-level3/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

**`Main`** has two classes called [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

Each of the four components,

* defines its *API* in an `interface` with the same name as the Component.
* exposes its functionality using a concrete `{Component Name}Manager` class (which implements the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class which implements the `Logic` interface.

![Class Diagram of the Logic Component](images/LogicClassDiagram.png)

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

The sections below give more details of each component.

### UI component

![Structure of the UI Component](images/UiClassDiagram.png)

**API** :
[`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* Executes user commands using the `Logic` component.
* Listens for changes to `Model` data so that the UI can be updated with the modified data.

### Logic component

![Structure of the Logic Component](images/LogicClassDiagram.png)

**API** :
[`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

1. `Logic` uses the `AddressBookParser` class to parse the user command.
1. This results in a `Command` object which is executed by the `LogicManager`.
1. The command execution can affect the `Model` (e.g. adding a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.
1. In addition, the `CommandResult` object can also instruct the `Ui` to perform certain actions, such as displaying help to the user.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

### Model component

![Structure of the Model Component](images/ModelClassDiagram.png)

**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user’s preferences.
* stores the address book data.
* exposes an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.


<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique `Tag`, instead of each `Person` needing their own `Tag` object.<br>
![BetterModelClassDiagram](images/BetterModelClassDiagram.png)

</div>


### Storage component

![Structure of the Storage Component](images/StorageClassDiagram.png)

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the address book data in json format and read it back.

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

![CommitActivityDiagram](images/CommitActivityDiagram.png)

#### Design consideration:

##### Aspect: How undo & redo executes

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

## Display Statistics Feature

#### Implementation

The display statistics mechanism is facilitated by `StatisticsCommand`. It extends `Command`.

* DisplayStatisticsCommand#execute(): Do validity check and returns a specific student's statistics if all
 validations passed.
 
These operations are exposed in the `Command` interface as `Command#execute()`. The following sequence diagram shows 
the interactions within the `Logic` component during the execution of a `StatisticsCommand`:

![StatisticsSequenceDiagram](images/StatisticsSequenceDiagram.png)

1. `Logic` uses the `TutorsPetParser` class to parse the user command.
3. A new instance of a `StatisticsCommand` object would be created by the `StatisticsCommandParser` and returns to
 `TutorsPetParser`.
2. `TutorsPetParser` encapsulates the `StatisticsCommand` object as a `Command` object which is executed by
 the `LogicManager`.
3. The command execution affects the `ModuleClassUtil` and `LessonUtil` as static methods from those classes are
 called.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

### Design Consideration:

#### Aspect 1: How statistics feature executes

* **Alternative 1:** Obtain all attendance information within `StatisticsCommand#execute()`.
    * Pros: Easy to implement.
    * Cons: Violates the law of demeter to a large extent.
    
* **Alternative 2 (cuurent choice):** Extract the methods out to another class 
(`ModuleClassUtil` and `LessonClassUtil`).
    * Pros: Does not violate the law of demeter. Increases cohesion and thus increasing maintainability.
    * Cons: Requires more wrapper methods to carry information. More effort to implement.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage students enrolled in classes
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage students and classes faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​ | I want to... | So that I can... |
| --- | --- | --- | --- |
| `* * *` | Tutor with many students | Store my students' contact info/emails | Contact them easily |
| `* * *` | Tutor with many classes | Create classes | Put my students in the appropriate classes |
| `* * *` | Tutor with many classes | Insert my students into the appropriate classes | Organise my students via classes
| `* * *` | New Tutor | View the help menu | Be familiar with app usage |
| `* * *` | Tutor | Delete student entries | Update my list of students if a student were to drop the class |
| `* *` | Tutor | View my students' test scores easily | Gauge my teaching efficiency |
| `* *` | Tutor | Mark my student's attendance and participation | Gauge each student's participation level |
| `* *` | Tutor teaching CS modules | View my students' GitHub | Gauge my student's progress in their coding assignment |
| `* *` | Tutor | View all my lessons | Manage my time more efficiently |
| `* *` | Tutor | Record my feedback for my students | Give participation points |
| `* *` | Forgetful Tutor | Track all my tasks | Know which is of greater urgency |
| `* *` | Busy Tutor | Store zoom links | Retrieve these zoom links for my lessons, consultations, etc. |
| `* *` | Busy Tutor | Store class rooms | Be reminded of my lesson venues |
| `* *` | Tutor with many students | Keep notes on each student's performance | Track their progress over time |
| `* *` | Careless tutor | Undo my commands | Correct any errors when I input things wrongly |
| `* *` | Tutor | Set recurring events (eg. lessons for every week) | Avoid typing the same events |
| `* *` | Tutor | Calculate the mean, median, mode of my students' scores | Gauge the overall performance of my class |
| `* *` | Tutor | Store the questions asked by students | Provide students with answers immediately, for questions that were asked before |
| `* *` | Tutor | Be reminded of my lessons | Attend them |
| `* *` | Tutor | Update the information of my students | Update my understanding of the progress of my students |
| `* *` | Tutor | Categorise my students into how well they are doing | Dedicate more time towards the weaker students |
| `* *` | Careless Tutor | Redo my undone actions | Easily reverse my accidental undos. |
| `*` | Caring tutor | Take note of student's special needs, if any | Cater my teaching toward them |
| `*` | Tutor for many semesters | Archive my past semesters | Avoid cluttering the app |
| `*` | Tutor | Store teaching feedback given by my students | Improve my teaching |
| `*` | Tutor | Prioritise my tasks | Work on important tasks first |
| `*` | Tutor | Keep track of the hours I have spent teaching/preparing for class | Be aware of how much time I have spent on teaching |
| `*` | Tutor | Store teaching feedback given by Professors | Improve my teaching |
| `*` | Forgetful Tutor | Set an alert for task deadlines | Complete my tasks on time |
| `*` | Forgetful Tutor | Store picture of my students | Easily match their faces to their names |
| `*` | Tutor with many classes | See all tasks related to a class | Easily tell which task belongs to which class |
| `*` | Tutor teaching modules that require many written assignments | View my student's written submissions | Mark/review their homework |
| `*` | Tutor | Manage my teaching materials | Find them easily |
| `*` | Tutor | Find free time | Provide consultation for students |

### Use cases

| Use Case ID | Description                               |
|-------------|-------------------------------------------|
| UC01        | Add a student                             |
| UC02        | List students                             |
| UC03        | Edit a student                            |
| UC04        | Find a student                            |
| UC05        | Delete a student                          |
| UC06        | Clear all students                        |
| UC07        | Add a class                               |
| UC08        | List classes                              |
| UC09        | List students within a class              |
| UC10        | Edit a class                              |
| UC11        | Find a class                              |
| UC12        | Delete a class                            |
| UC13        | Clear all classes                         |
| UC14        | List students and classes                 |
| UC15        | Link a student to a class                 |
| UC16        | Unlink a student from a class             |
| UC17        | Add a lesson                              |
| UC18        | Edit a lesson                             |
| UC19        | Delete a lesson                           |
| UC20        | Add an attendance record of a student     |
| UC21        | Edit the attendance record of a student   |
| UC22        | Find the attendance record of a student   |
| UC23        | Delete the attendance record of a student |

(For all use cases below, the **System** is `Tutor's Pet` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Add a student**

**MSS**

1.  User requests to add a student.
2.  User provides the parameters to be added.
3.  Tutor's Pet adds the student.

    Use case ends.

**Extensions**

* 2a. The parameters provided are invalid.

    * 2a1. Tutor's Pet shows an error message.

      Use case ends.

**Use case: UC02 - List students**

**MSS**

1.  User requests to list students.
2.  Tutor's Pet shows a list of students.

    Use case ends.

**Use case: UC03 - Edit a student**

**MSS**

1.  User requests to list students.
2.  Tutor's Pet shows a list of students.
3.  User requests to edit a specific student in the list.
4.  User provides the parameters that are to be edited.
5.  Tutor's Pet edits the student's information.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Tutor's Pet shows an error message.

      Use case resumes at step 2.

* 4a. The parameters provided are invalid.

    * 4a1. Tutor's Pet shows an error message.

      Use case resumes at step 2.

**Use case: UC04 - Find a student**

**MSS**

1.  User requests to find a student.
2.  User provides the parameters to find student.
3.  Tutor's Pet finds the student.
4.  Tutor's Pet displays all matching students found.

    Use case ends.

**Extensions**

* 3a. No students found.

    * 3a1. Tutor's Pet displays a message that no students are found.

      Use case ends.

**Use case: UC05 - Delete a student**

**MSS**

1.  User requests to list students.
2.  Tutor's Pet shows a list of students.
3.  User requests to delete a specific student in the list.
4.  Tutor's Pet removes the student from its associated class.
5.  Tutor's Pet deletes the student.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Tutor's Pet shows an error message.

      Use case resumes at step 2.

* 4a. The student is not in a class.

  Use case skips to step 5.

**Use case: UC06 - Clear all students**

**MSS**

1. User requests to clear all students.
2. Tutor's Pet clears all students.

    Use case ends.

**Use case: UC07 - Add a class**

**MSS**

1. User requests to add a class.
2. User provides the parameters of the class.
3. Tutor's Pet adds the class.

    Use case ends.

**Extensions**

* 2a. The parameters provided are invalid.

    * 2a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

* 3a. The class already exists.

    * 3a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

**Use case: UC08 - List classes**

**MSS**

1.  User requests to list classes.
2.  Tutor's Pet shows a list of classes.

    Use case ends.

**Use case: UC09 - List students within a class**

**MSS**

1.  User requests to list students within a class.
2.  User specifies the class.
3.  Tutor's Pet shows a list of students in the class.

    Use case ends.

**Extensions**

* 2a. The given class index is invalid.

    * 2a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

**Use case: UC10 - Edit a class**

**MSS**

1.  User requests to list classes.
2.  Tutor's Pet shows a list of classes.
3.  User requests to edit a specific class in the list.
4.  User provides the parameters that are to be edited.
5.  Tutor's Pet edits the class's information.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Tutor's Pet shows an error message.

      Use case resumes at step 2.

* 4a. The parameters provided are invalid.

    * 4a1. Tutor's Pet shows an error message.

      Use case resumes at step 2.

**Use case: UC11 - Find a class**

**MSS**

1.  User requests to find a class.
2.  User provides the parameters to find class.
3.  Tutor's Pet finds the class.
4.  Tutor's Pet displays all matching classes found.

    Use case ends.

**Extensions**

* 3a. No classes found.

    * 3a1. Tutor's Pet displays a message that no classes are found.

      Use case ends.

**Use case: UC12 - Delete a class**

**MSS**

1.  User requests to list classes.
2.  Tutor's Pet shows a list of classes.
3.  User requests to delete a specific class in the list.
4.  Tutor's Pet deletes the class.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Tutor's Pet shows an error message.

      Use case resumes at step 2.

**Use case: UC13 - Clear all classes**

**MSS**

1. User requests to clear all classes.
2. Tutor's Pet clears all classes.

    Use case ends.

**Use case: UC14 - List students and classes**

**MSS**

1.  User requests to list students and classes.
2.  Tutor's Pet shows a list of students and a list of classes.

    Use case ends.

**Use case: UC15 - Link a student to a class**

**MSS**

1.  User requests to list classes.
2.  Tutor's Pet shows a list of classes.
3.  User requests to list students.
4.  Tutor's Pet shows a list of students.
5.  User requests to link a specific student to a specific class in the list.
6.  Tutor's Pet links the specified student to the specified class.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 4a. The list is empty.

  Use case ends.

* 5a. At least one of the given indexes is invalid.

    * 5a1. Tutor's Pet shows an error message.

      Use case resumes at step 4.

**Use case: UC16 - Unlink a student from a class**

**MSS**

1.  User requests to list classes.
2.  Tutor's Pet shows a list of classes.
3.  User requests to list students.
4.  Tutor's Pet shows a list of students.
5.  User requests to unlink a specific student from a specific class in the list.
6.  Tutor's Pet unlinks the specified student from the specified class.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 4a. The list is empty.

  Use case ends.

* 5a. At least one of the given indexes is invalid.

    * 5a1. Tutor's Pet shows an error message.

      Use case resumes at step 4.

* 5b. The specified student is not linked to the specified class.

    * 5b1. Tutor's Pet shows an error message.

      Use case ends.

**Use case: UC17 - Add a lesson**

**MSS**

1. User requests to add a lesson.
2. User provides the parameters of the lesson.
3. Tutor's Pet adds the lesson.

    Use case ends.

**Extensions**

* 2a. The parameters provided are invalid.

    * 2a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

* 3a. The lesson already exists.

    * 3a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

**Use case: UC18 - Edit a lesson**

**MSS**

1.  User requests to edit a specific lesson.
2.  User provides the parameters that are to be edited.
3.  Tutor's Pet edits the lesson's information.

    Use case ends.

**Extensions**

* 1a. One or more of the given indexes are invalid.

    * 1a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

* 2a. The parameters provided are invalid.

    * 2a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

**Use case: UC19 - Delete a lesson**

**MSS**

1.  User requests to delete a specific lesson.
2.  Tutor's Pet deletes the lesson.

    Use case ends.

**Extensions**

* 1a. One or more of the given indexes are invalid.

    * 1a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

**Use case: UC20 - Add an attendance record of a student**

**MSS**

1. User requests to add an attendance record of a student.
2. User provides the parameters of the attendance record.
3. Tutor's Pet adds the attendance record.

    Use case ends.

**Extensions**

* 2a. The parameters provided are invalid.

    * 2a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

* 3a. The attendance record already exists.

    * 3a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

**Use case: UC21 - Edit the attendance record of a student**

**MSS**

1.  User requests to edit a specific attendance record.
2.  User provides the parameters that are to be edited.
3.  Tutor's Pet edits the attendance record's information.

    Use case ends.

**Extensions**

* 1a. One or more of the given indexes are invalid.

    * 1a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

* 2a. The parameters provided are invalid.

    * 2a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

**Use case: UC22 - Find the attendance record of a student**

**MSS**

1.  User requests to find an attendance record.
2.  User provides the parameters to find attendance record.
3.  Tutor's Pet finds the attendance record.
4.  Tutor's Pet displays matching attendance record found.

    Use case ends.

**Extensions**

* 3a. No attendance records found.

    * 3a1. Tutor's Pet displays a message that no attendance records are found.

      Use case ends.

**Use case: UC23 - Delete the attendance record of a student**

**MSS**

1.  User requests to delete a specific attendance record.
2.  Tutor's Pet deletes the attendance record.

    Use case ends.

**Extensions**

* 1a. One or more of the given indexes are invalid.

    * 1a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 students and classes without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
