package com.profitsoft.first_task.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Person {
    private LocalDateTime dateTime;
    private String firstName;
    private String lastName;
    private ViolationType violationType;
    private double fineAmount;

    public Person() {
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ViolationType getViolationType() {
        return violationType;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setViolationType(ViolationType violationType) {
        this.violationType = violationType;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return firstName.equals(person.firstName) && lastName.equals(person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return "Person{" +
                "dateTime=" + dateTime +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", violationType=" + violationType +
                ", fineAmount=" + fineAmount +
                '}';
    }
}
