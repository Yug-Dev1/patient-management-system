# 🏥 Patient Management System

A **Java Desktop Application** built using **Apache NetBeans (Swing + MySQL)** to efficiently manage hospital workflows including patient records, appointments, billing, and prescriptions.

This system follows a **Role-Based Access Control (RBAC)** architecture with dedicated modules for **Admin, Receptionist, and Doctor**, ensuring organized and secure operations.

---

## 🚀 Key Features

### 🔐 Role-Based Login (RBAC)
- Secure login system
- Separate dashboards for:
  - Admin
  - Receptionist
  - Doctor

---

## 🧩 System Architecture

- Centralized **MySQL database (JDBC)**
- Modular design (Admin / Doctor / Reception)
- Email automation + dynamic search
- Scalable structure for future upgrades

---

## 👨‍💼 Admin Module

- Manage patients (view, history, details)
- Staff management (add, roles, activation)
- Inventory management (stock, alerts)
- Analytics dashboard (charts, reports, insights)

---

## 🧾 Reception Module

- Patient registration with dynamic search
- Appointment scheduling & cancellation
- Billing system:
  - Generate invoices
  - Paid / unpaid tracking
  - Date filtering
- Inventory access (stock updates)

---

## 👨‍⚕️ Doctor Module

- View patient profiles & history
- Create and manage prescriptions
- Track appointments (today / status)
- Access previous medical records

---

## 📊 Analytics Dashboard

- Appointment source analysis (online, walk-in, referral, ads)
- Appointment type distribution
- Time-based filtering
- Insights for decision-making

---

## 🧍 Patient Management

- Store complete patient details:
  - Name, DOB, Blood Group
  - Contact info
  - Allergies & notes
- Track visit history & appointments

---

## 💊 Prescription System

- Add medicines with dosage & duration
- Maintain medical history
- Prevent invalid prescription creation
- View old records easily

---

## 💳 Billing System

- Generate invoices
- Payment tracking (cash, UPI, card)
- Revenue insights
- Pending bill tracking

---

## 📄 PDF & Email Integration

- Generate PDF invoices
- Email invoices to patients
- Automated document handling

---

## 📅 Appointment Management

- Book / cancel appointments
- Track status:
  - Scheduled
  - Completed
  - Cancelled
- View daily schedules

---

## 🛠️ Tech Stack

- **Language:** Java  
- **GUI:** Java Swing  
- **IDE:** Apache NetBeans  
- **Database:** MySQL (JDBC)  
- **Build Tool:** Ant  

---

## 📁 Project Structure

Patient-Management-System/
│── src/
│   ├── Admin/
│   ├── Doctor/
│   ├── Reception/
│   ├── db/
│   └── patient/
│
│── nbproject/
│── .gitignore
│── build.xml
│── manifest.mf

---

## ⚙️ How to Run

### 1. Clone Repository

git clone https://github.com/Yug-Dev1/patient-management-system.git

### 2. EMAIL credentials

For security reasons, credentials are not stored in the repository.
Locate the email configuration in the code and replace:

String email = "YOUR_EMAIL";
String password = "YOUR_APP_PASSWORD";

[Add your own gmail n APP_PASSWORD (not normal)]

### 3. Open in NetBeans
- Open Apache NetBeans
- Click **Open Project**
- Select the project folder

### 4. Configure Database
Edit:
src/db/DBconnection.java

Add your MySQL credentials.

### 5. Run
Press **F6** or click **Run Project**

---

## 🎯 Future Improvements

- Web-based version (Spring Boot / React)
- Online appointment booking
- Authentication with encryption
- Cloud deployment
- Mobile app integration

---

## 👨‍💻 Author

**Yug Dev**  
https://github.com/Yug-Dev1  

---

## 📌 Note

This project is built for **learning and demonstration purposes** and showcases real-world hospital management workflows using Java.
![IMG_1013](https://github.com/user-attachments/assets/16226005-cbdf-486d-a691-180631a13e3d)
![IMG_1015](https://github.com/user-attachments/assets/e547a168-c802-4aab-89c6-30710f7e597f)
![IMG_1012](https://github.com/user-attachments/assets/baf83be1-1547-49a1-b95c-d7f9f751b177)
![IMG_1009](https://github.com/user-attachments/assets/8a748f59-5df6-48ee-9bca-d69801f5040a)
![IMG_1008](https://github.com/user-attachments/assets/e34da789-f202-473f-9801-6bdaf9e1c95e)
![IMG_1007](https://github.com/user-attachments/assets/8bf84d8d-41f9-40c1-ae06-86af5475f4cb)
