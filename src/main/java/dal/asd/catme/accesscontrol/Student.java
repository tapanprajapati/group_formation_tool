package dal.asd.catme.accesscontrol;

import java.util.List;

import dal.asd.catme.courses.Course;

public class Student extends User
{
    public List<Course> enrolledCourses;
    
	public Student()
    {
    }

    public Student(String bannerId, String lastName, String firstName, String email, String password, List<Role> role, List<Course> enrolledCourses)
    {
        super(bannerId, lastName, firstName, email, password, role);
        this.enrolledCourses = enrolledCourses;
    }

    public Student(String bannerId, String lastName, String firstName, String email, String password, List<Role> role)
    {
        super(bannerId, lastName, firstName, email, password, role);
    }

    public Student(String bannerId, String lastName, String firstName, String email, String password)
    {
        super(bannerId, lastName, firstName, email, password);
    }

    public Student(String bannerId, String lastName, String firstName, String email)
    {
        super(bannerId, lastName, firstName, email);
    }

	public List<Course> getEnrolledCourses() {
		return enrolledCourses;
	}

	public void setEnrolledCourses(List<Course> enrolledCourses) {
		this.enrolledCourses = enrolledCourses;
	}

    @Override
	public String getBannerId() {
		return super.getBannerId();
	}

	@Override
	public void setBannerId(String bannerId) {
		super.setBannerId(bannerId);
	}

	@Override
	public String getLastName() {
		return super.getLastName();
	}

	@Override
	public void setLastName(String lastName) {
		super.setLastName(lastName);
	}

	@Override
	public String getFirstName() {
		return super.getFirstName();
	}

	@Override
	public void setFirstName(String firstName) {
		super.setFirstName(firstName);
	}

	@Override
	public String getEmail() {
		return super.getEmail();
	}

	@Override
	public void setEmail(String email) {
		super.setEmail(email);
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public void setPassword(String password) {
		super.setPassword(password);
	}

	@Override
	public List<Role> getRole() {
		return super.getRole();
	}

	@Override
	public void setRole(List<Role> role) {
		super.setRole(role);
	}

	@Override
	public String toString() {
		return super.toString();
	}
}