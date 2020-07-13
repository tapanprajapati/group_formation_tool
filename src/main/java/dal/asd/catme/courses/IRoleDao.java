
package dal.asd.catme.courses;


import java.sql.Connection;

public interface IRoleDao
{
    public int assignRole(String bannerId, int roleId, Connection con);

    public int addInstructor(String courseId, int userRoleId, Connection con);

    public String assignTa(IEnrollment user, Connection con);

    public int checkCourseInstructor(String bannerId, String courseId, Connection con);

    public int checkUserRole(String bannerId, int roleId, Connection con);

    public int getUserRoleId(String bannerId, int roleId, Connection con);

}