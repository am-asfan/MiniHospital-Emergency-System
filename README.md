# 🏥 Mini Hospital Emergency Management System

A console-based **Java application** that simulates a real hospital emergency department workflow using four core **Data Structures** implemented manually from scratch — no built-in Java collections used.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Data Structures Used](#data-structures-used)
- [Project Structure](#project-structure)
- [Features](#features)
- [How to Run](#how-to-run)
- [Menu Options](#menu-options)
- [Sample Workflow](#sample-workflow)
- [OOP Concepts Applied](#oop-concepts-applied)
- [Author](#author)

---

## Overview

The **Mini Hospital Emergency Management System** is a DSA (Data Structures and Algorithms) project built in Java. It models how a real emergency department manages patients — from registration and queuing, through treatment, to maintaining a full visit history per patient.

Every data structure is hand-implemented using node-based linked structures (no `ArrayList`, `LinkedList`, `Stack`, or `Queue` from Java's standard library).

---

## Data Structures Used

| Data Structure | Where Used | Why |
|---|---|---|
| **Binary Search Tree (BST)** | Patient Records | Fast insert, search, and delete by Patient ID — O(log n) average |
| **FIFO Queue** | Emergency Waiting Line | First patient to arrive is treated first — fair ordering |
| **LIFO Stack** | Treatment History | Most recent treatment is reviewed first |
| **Singly Linked List** | Per-Patient Visit History | Each patient carries their own chronological visit history |

---

## Project Structure

```
MiniHospitalEmergencySystem/
├── src/
│   ├── Main.java               # Entry point — interactive console menu
│   │
│   ├── Patient.java            # Patient data model (Encapsulation)
│   ├── PatientBST.java         # Binary Search Tree (insert, search, delete, in-order)
│   ├── PatientNode.java        # BST node (left, right pointers)
│   │
│   ├── EmergencyQueue.java     # FIFO Queue (enqueue, dequeue, display)
│   ├── QueueNode.java          # Queue node (next pointer)
│   │
│   ├── TreatmentStack.java     # LIFO Stack (push, pop, display)
│   ├── TreatmentNode.java      # Stack node (next pointer)
│   ├── Treatment.java          # Treatment data model
│   │
│   ├── VisitLinkedList.java    # Singly Linked List (add, remove, search, display)
│   ├── VisitNode.java          # Linked list node (next pointer)
│   └── Visit.java              # Visit data model
│
└── README.md
```

---

## Features

### Patient Records (BST)
- ✅ Register a new patient with ID, name, age, contact, and medical condition
- ✅ Duplicate Patient ID rejected immediately (before prompting for other details)
- ✅ Search patient by ID — also shows their queue position and total visits
- ✅ Delete patient — automatically removes them from the emergency queue if waiting
- ✅ Display all patients sorted in ascending Patient ID order

### Emergency Queue
- ✅ Add registered patients to the waiting queue
- ✅ Prevents adding the same patient twice (shows their current queue position)
- ✅ Treat (dequeue) the next patient — option to record treatment immediately
- ✅ Display full waiting list with positions

### Treatment History (Stack)
- ✅ Record completed treatment details (doctor, diagnosis, treatment, date)
- ✅ Treatment is automatically synced to the patient's personal visit history
- ✅ Remove the most recent treatment record
- ✅ Display full treatment history (most recent first)

### Patient Visit History (Linked List)
- ✅ Each patient has their own independent visit history
- ✅ Add, remove, search, and display visits by Visit ID
- ✅ Duplicate Visit ID rejected per patient
- ✅ Visits auto-recorded when treatment is added via Option 6 or 8

### Input Handling
- ✅ Invalid non-numeric input gracefully rejected with re-prompt
- ✅ Blank Enter keypresses ignored silently (no error spam)
- ✅ EOF / Ctrl+Z handled gracefully — exits cleanly without crashing

---

## How to Run

### Prerequisites
- Java JDK 8 or later installed
- Any terminal (Command Prompt, PowerShell, or VS Code Terminal)

### Steps

**1. Navigate to the source folder:**
```bash
cd "path/to/MiniHospitalEmergencySystem/src"
```

**2. Compile all Java files:**
```bash
javac *.java
```

**3. Run the program:**
```bash
java Main
```

---

## Menu Options

```
=========================================
 MINI HOSPITAL EMERGENCY MANAGEMENT SYSTEM
=========================================
  PATIENT RECORDS
    1.  Register New Patient
    2.  Search Patient
    3.  Delete Patient
    4.  Display All Patients
-----------------------------------------
  EMERGENCY QUEUE
    5.  Add Patient to Emergency Queue
    6.  Treat Next Patient
    7.  Display Emergency Queue
-----------------------------------------
  TREATMENT HISTORY
    8.  Add Completed Treatment
    9.  Remove Latest Treatment
    10. Display Treatment History
-----------------------------------------
  PATIENT VISIT HISTORY
    11. Add Patient Visit
    12. Remove Patient Visit
    13. Search Patient Visit
    14. Display Patient Visit History
-----------------------------------------
    15. Exit
=========================================
```

---

## Sample Workflow

```
Step 1  → [Option 1]  Register Patient: ID=101, Name=Sara, Age=28, Condition=Fever
Step 2  → [Option 1]  Register Patient: ID=102, Name=Ahmed, Age=40, Condition=Asthma
Step 3  → [Option 4]  Display All Patients → shows Sara (101) and Ahmed (102) sorted by ID
Step 4  → [Option 5]  Add Patient 101 (Sara) to Emergency Queue
Step 5  → [Option 5]  Add Patient 102 (Ahmed) to Emergency Queue
Step 6  → [Option 7]  Display Queue → Sara is Position 1, Ahmed is Position 2
Step 7  → [Option 6]  Treat Next Patient → Sara is dequeued, option to record treatment
              → Enter Doctor, Diagnosis, Treatment, Date
              → Treatment saved to history + auto-added to Sara's visit list
Step 8  → [Option 10] Display Treatment History → shows Sara's treatment record
Step 9  → [Option 14] Display Sara's Visit History → shows auto-recorded visit
Step 10 → [Option 15] Exit
```

---

## OOP Concepts Applied

| Concept | Where Applied |
|---|---|
| **Encapsulation** | All fields in `Patient`, `Visit`, `Treatment` are `private` with public getters/setters |
| **Abstraction** | Each data structure hides its internal node logic behind clean public methods |
| **Single Responsibility** | Each class has one clear job (e.g., `PatientBST` only manages the BST) |
| **Separation of Concerns** | `Main.java` handles UI/input; data classes handle logic; node classes handle structure |

---

## Author

**BAIT | SLTC**
Module: Data Structures and Algorithms (DSA)
Project: Mini Hospital Emergency Management System
Language: Java (Console Application)

---

> *All data structures in this project are implemented manually from scratch without using Java's built-in collection classes.*
