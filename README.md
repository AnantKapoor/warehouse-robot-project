# Warehouse Assignment - RP 2018

## Job Roles
* **Team Manager:** Anant Kapoor

### Team 1
* Tarun
* Han
* Arun
* Om

### Team 2
* Gintarė
* Martynas
* Tom

### Team 3
* Anant
* Hitesh
* Will


## Meeting Summaries
### **27/01/2018**
#### Members present:
* Anant Kapoor
* Gintarė Statkutė
* Will Tetlow

#### What was discussed:
* Meet and greet
* Overview of markscheme posted by Phil
https://canvas.bham.ac.uk/courses/27283/pages/warehouse-assignment-mark-scheme
* Discussion of difficulty of each individual task and role
https://canvas.bham.ac.uk/courses/27283/assignments/98523

 **Difficulty Level based on marks awarded** (descending)
* (Multi Robot) Route planning
* Localisation
* Integration
* Route Excecution
* Job Assignment 
* Warehouse Interface
* Job Selection
* Robot Motion Control
* Robot Interface

* Concluded that roles should be assigned to sub-teams to allow easier collaboration for each task

#### Next Meeting
**01/03/2018**
##### Agenda
* Assign individuals to sub teams
* Assign roles to sub-teams
* Work towards completing intermediate submission

### **01/03/2018**
#### Members present:
* Anant Kapoor
* Gintarė Statkutė
* Will Tetlow
* Arun Venu
* Martynas Greičius
* Om Sai Krishna Sarnala
* Tarun Sampat
* Seonghee Han

#### What was discussed:
* Assigned roles to sub teams
* Assigned individuals to sub teams

##### Team 1
* Grid Movement
* Robot Interface
* Robot Building
* Route Planning

##### Team 2
* Input/Output
* Algorithms
* Job Selection
* Route Planning (less emphasis than group 1)

##### Team 3
* Multi Robot Communication
* Warehouse Interface

#### Next Meeting
**02/03/2018 - 12pm**
##### Agenda
* Meet with TA

### **02/03/2018**
#### Members present:
* Anant
* Gintaré
* Om
* Tarun
* Arun
* Martynas

#### What was discussed
* Meeting with TA

TA discussed:
* To integrate basic solutions early on
* To create a diagram to plan project before hardcoding

#### Next Meeting
**08/03/2018 - 12pm**
##### Agenda
* Meet with TA
* Discuss progress of each team
* Set objectives for each team

### **07/03/2018**
#### Short notice meeting, not all members available 
#### Members present:
* Anant
* Gintaré
* Tarun
* Arun
* Will
* Hitesh

#### What was discussed
* Created UML diagram for project
* Individual marking for each sub-teams tasks

#### Next Meeting
**08/03/2018 - 12pm**
##### Agenda
* Meet with TA
* Discuss progress of each team
* Set objectives for each team

### **08/03/2018**
#### Members present:
* Anant
* Gintaré
* Om
* Tarun
* Arun
* Martynas
* Hitesh
* Han

#### What was discussed
* Meeting with TA

TA discussed:
* Individual marking questions
* Information about intermediate submission
* **VIVA AT 14:30 12/03/18**
* Assigned demonstration tasks for each team for VIVA

#### Team 1
* Example of robot interface
* Demonstration of robot motion on grid
* Demonstration of route excecution, following a scripted route

#### Team 2
* Demonstration of job being selected from list of jobs
* Demonstration of jobs being assigned in order
* Demonstration of A* search

#### Team 3
* Example of warehouse interface
* Simple GUI that displays job list with ability to cancel jobs
* Communication between pc and single robot

### Integration
* Route sent to robot after job being selected
* Robot follows route recieved

#### Next Meeting
**12/03/2018 - 2:30pm**
##### Agenda
**VIVA**

### **11/03/2018**
#### Members present:
* Anant
* Gintaré
* Tarun
* Om
* Martynas
* Hitesh

#### What was discussed
* Integration of pc-subsystems
* Integration of robot-subsystems

#### Next Meeting
**12/03/2018 - 2:30pm**
##### Agenda
**VIVA**

## Intermediate Submission
### **Due 12 March - 17:00**
#### Expected to submit the following:
* A breakdown of the overall assignment into roles/subsystems.
* A mapping from team members to roles/subsystems.
* For each subsystem where appropriate, a suite of JUnit tests which you expect the final subsystem to pass.
* A prototype implementation of each subsystem which can be run against the tests (but may fail all of them).
* One or more Git repositories which can be monitored to observe development progress.
* Ideally some demonstration of partial subsystem integration.
* Any additional documentation necessary to support your team's progress (e.g. notes on who has worked well to deadlines).

#### Additional Information
* 20 minutes long
* Split into demonstrations and individuals discussing their contributions to code

### **14/03/2018**
#### Members present:
* Anant
* Gintaré
* Arun

#### What was discussed
* Discussion of remaining tasks for final submission
* Assigned remaining tasks to sub teams 

### Team 1
#### Objectives:
* **Robot Interface**
* Unreliable human:
* *Wrong number*
* *timeouts*
* Additional capabilites 
* **Route Excecution**
* Recieving position updates for checking against plan and updating world model

### Team 2
#### Objectives:
* **Job Selection**
* Cancellation prediction
* Take current location of robot into account
* Sharing jobs across robots rather than one job per robot
* **Multi Robot route planning**
* Multiple robots, avoiding collisions


### Team 3
#### Objectives :
* **Bluetooth Communication**
* Implement for multiple robots
* **Warehouse Interface**
* Robot plans and position updates
* **Localisation**
* Probalistic localisation using distance sensor

#### Next Meeting
**15/03/2018 - 11:00 Room 225**
##### Agenda
* Meet with TA

### **15/03/2018**
#### Members present:
* Anant
* Gintaré
* Arun
* Martynas
* Tarun
* Om
* Will

#### What was discussed
* Meeting with TA
* Discussed performance for intermediate deadline (20/20)
* Discussed objectives for final week coming up to final warehouse submission.

##### Warehouse Interface

* List of previously completed jobs
* Know which robot is doing which job
* **Extension** Display robot's route/location

##### Robot Motion

* Rather than hardcoding turning value, alter algorithm so robot turns into line is found

##### Robot Interface

* **Optional** Show where robot is and what they're doing
##### Bluetooth

* Multiple threads for multiple robots
* Serialisation and de-serialisation

##### Route Excecution

* Look at problems that may arise and solve them before they happen i.e. robot losing line etc

##### Cancellation Prediction

* Prioritise over localisation
* Use any library available


