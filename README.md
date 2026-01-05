# 📅 Appointment Scheduler (Java + JDBC)

A **console-based Java application** that allows users to **schedule, view, update, search, and delete appointments** using a **MySQL database**.  
This project demonstrates **core Java**, **JDBC connectivity**, and **database-driven application design**.

---

## 🔗 Project Repository
👉 [[github.com/Vishrudha18/AppointmentScheduler](https://github.com/Vishrudha18/Console-Based-Project/tree/main/AppointmentScheduler)](https://github.com/Vishrudha18/Console-Based-Project/blob/main/src/AppointmentScheduler.java)

---

## 🚀 Features
- ➕ Add new appointments
- 🔍 Search appointments by client name or date
- ✏ Update existing appointment details
- ❌ Delete appointments
- 📋 View all scheduled appointments
- 🗄 Persistent storage using MySQL database

---

## 🛠 Technologies Used
- **Java (JDK 8+)**
- **JDBC (MySQL Connector)**
- **MySQL Database**
- **VS Code / IntelliJ / Eclipse**

---

## 📂 Project Structure
AppointmentScheduler/
│
├── src/
│ └── AppointmentScheduler.java # Main Java source file
│
├── bin/
│ └── AppointmentScheduler.class # Compiled class file
│
├── lib/
│ └── mysql-connector-j-9.2.0.jar # MySQL JDBC Driver
│
└── README.md

---

## ⚙ Prerequisites
Before running the project, ensure you have:

☑ Java JDK installed  
☑ MySQL installed and running
☑ MySQL Connector JAR placed inside lib/

---

## 🗄 Database Setup
### 1️⃣ Create Database
CREATE DATABASE appointment_system;
USE appointment_system;

### 2️⃣ Create Table
CREATE TABLE appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_name VARCHAR(100),
    appointment_date DATE,
    appointment_time TIME,
    purpose VARCHAR(255)
);

---

## ▶ How to Compile and Run
### 🔹 Compile
javac -cp ".;lib/mysql-connector-j-9.2.0.jar" -d bin src/AppointmentScheduler.java

### 🔹 Run
java -cp ".;lib/mysql-connector-j-9.2.0.jar;bin" AppointmentScheduler

---

## Output



---

## 🎯 Learning Outcomes

- Understanding JDBC workflow
- Connecting Java applications to MySQL
- Executing SQL queries from Java
- Structuring a console-based database application

