package model;

public class Teacher extends Person
{

    private String teacherId;
    private String course;
    private double salary;

    public Teacher(String teacherId, String name, String course, int age, double salary)
    {
        super(name, age);
        this.teacherId = teacherId;
        this.course = course;
        this.salary = salary;
    }

    public String getTeacherId()
    {
        return teacherId;
    }

    public void setTeacherId(String teacherId)
    {
        this.teacherId = teacherId;
    }

    public String getCourse()
    {
        return course;
    }

    public void setCourse(String course)
    {
        this.course = course;
    }

    public double getSalary()
    {
        return salary;
    }

    public void setSalary(double salary)
    {
        this.salary = salary;
    }

    @Override
    public void displayDetails()
    {
        System.out.println("-----------------------------------------------");
        System.out.println("Teacher ID : " + teacherId);
        System.out.println("Name       : " + getName());
        System.out.println("Course     : " + course);
        System.out.println("Age        : " + getAge());
        System.out.printf("Salary     : %.2f%n", salary);
        System.out.println("-----------------------------------------------");
    }

    @Override
    public String toString()
    {
        return teacherId + "|" + getName() + "|" + course + "|" + getAge() + "|" + salary;
    }
}
