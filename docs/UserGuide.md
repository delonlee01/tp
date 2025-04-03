---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# RecruitTrackPro User Guide

RecruitTrackPro is a **desktop app for managing applicants, optimized for use via a Line Interface** (CLI) while still
having the benefits of a Graphical User Interface (GUI). If you can type fast, RecruitTrackPro can get your applicant
management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2425S2-CS2103-F15-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your RecruitTrackPro.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar recruittrackpro.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the RecruitTrackPro.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message that describes the functionality of each command.

Format: `help`

### Adding a person: `add`

Adds a candidate to the RecruitTrackPro.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]… [c/COMMENT]`

<box type="tip" seamless>

**Tip:** A candidate can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/python e/betsycrowe@example.com a/Newgate Prison p/1234567 t/java`
* `add n/Bruce Wayne e/bwayne@example.com a/Gotham City p/91234567 c/Owner of Wayne Enterprises`

### Listing all candidates : `list`

Shows a list of all candidates in the RecruitTrackPro.

Format: `list`

### Editing a candidate : `edit`

Edits an existing candidate in the RecruitTrackPro.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]… [c/COMMENT]`

* Edits the candidate at the specified `INDEX`. The index refers to the index number shown in the displayed candidate list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the candidate will be removed i.e adding of tags is not cumulative.
* You can remove all the candidate’s tags by typing `t/` without
    specifying any tags after it.
* When editing the comment, the existing comment of the candidate will be overwritten.
* You can clear the candidate’s comment by typing `c/` without
  specifying any comment after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st candidate to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd candidate to be `Betsy Crower` and clears all existing tags.
*  `edit 3 a/Gotham City c/` Edits the address and comment of the 3rd candidate to be `Gotham City` and empty respectively.

### Adding Tag(s) to a Candidate: `add-tags`

Adds one or more tags to an existing candidate in RecruitTrackPro.

Format: `add-tags INDEX t/tag [t/MORE_TAGS]…​`

* Adds the specified tag(s) to the candidate at the specified `INDEX`. The index refers to the number shown in the currently displayed person list. The index **must be a positive integer** (e.g., 1, 2, 3, ...).
* Tags are added **case-insensitively**. For example, `t/java` and `t/Java` are treated as the same tag.
* If a tag already exists for the candidate, it will **not** be added again. The system will notify the user of any **duplicate tag(s)**.
* New, unique tags will be added to the candidate, regardless of casing.

Examples:

*  `add-tags 1 t/Java Developer t/JSP Framework`  Adds the tags `Java Developer` and `JSP Framework` to the first candidate.
*  `add-tags 2 t/Java t/java`  Adds the tag `Java` to the second candidate (only once), the second tag `java` is ignored.
*  `add-tags 2 t/jaVa` Informs user that the tag `Java` already exists.

### Locating candidates by name: `find`

Finds candidates whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* candidates matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Optional: `--contain-all`
* `find --contain-all KEYWORDS` will match candidates will all keywords (i.e. `AND` search).
  e.g. `Hans Bo` will return 0 candidates.  

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a candidate : `delete`

Deletes the specified candidate from the RecruitTrackPro.

Format: `delete INDEX`

* Deletes the candidate at the specified `INDEX`.
* The index refers to the index number shown in the displayed candidate list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd candidate in the RecruitTrackPro.
* `find Betsy` followed by `delete 1` deletes the 1st candidate in the results of the `find` command.

### Switching the sorting order : `switch-sort`

Switches the alphabetical sorting order of candidates between ascending (A to Z) and descending (Z to A).

Format: `switch-sort`

### Clearing all entries : `clear`

Clears all entries from the RecruitTrackPro.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

RecruitTrackPro data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

RecruitTrackPro data are saved automatically as a JSON file `[JAR file location]/data/recruittrackpro.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, RecruitTrackPro will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the RecruitTrackPro to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous RecruitTrackPro home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the User Guide Window** and then use the `User Guide` menu, or the keyboard shortcut `F1` again, the original User Guide Window will remain minimized, and no new User Guide Window will appear. The remedy is to manually restore the minimized User Guide Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action            | Format, Examples                                                                                                                                                            |
|-------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**           | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]… [c/COMMENT]` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/java t/python` |
| **Clear**         | `clear`                                                                                                                                                                     |
| **Delete**        | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                         |
| **Edit**          | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]… [c/COMMENT]`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                      |
| **Add Tag(s)**    | `add-tags INDEX t/TAG [t/MORE_TAGS]... `<br> e.g., `add-tags 1 t/Java Developer t/C# Developer`                                                                             |
| **Edit Tag**      | `edit-tag INDEX from/OLD_TAG to/NEW_TAG `<br> e.g., `edit-tag 1 from/Java Developer to/JavaScript Developer`                                                                |
| **Remove Tag(s)** | `remove-tags INDEX t/TAG [t/MORE_TAGS]... `<br> e.g., `remove-tags 1 t/JavaScript Developer t/C# Developer`                                                                 |
| **Find**          | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake` <br> optional: `--contain-all`                                                                                   |
| **List**          | `list`                                                                                                                                                                      |
| **Help**          | `help`                                                                                                                                                                      |
| **Switch sort**   | `switch-sort`                                                                                                                                                               |
