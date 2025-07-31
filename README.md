# Personal Wellness Tracker

This Java desktop application was developed as my final project for the CIS171 (Java Programming) course. The goal was to apply core Java and Object-Oriented Programming principles to build a functional, real-world application that helps users log and track their daily physical and mental well-being.

<p align="center">
  <img src="https://raw.githubusercontent.com/chaudharyabdul/CIS171/main/assets/Wellness%20Tracker.png" alt="Wellness Tracker Application" width="700"/>
</p>

---

## ✨ Key Features

* **Dual-Entry System:** Log daily wellness through distinct Physical (exercise, sleep) and Mental (mood, stress) entries.
* **Data-Driven Analytics:** Get instant feedback on your progress with an analytics engine that calculates average scores, standard deviation, and basic sentiment analysis on your journal notes.
* **Personalized Tips:** The application generates tips based on your recent wellness score trends, encouraging consistency and positive habits.
* **Dynamic Data Visualization:** A custom-drawn graph panel provides a clear visual representation of your wellness score over time.
* **User Profiles & Data Persistence:** Create a user profile and save your progress. The application automatically saves your data to a local file and attempts to load it on startup.
* **Robust Unit Testing:** The core business logic is validated through a comprehensive test suite built with JUnit, ensuring calculations are accurate and reliable.

---

## 🛠️ Tech Stack

* **Language:** Java
* **GUI:** Java Swing
* **Testing:** JUnit 5

---

## 🧠 Object-Oriented Design Highlights

This project was a fantastic opportunity to implement key OOP concepts:

* **Inheritance:** An abstract `WellnessEntry` class serves as the base, with `PhysicalEntry` and `MentalEntry` inheriting from it to share common properties like date and notes while having their own unique attributes.
* **Polymorphism:** The `calculateImpactScore()` method is defined as abstract in the parent class and implemented uniquely in each subclass, allowing for different scoring logic depending on the entry type.
* **Encapsulation:** All data models (POJOs) use private member variables with public getters to protect the integrity of the data.
* **Composition:** The `User` class has a "has-a" relationship with `WellnessEntry`, containing an `ArrayList` of entries to model the user's wellness history.

---

## 🚀 Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

* Java Development Kit (JDK) 11 or higher.

### Installation & Running

1.  Clone the repository:
    ```sh
    git clone https://github.com/chaudharyabdul/CIS171.git
    ```
2.  Navigate to the project directory:
    ```sh
    cd CIS171
    ```
3.  Compile the Java source files from inside the `src` directory:
    ```sh
    javac wellness/*.java
    ```
4.  Run the application (the main method is in `WellnessManager`):
    ```sh
    java wellness.WellnessManager
    ```

---

## Usage

1.  On first launch, create a user by entering your name and age and clicking "Create User". On subsequent launches, the app will try to auto-load your previous data.
2.  To add an entry, fill in the fields for either a "Physical Entry" or "Mental Entry".
3.  Add an optional "Journal Note" at the bottom.
4.  Click the corresponding "Add..." button to log the entry.
5.  Click "Refresh Analytics" to see the latest stats and update the trend graph.
6.  Click "Save User" to persist your data to a file.

---

Thank you for checking out my project! This was a fantastic learning experience, and I'm proud of how it turned out. Feel free to connect with me on LinkedIn if you have any questions or feedback.
