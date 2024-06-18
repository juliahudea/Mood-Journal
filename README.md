# My Personal Project

## Mood Tracker Proposal

A mood tracking application for personal use. The application will let you enter information about your day. Possible features are:
- mood
- energy level
- amount of sleep
- exercise
- diet

The application will be used as a personal tracker of mood to help individuals better understand their mental health.
It can be used by *anyone* who wants to improve their mental health. 

This project is of interest to me because my mental health has the largest impact on my life and I am still trying to understand what affects it.
I would like this application to be used in a more complex mental health application in the future.

## User Stories

- As a user, I want to be able to add my mood for a particular day in a journal entry.
- As a user, I want to add individual journal entries to my mood journal.
- As a user, I want to be able to view my mood journal entries from past days.
- As a user, I want to be able to add how long I slept on a particular day to a journal entry.
- As a user, I want to record the weather on a particular day
- As a user, I want to be able to save my mood journal to a file (if I so choose)
- As a user, I want to be able to load my mood journal from a file (if I so choose)

## Phase 4: Task 2:
Tue Apr 11 21:35:40 PDT 2023
Created entry.

Tue Apr 11 21:35:40 PDT 2023
Added entry.

Tue Apr 11 21:35:40 PDT 2023
Created entry.

Tue Apr 11 21:35:40 PDT 2023
Added entry.

Tue Apr 11 21:35:40 PDT 2023
Created entry.

Tue Apr 11 21:35:40 PDT 2023
Added entry.

Tue Apr 11 21:35:40 PDT 2023
Created entry.

Tue Apr 11 21:35:40 PDT 2023
Added entry.

Tue Apr 11 21:35:40 PDT 2023
Retrieved all entries.

Tue Apr 11 21:35:59 PDT 2023
Created entry.

Tue Apr 11 21:35:59 PDT 2023
Added entry.

Tue Apr 11 21:35:59 PDT 2023
Retrieved entries.

Tue Apr 11 21:36:05 PDT 2023
Retrieved entry.

Tue Apr 11 21:36:13 PDT 2023
Retrieved entries.

## Phase 4: Task 3
Based on the design presented in my UML diagram as well as an assessment of 
the methods and overall code of my project. I would implement the following refactoring
to improve the design if I had more time:
- Refactor the JournalEntry class to use enumeration instead of integers for the mood values. There is some confusion on
the exact values that are allowed for the mood entry that could be fixed with enumeration. It would make it more obvious
what the possible mood values are and reduce errors from invalid integer inputs.
- Implement the Observer Pattern to increase separation between the Gui Class and the MoodJournal Class. I would create 
a change where the MoodJournal acts as the Subject and the Gui class acts as the Observer. I would potentially use an
abstract interface for the Gui that would observe changes in the MoodJournal. The hope is that this change would make it
easier to modify the application and include more features in the future.
- As an add-on to the previous change, filtering the journal entries by mood is currently being done in the Gui by only 
displaying entries with a mood above the given mood value. I would move this feature to be done within the MoodJournal 
class as it would allow for the Observer Pattern to be properly implemented and decouple the Gui from the MoodJournal.