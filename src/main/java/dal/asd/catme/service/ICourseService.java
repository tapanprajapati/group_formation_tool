package dal.asd.catme.service;

import dal.asd.catme.beans.Course;
import dal.asd.catme.beans.Student;
import dal.asd.catme.beans.User;
import dal.asd.catme.exception.CatmeException;

import java.util.List;

public interface ICourseService
{
    public List<Course> getCourses(String role) throws CatmeException;

    public Course displayCourseById(String courseId) throws CatmeException;

    public String findRoleByCourse(User user, String courseId) throws CatmeException;

    public List<Student> getEnrolledStudents(String courseId);
}
