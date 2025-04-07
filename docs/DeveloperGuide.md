---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# RecruitTrackPro Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

<div style="page-break-after: always;"></div>

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

<div style="page-break-after: always;"></div>

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

<div style="page-break-after: always;"></div>

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `RecruitTrackProParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `RecruitTrackProParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `RecruitTrackProParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

<div style="page-break-after: always;"></div>

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the recruit track pro data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `RecruitTrackPro`, which `Person` references. This allows `RecruitTrackPro` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both RecruitTrackPro data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `RecruitTrackProStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.recruittrackpro.commons` package.

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Add tags feature
Given below is the activity diagram of a `AddTagsCommand`.

<puml src="diagrams/AddTagsActivityDiagram.puml" alt="AddTagsActivityDiagram" />

<div style="page-break-after: always;"></div>

### Remove tags feature
Given below is the activity diagram of a `RemoveTagsCommand`.

<puml src="diagrams/RemoveTagsActivityDiagram.puml" alt="RemoveTagsActivityDiagram" />

<div style="page-break-after: always;"></div>

### Edit tag feature
Given below is the activity diagram of a `EditTagCommand`.

<puml src="diagrams/EditTagActivityDiagram.puml" alt="EditTagActivityDiagram" />

### Find by tag feature
Given below is the activity diagram of a `FindCommand` using a `t/` prefix only.

<puml src="diagrams/FindActivityDiagram.puml" alt="FindActivityDiagram" />

<div style="page-break-after: always;"></div>

### Switch sort feature
Given below is the activity diagram of a `SwitchSortCommand`.

<puml src="diagrams/SwitchSortActivityDiagram.puml" alt="SwitchSortActivityDiagram" />

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedRecruitTrackPro`. It extends `RecruitTrackPro` with an undo/redo history, stored internally as an `recruitTrackProStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedRecruitTrackPro#commit()` — Saves the current RecruitTrackPro state in its history.
* `VersionedRecruitTrackPro#undo()` — Restores the previous RecruitTrackPro state from its history.
* `VersionedRecruitTrackPro#redo()` — Restores a previously undone RecruitTrackPro state from its history.

These operations are exposed in the `Model` interface as `Model#commitRecruiTrackPro()`, `Model#undoRecruiTrackPro()` and `Model#redoRecruiTrackPro()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedRecruitTrackPro` will be initialized with the initial RecruitTrackPro state, and the `currentStatePointer` pointing to that single RecruitTrackPro state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th candidate in RecruitTrackPro. The `delete` command calls `Model#commitRecruitTrackPro()`, causing the modified state of RecruitTrackPro after the `delete 5` command executes to be saved in the `recruitTrackProStateList`, and the `currentStatePointer` is shifted to the newly inserted RecruitTrackPro state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new candidate. The `add` command also calls `Model#commitRecruitTrackPro()`, causing another modified RecruitTrackPro state to be saved into the `recruitTrackProStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitRecruitTrackPro()`, so the RecruitTrackPro state will not be saved into the `recruitTrackProStateList`.

</box>

Step 4. The user now decides that adding the candidate was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoRecruitTrackPro()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous RecruitTrackPro state, and restores the RecruitTrackPro application to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial RecruitTrackPro state, then there are no previous RecruitTrackPro states to restore. The `undo` command uses `Model#canUndoRecruitTrackPro()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

<div style="page-break-after: always;"></div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoRecruitTrackPro()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the RecruitTrackPro application to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `recruitTrackProStateList.size() - 1`, pointing to the latest RecruitTrackPro state, then there are no undone RecruitTrackPro states to restore. The `redo` command uses `Model#canRedoRecruitTrackPro()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the RecruitTrackPro application, such as `list`, will usually not call `Model#commitRecruitTrackPro()`, `Model#undoRecruitTrackPro()` or `Model#redoRecruitTrackPro()`. Thus, the `recruitTrackProStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitRecruitTrackPro()`. Since the `currentStatePointer` is not pointing at the end of the `recruitTrackProStateList`, all RecruitTrackPro states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire RecruitTrackPro application.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the candidate being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.


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

* HR recruiters who handle a high volume of applicants.
* Prefers desktop applications over web or mobile alternatives.
* Types quickly and favors keyboard shortcuts over mouse navigation.
* Prioritizes speed and efficiency in tracking and managing applicant details.
* Comfortable with command-line interfaces to some extent.

**Value proposition**: RecruitTrackPro streamlines applicant tracking, ensures quick data retrieval, and reduces administrative workload, making recruitment management more efficient.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​   | I want to …​                                                                                                         | So that I can…​                                                                  |
|----------|-----------|----------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------|
| `* * *`  | Recruiter | add a new candidate by specifying their name, phone, and email                                                       | store their basic contact information in one place                               |
| `* * *`  | Recruiter | delete a candidate                                                                                                   | remove entries for candidates who are no longer relevant                         |
| `* * *`  | Recruiter | list all candidates                                                                                                  | have a quick overview of everyone in my current pipeline                         |
| `* * *`  | Recruiter | insert tags such as candidate skills and experience in their profile (e.g., “Java Developer”, “University Graduate”) | easily identify candidates who meet the required skills or experience for a role |
| `* * *`  | Recruiter | exit the application via a command                                                                                   | cleanly close the program when I’m finished                                      |
| `* * *`  | Recruiter | store all application data locally                                                                                   | access it even if I’m offline or between network connections                     |
| `* *`    | Recruiter | add free-form notes to a candidate’s record                                                                          | capture extra context or personal observations                                   |
| `* *`    | Recruiter | update a candidate’s tags                                                                                            | adjust how candidates are categorized over time                                  |
| `* *`    | Recruiter | remove a candidate’s tags                                                                                            | adjust how candidates are categorized over time                                  |
| `* *`    | Recruiter | edit a candidate’s details (e.g., phone, email, address)                                                             | keep the record accurate when a candidate’s information changes                  |
| `* *`    | Recruiter | find a candidate by name                                                                                             | locate a specific candidate’s details quickly                                    |
| `* *`    | Recruiter | sort the candidate list by name                                                                                      | see them in alphabetical order for easier browsing                               |
| `* *`    | Recruiter | find candidates by other fields (e.g., phone, email)                                                                 | organize the data in the most useful way for my tasks                            |
| `* *`    | Recruiter | find candidates with a set of specific fields (e.g., phone, email)                                                   | organize the data in the most useful way for my tasks                            |
| `* *`    | Recruiter | filter for candidates by specific skills (i.e. tags)                                                                 | find the most qualified applicants for a role                                    |
| `*`      | Recruiter | update the (optional) reference contact info for each candidate                                                      | have all necessary details for thorough background checks                        |
| `*`      | Recruiter | attach or link a candidate’s resume in their profile                                                                 | access their CV directly from the application                                    |
| `*`      | Recruiter | count the number of times I’ve contacted a candidate                                                                 | keep track of communication attempts without spamming them                       |
| `*`      | Recruiter | create new tag categories (e.g., “Willing to Relocate”)                                                              | capture additional attributes beyond standard fields                             |
| `*`      | Recruiter | mark specific candidates as “High Priority”                                                                          | focus on them first for interviews or offers                                     |
| `*`      | Recruiter | view my command history                                                                                              | reuse previous commands or correct mistakes                                      |
| `*`      | Recruiter | undo my last command                                                                                                 | revert an accidental action (e.g., deleting the wrong candidate)                 |
| `*`      | Recruiter | redo a command I just undid                                                                                          | restore changes if I reverted them by mistake                                    |
| `*`      | New User  | access a help command                                                                                                | quickly learn the available features and syntax                                  |
| `*`      | Recruiter | create a new job posting (e.g., role title, required skills)                                                         | track open positions within my organization                                      |
| `*`      | Recruiter | list all active job openings                                                                                         | quickly see every position that needs to be filled                               |
| `*`      | Recruiter | archive a job posting once it’s filled or no longer needed                                                           | keep historical records without cluttering the active list                       |
| `*`      | Recruiter | assign a candidate to a job posting                                                                                  | track who is applying or being considered for each position                      |
| `*`      | Recruiter | remove a candidate from a job posting	                                                                               | correct mistakes or reassign them to a more suitable role                        |
| `*`      | Recruiter | view assigned candidates under job postings                                                                          | see a focused list of all applicants for a specific position                     |
| `*`      | Recruiter | view a list of unassigned candidates                                                                                 | quickly identify who could be matched to newly opened roles                      |
| `*`      | Recruiter | mark a job posting as “Urgent”                                                                                       | prioritize filling it quickly                                                    |
| `*`      | New User  | import candidate data from a CSV                                                                                     | quickly populate the system using existing records                               |
| `*`      | Recruiter | export candidate data to a CSV                                                                                       | create backups or share the list with others                                     |
| `*`      | New User  | interact with sample data	                                                                                           | see how the app will look like in use                                            |
| `*`      | New User  | purge all current data                                                                                               | delete sample data                                                               |


### Use cases

(For all use cases below, the **System** is the `RecruitTrackPro` and the **Actor** is the `user`, unless specified otherwise)

**Use Case: UC-001 - Add Candidate**

**MSS:**
1. User requests to add a candidate with relevant details.
2. RecruitTrackPro adds the new candidate and displays a success message.

   Use case ends.


**Extensions:**
* 1a. User did not enter all required fields (name, phone number, email, or address).
    * 1a1. RecruitTrackPro displays an error message.

      Use case ends.

* 1b. User enters an invalid format for any field. 
    * 1b1. RecruitTrackPro displays an error message based on the invalid field.

      Use case ends.

* 1c. User enters a duplicate candidate – same name and phone number as an existing candidate. 
    * 1c1. RecruitTrackPro displays an error message.

      Use case ends.

* 1d. User enters multiple values for the same field, excluding tag.
    * 1d1. RecruitTrackPro displays an error message based on the duplicated fields.

      Use case ends.

* 1e. User enters the same tag (case-insensitive) multiple times.
    * 1e1. RecruitTrackPro displays an error message that the tag is duplicated.

      Use case ends.

**Use case: UC-002 - List Candidate**

**MSS**

1. User requests a list of all candidates.
2. RecruitTrackPro displays the list of candidates with relevant details.

   Use case ends.

**Use Case: UC-003 - Delete Candidate**

**MSS**

1. User requests to remove a specified candidate from RecruitTrackPro.
2. RecruitTrackPro updates the displayed list accordingly.
   
   Use case ends.

**Extensions**

* 1a. User enters an invalid index (i.e. not a positive integer).
   * 1a1. RecruitTrackPro shows an error message.
   
     Use case ends.

* 1b. User enters an index that is out of bounds.
   * 1b1. RecruitTrackPro shows an error message.
      
     Use case ends.

**Use case: UC-004 - Find Candidates**

**MSS**

1. User requests to search for candidates by a field and value.
2. RecruitTrackPro shows the list of matching candidates.

    Use case ends.

**Extensions**

* 1a. User did not enter a search field or value.
    * 1a1. RecruitTrackPro shows an error message.

      Use case ends.

* 1b. User enters an invalid search field.
    * 1b1. RecruitTrackPro shows an error message.

      Use case ends.
  
* 1c. User searches by phone number but enters an invalid phone number.
    * 1c1. RecruitTrackPro shows an error message.

      Use case ends.

* 1d. User searches by email but enters an invalid email address.
    * 1d1. RecruitTrackPro shows an error message. 

      Use case ends.

* 1e. User enters an invalid option.
    * 1e1. RecruitTrackPro shows an error message.

      Use case ends.

* 1f. User enters multiple values for the same field.
    * 1f1. RecruitTrackPro displays an error message based on the duplicated fields.

      Use case ends.

**Use case: UC-005 - Add Tag(s) to a Candidate**

**Pre-Condition**

1. The candidate must already exist in the system.

**MSS**

1. User selects a candidate and specifies one or more tags. 
2. RecruitTrackPro displays the list of candidates with the updated information. 

   Use case ends.

**Extensions**

* 1a. Candidate does not exist.
    * 1a1. RecruitTrackPro notifies the user that the candidate does not exist.

      Use case ends.

* 1b. User does not specify any tags.
    * 1b1. RecruitTrackPro prompts the user to enter at least one tag.

      Use case ends.

* 1c. User enters a tag that is already associated with the candidate.
    * 1c1. RecruitTrackPro informs the user that the tag already exists for the candidate.

      Use case ends.

* 1d. User enters the same tag (case-insensitive) multiple times.
    * 1d1. RecruitTrackPro notifies the user that the tag is duplicated.

      Use case ends.

**Use case: UC-006 - Edit Candidate**

**MSS**

1. User requests to edit the field(s) of a specified candidate.
2. RecruitTrackPro shows the list of candidates with the updated information.

   Use case ends.

**Extensions**

* 1a. Candidate specified by the user does not exist.
    * 1a1. RecruitTrackPro notifies the user that the candidate does not exist.

      Use case ends.

* 1b. User enters an invalid format for any field.
    * 1b1. RecruitTrackPro displays an error message based on the invalid field.

      Use case ends.

* 1c. User enters a name and phone number that is the same as an existing candidate.
    * 1c1. RecruitTrackPro displays an error message.

      Use case ends.

* 1d. User enters the same tag (case-insensitive) multiple times.
    * 1d1. RecruitTrackPro displays an error message that the tag is duplicated.

      Use case ends.

* 1e. User enters multiple values for the same field, excluding tag.
    * 1e1. RecruitTrackPro displays an error message based on the duplicated fields.

      Use case ends.

**Use case: UC-007 - Remove Tag from a Candidate**

**MSS**

1. User requests to delete a specific tag for a candidate.
2. RecruitTrackPro removes the specified tag from the candidate’s profile and shows a success message.

   Use case ends.

**Extensions**

* 1a. User enters an invalid candidate index.
    * 1a1. RecruitTrackPro displays an error message: “The selected candidate does not exist. Please check the index and try again.”

      Use case ends.

* 1b. User does not enter any tags.
    * 1b1. RecruitTrackPro displays an error message: “Tag name cannot be empty. Please enter a valid tag.”

      Use case ends.

* 1c. User specifies a tag which does not exist the candidate's record.
    * 1c1. RecruitTrackPro displays an error message: “Tag does not exist for this candidate. Please enter a valid tag.”

      Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should work without requiring an installer.
3.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
4.  Should not be dependent on a remote server.
5.  Should not use a Database Management System to store data.
6.  Data should be stored locally in a human editable text file.
7.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
8.  GUI should be _usable_ for screen resolutions 1280x720 and higher.
9.  User Guide and Developer Guide should be PDF-friendly.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Usable**: All functions can be used even if the user experience is not optimal

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

Team size: 5

1. **Add support for other languages**: The current system is only able to properly handle inputs in English. It does 
not handle inputs in other languages well. We plan to improve the system by adding support for non-English languages 
(e.g. Chinese, Tamil) and right-to-left languages (e.g. Arabic).
2. **Allow names to have special characters**: The current implementation does not allow the `name` field to include 
special characters. We plan to allow special characters that are commonly used in actual names (e.g. `,`, `-`, `/`).
3. **Make `find` handle partial match**: The current implementation for the `find` command does not handle partial 
matches<br> (e.g. `find a/01` does not match the unit number `#01-23` of an address). We plan to make the `find` command 
handle partial matches for all the parameters.
4. **Update command parameter format**: The current implementation identifies command parameters using prefixes<br> 
(e.g. `n/`, `a/`, `c/`). If the command parameter has a value that resembles a valid prefix, the parser will not be 
able to parse it correctly (e.g. `n/Bob a/l John` is treated as `name=Bob`, `address=l John`). We plan to change the 
format of command parameters to be enclosed in `{}` (e.g. `n/NAME` becomes `n{NAME}`).

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder.

   2. Open a terminal in the folder containing the JAR file and run:
      ```
      java -jar recruittrackpro.jar
      ```
      Expected: Shows the GUI with a set of sample candidates. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the application using the same command:
      ```
      java -jar recruittrackpro.jar
      ```
       Expected: The most recent window size and location is retained.

### Deleting a candidate

1. Deleting a candidate while all candidates are being shown

   1. Prerequisites: List all candidates using the `list` command. Multiple candidates in the list.

   2. Test case: `delete 1`<br>
      Expected: First candidate is deleted from the list. Details of the deleted candidate shown in the result message.

   3. Test case: `delete 0`<br>
      Expected: No candidate is deleted. Error details shown in the result message.

   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

2. Deleting a candidate while some candidates are being shown

   1. Prerequisites: Filter the list of candidates using the `find` command. Multiple candidates in the list.

   2. Test case: `delete 1`<br>
      Expected: First candidate is deleted from the list. Details of the deleted candidate shown in the result message.

   3. Test case: `delete 0`<br>
      Expected: No candidate is deleted. Error details shown in the result message.

   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Finding a candidate

1. Finding a candidate by name

   1. Prerequisites: List contains the set of sample candidates.

   2. Test case: `find n/alex david`<br>
      Expected: List updates and only shows Alex Yeoh and David Li.

   3. Test case: `find -ca n/alex david`<br>
      Expected: List updates and is empty.

   4. Test case: `find n/`<br>
      Expected: List does not update. Error details shown in the result message.

2. Finding a candidate by multiple fields

    1. Prerequisites: List contains the set of sample candidates.

    2. Test case: `find n/bernice t/python`<br>
       Expected: List updates and only shows Bernice Yu and Roy Balakrishnan.

    3. Test case: `find -ca n/bernice t/python`<br>
       Expected: List updates and only shows Bernice Yu.

    4. Test case: `find n/ t/python`<br>
       Expected: List does not update. Error details shown in the result message.
