package pl.edu.agh.to.school.grade;

import pl.edu.agh.to.school.course.Course;

import java.util.Objects;

public final class Grade {
    private final Course course;
    private final double value;

    public Grade(Course course, double value) {
        this.course = course;
        this.value = value;
    }

    public Course course() {
        return course;
    }

    public double value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Grade) obj;
        return Objects.equals(this.course, that.course) &&
                Double.doubleToLongBits(this.value) == Double.doubleToLongBits(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, value);
    }

    @Override
    public String toString() {
        return "Grade[" +
                "course=" + course + ", " +
                "value=" + value + ']';
    }
}
