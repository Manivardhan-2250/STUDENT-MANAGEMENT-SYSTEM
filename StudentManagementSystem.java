import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StudentManagementSystem {
    private static class Student {
        String id;
        String name;
        int age;
        Map<String, Double> grades;
        List<Boolean> attendance;

        Student(String id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.grades = new HashMap<>();
            this.attendance = new ArrayList<>();
        }
    }

    private static List<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Record Attendance");
            System.out.println("3. Add Grade");
            System.out.println("4. View Student Details");
            System.out.println("5. View All Students");
            System.out.println("6. Exit");
            System.out.print("Choose an option (1-6): ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        addStudent();
                        break;
                    case "2":
                        recordAttendance();
                        break;
                    case "3":
                        addGrade();
                        break;
                    case "4":
                        viewStudentDetails();
                        break;
                    case "5":
                        viewAllStudents();
                        break;
                    case "6":
                        System.out.println("Exiting. Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option. Please choose 1-6.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addStudent() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        if (findStudent(id) != null) {
            System.out.println("Error: Student ID already exists.");
            return;
        }

        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student age: ");
        int age;
        try {
            age = Integer.parseInt(scanner.nextLine());
            if (age <= 0) {
                System.out.println("Error: Age must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid age format.");
            return;
        }

        students.add(new Student(id, name, age));
        System.out.println("Student added successfully.");
    }

    private static void recordAttendance() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        Student student = findStudent(id);
        if (student == null) {
            System.out.println("Error: Student not found.");
            return;
        }

        System.out.print("Is student present? (yes/no): ");
        String present = scanner.nextLine().toLowerCase();
        boolean isPresent = present.equals("yes");
        student.attendance.add(isPresent);
        System.out.println("Attendance recorded.");
    }

    private static void addGrade() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        Student student = findStudent(id);
        if (student == null) {
            System.out.println("Error: Student not found.");
            return;
        }

        System.out.print("Enter subject: ");
        String subject = scanner.nextLine();
        System.out.print("Enter grade (0-100): ");
        try {
            double grade = Double.parseDouble(scanner.nextLine());
            if (grade < 0 || grade > 100) {
                System.out.println("Error: Grade must be between 0 and 100.");
                return;
            }
            student.grades.put(subject, grade);
            System.out.println("Grade added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid grade format.");
        }
    }

    private static void viewStudentDetails() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        Student student = findStudent(id);
        if (student == null) {
            System.out.println("Error: Student not found.");
            return;
        }

        System.out.println("\nStudent Details:");
        System.out.println("ID: " + student.id);
        System.out.println("Name: " + student.name);
        System.out.println("Age: " + student.age);
        System.out.println("Grades: " + (student.grades.isEmpty() ? "No grades recorded" : ""));
        for (Map.Entry<String, Double> entry : student.grades.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Attendance: " + (student.attendance.isEmpty() ? "No attendance recorded" : ""));
        int presentCount = 0;
        for (Boolean present : student.attendance) {
            System.out.println("  " + (present ? "Present" : "Absent"));
            if (present) presentCount++;
        }
        if (!student.attendance.isEmpty()) {
            double attendanceRate = (presentCount * 100.0) / student.attendance.size();
            System.out.printf("Attendance Rate: %.2f%%\n", attendanceRate);
        }
    }

    private static void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }

        System.out.println("\nAll Students:");
        for (Student student : students) {
            System.out.println("ID: " + student.id + ", Name: " + student.name + ", Age: " + student.age);
        }
    }

    private static Student findStudent(String id) {
        for (Student student : students) {
            if (student.id.equals(id)) {
                return student;
            }
        }
        return null;
    }
}