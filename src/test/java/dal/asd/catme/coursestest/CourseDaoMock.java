package dal.asd.catme.coursestest;

import dal.asd.catme.accesscontrol.Student;
import dal.asd.catme.accesscontrol.User;
import dal.asd.catme.courses.Course;
import dal.asd.catme.courses.ICourseDao;
import dal.asd.catme.exception.CatmeException;
import dal.asd.catme.util.CatmeUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoMock implements ICourseDao
{

    ArrayList<Course> courses;

    public CourseDaoMock()
    {

    }

    public CourseDaoMock(ArrayList<Course> courses)
    {
        this.courses = courses;
    }

    List<Course> listOfCourses;

    public CourseDaoMock(List<Course> listOfCourses)
    {
        this.listOfCourses = listOfCourses;
    }

    @Override
    public List<Course> getCourses(String role) throws CatmeException
    {
        if (role.equals(CatmeUtil.GUEST_ROLE))
        {
            //listOfCourses=formCourses();	
        } else
        {
            listOfCourses = null;
        }
        return listOfCourses;
    }

    @Override
    public Course displayCourseById(String courseId) throws CatmeException
    {
        Course course = new Course();
        for (Course c : listOfCourses)
        {
            if (c.getCourseId().equals(courseId))
            {
                course = c;
            }
        }
        return course;
    }

    @Override
    public String findRoleByCourse(User user, String courseId) throws CatmeException
    {
        String role = "";

        if (user.getBannerId().equals("B00835822") && courseId.equals("5409"))
        {
            role = CatmeUtil.STUDENT_ROLE;
        }
        if (user.getBannerId().equals("B00835822") && courseId.equals("5308"))
        {
            role = CatmeUtil.TA_ROLE;
        }
        if (user.getBannerId().equals("B00835822") && courseId.equals("5306"))
        {
            role = CatmeUtil.GUEST_ROLE;
        }
        return role;
    }

    @Override
    public List<Student> getRegisteredStudents(String courseId)
    {
        return null;
    }

    /*
     * @Override public int checkCourseRegistration(String bannerId, int courseId,
     * Connection con) { return 0; }
     */


    @Override
    public int checkCourseRegistration(String bannerId, String courseId, Connection con)
    {
        // TODO Auto-generated method stub
        for (Course c : courses)
        {
            if (c.getCourseId().equalsIgnoreCase(courseId))
            {
                for (User u : c.getStudents())
                {
                    if (u.getBannerId().equalsIgnoreCase(bannerId))
                    {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public int checkCourseExists(String courseId, Connection con)
    {
        // TODO Auto-generated method stub
        for (Course c : courses)
        {
            if (c.getCourseId().equalsIgnoreCase(courseId))
                return 1;
        }
        return 0;
    }


}