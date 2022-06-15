package sample.model;

public class Student {
    private int id;
    private String name;
    private String surname;
    private String faculty;
    private String specialty;
    private String group_number;

    public Student(int id, String name, String surname, String faculty, String specialty, String group_number) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.faculty = faculty;
        this.specialty = specialty;
        this.group_number = group_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getGroup_number() {
        return group_number;
    }

    public void setGroup_number(String group_number) {
        this.group_number = group_number;
    }
}
