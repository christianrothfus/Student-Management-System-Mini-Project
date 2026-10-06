package service;

import model.Teacher;

import java.io.*;
import java.util.*;

public class TeacherManager {

    private final ArrayList<Teacher> teachers = new ArrayList<>();

    // Teacher ID -> Teacher
    private final HashMap<String, Teacher> teacherMap = new HashMap<>();

    private final Scanner sc;

    private final String FILE_NAME = "data/teachers.txt";

    public TeacherManager(Scanner sc) {
        this.sc = sc;
        loadFromFile();
    }

    // ================= ADD TEACHER =================

    public void addTeacher() {

        System.out.println("\n========== ADD TEACHER ==========");

        String id = readNonEmptyString("Enter Teacher ID: ");

        if (teacherMap.containsKey(id)) {
            System.out.println("Error: Teacher ID already exists!");
            return;
        }

        String name = readNonEmptyString("Enter Name: ");

        String course = readNonEmptyString("Enter Course: ");

        int age = readInt("Enter Age: ");

        if (age < 18 || age > 100) {
            System.out.println("Age must be between 18 and 100.");
            return;
        }

        double salary = readDouble("Enter Salary: ");

        if (salary < 0) {
            System.out.println("Salary cannot be negative.");
            return;
        }

        Teacher teacher = new Teacher(
                id,
                name,
                course,
                age,
                salary
        );

        teachers.add(teacher);
        teacherMap.put(id, teacher);

        System.out.println("\nTeacher added successfully!");

        saveToFile();
    }

    // ================= VIEW TEACHERS =================

    public void viewTeachers() {

        System.out.println("\n========== ALL TEACHERS ==========");

        if (teachers.isEmpty()) {
            System.out.println("No teacher records found.");
            return;
        }

        for (Teacher teacher : teachers) {
            teacher.displayDetails();
        }

        System.out.println("Total Teachers: " + teachers.size());
    }

    // ================= SEARCH BY ID =================

    public void searchById() {

        System.out.println("\n========== SEARCH TEACHER BY ID ==========");

        String id = readNonEmptyString("Enter Teacher ID: ");

        Teacher teacher = teacherMap.get(id);

        if (teacher == null) {
            System.out.println("Teacher with ID " + id + " not found.");
            return;
        }

        teacher.displayDetails();
    }

    // ================= SEARCH BY COURSE =================

    public void searchByCourse() {

        System.out.println("\n========== SEARCH TEACHERS BY COURSE ==========");

        String course = readNonEmptyString("Enter Course: ");

        boolean found = false;

        for (Teacher teacher : teachers) {

            if (teacher.getCourse().equalsIgnoreCase(course)) {

                teacher.displayDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No teacher found for this course.");
        }
    }

    // ================= UPDATE =================

    public void updateTeacher() {

        System.out.println("\n========== UPDATE TEACHER ==========");

        String id = readNonEmptyString("Enter Teacher ID: ");

        Teacher teacher = teacherMap.get(id);

        if (teacher == null) {
            System.out.println("Teacher not found.");
            return;
        }

        System.out.println("\nCurrent Details:");
        teacher.displayDetails();

        String name = readNonEmptyString("Enter New Name: ");

        String course = readNonEmptyString("Enter New Course: ");

        int age = readInt("Enter New Age: ");

        if (age < 18 || age > 100) {
            System.out.println("Invalid age.");
            return;
        }

        double salary = readDouble("Enter New Salary: ");

        if (salary < 0) {
            System.out.println("Invalid salary.");
            return;
        }

        teacher.setName(name);
        teacher.setCourse(course);
        teacher.setAge(age);
        teacher.setSalary(salary);

        System.out.println("\nTeacher updated successfully!");

        saveToFile();
    }

    // ================= DELETE =================

    public void deleteTeacher() {

        System.out.println("\n========== DELETE TEACHER ==========");

        String id = readNonEmptyString("Enter Teacher ID: ");

        Teacher teacher = teacherMap.get(id);

        if (teacher == null) {
            System.out.println("Teacher not found.");
            return;
        }

        teacher.displayDetails();

        System.out.print("Are you sure you want to delete? (Y/N): ");

        String choice = sc.nextLine();

        if (choice.equalsIgnoreCase("Y")) {

            teachers.remove(teacher);
            teacherMap.remove(id);

            System.out.println("Teacher deleted successfully!");

            saveToFile();

        } else {
            System.out.println("Delete operation cancelled.");
        }
    }

    // ================= SAVE FILE =================

    public synchronized void saveToFile() {

        try {

            File directory = new File("data");

            if (!directory.exists()) {
                directory.mkdirs();
            }

            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(FILE_NAME)
                    );

            for (Teacher teacher : teachers) {
                writer.write(teacher.toString());
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {

            System.out.println(
                    "Error while saving teacher data: "
                            + e.getMessage()
            );
        }
    }

    // ================= LOAD FILE =================

    private void loadFromFile() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(file)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\\|");

                if (data.length == 5) {

                    String id = data[0];
                    String name = data[1];
                    String course = data[2];
                    int age = Integer.parseInt(data[3]);
                    double salary = Double.parseDouble(data[4]);

                    Teacher teacher =
                            new Teacher(
                                    id,
                                    name,
                                    course,
                                    age,
                                    salary
                            );

                    teachers.add(teacher);
                    teacherMap.put(id, teacher);
                }
            }

            reader.close();

            System.out.println(
                    teachers.size()
                            + " teacher records loaded."
            );

        } catch (IOException | NumberFormatException e) {

            System.out.println(
                    "Error while loading teacher data."
            );
        }
    }

    // ================= INPUT METHODS =================

    private String readNonEmptyString(String message) {

        while (true) {

            System.out.print(message);

            String input = sc.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println(
                    "Input cannot be empty. Try again."
            );
        }
    }

    private int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        sc.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }

    private double readDouble(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Double.parseDouble(
                        sc.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }
}
