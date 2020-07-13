package dal.asd.catme.courses;

import dal.asd.catme.accesscontrol.*;
import dal.asd.catme.config.CatmeSecurityConfig;
import dal.asd.catme.config.SystemConfig;
import dal.asd.catme.exception.CatmeException;
import dal.asd.catme.exception.InvalidFileFormatException;
import dal.asd.catme.studentlistimport.CSVParserAbstractFactoryImpl;
import dal.asd.catme.studentlistimport.ICSVParser;
import dal.asd.catme.studentlistimport.ICSVParserAbstractFactory;
import dal.asd.catme.studentlistimport.ICSVReader;
import dal.asd.catme.util.CatmeUtil;
import dal.asd.catme.util.RandomTokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static dal.asd.catme.accesscontrol.MailSenderUtil.TOKEN_LENGTH;

public class CourseManageController
{
	ICSVParserAbstractFactory icsvParserAbstractFactory = CSVParserAbstractFactoryImpl.instance();
	ICourseAbstractFactory courseAbstractFactory = CourseAbstractFactoryImpl.instance();
	IAccessControlAbstractFactory accessControlAbstractFactory = AccessControlAbstractFactoryImpl.instance();

	ICourseModelAbstractFactory modelAbstractFactory = CourseModelAbstractFactoryImpl.instance();
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);


    @RequestMapping("/manageCourse")
    public ModelAndView manageCourse(@RequestParam(name = "courseId") String courseId)
    {
        log.info("Manage Course " + courseId);
        ModelAndView uploadPage = new ModelAndView();

        try
        {
            CatmeSecurityConfig catmeSecurityConfig = SystemConfig.instance().getCatmeServiceConfig();
            List<String> roles = catmeSecurityConfig.fetchRolesHomePage();

            if (roles.contains(CatmeUtil.INSTRUCTOR_ROLE) || roles.contains(CatmeUtil.TA_ROLE))
            {
                uploadPage.setViewName(CatmeUtil.MANAGE_COURSE_PAGE);

                uploadPage.addObject("courseId", courseId);
                ICourseService courseService = courseAbstractFactory.getCourseService();
                uploadPage.addObject("studentList", courseService.getEnrolledStudents(courseId));
            } else
            {
                uploadPage.setViewName(CatmeUtil.LOGIN_PAGE);
                return uploadPage;
            }

        } catch (CatmeException e)
        {
            e.printStackTrace();
        }

        return uploadPage;
    }

    @PostMapping("/manageCourse")
    public ModelAndView uploadFile(@RequestParam("student-list-csv") MultipartFile file, @RequestParam("courseId") String courseId)
    {
        ModelAndView model = new ModelAndView();
		model.setViewName(CatmeUtil.MANAGE_COURSE_PAGE);


		try
        {
			ICSVReader icsvReader = icsvParserAbstractFactory.getCSVReader(file.getInputStream());
			ICSVParser icsvParser = icsvParserAbstractFactory.getCSVParser();
			IUserService userService = accessControlAbstractFactory.getUserService();
			IEnrollStudentService enrollStudentService = courseAbstractFactory.getEnrollmentService();
			IMailSenderService mailSenderService = accessControlAbstractFactory.getMailSenderService();

			icsvReader.validateFile(file);
			ICourse c = modelAbstractFactory.createCourse();
			c.setCourseId(courseId);

			ArrayList<IUser> students = icsvParser.getStudentsFromFile(icsvReader);

			if(students==null)
			{
				model.addObject("message", "Error Enrolling Students");
				return model;
			}

            for (IUser s : students)
            {
                s.setPassword(RandomTokenGenerator.generateRandomPassword(TOKEN_LENGTH));
                if (userService.addUser(s).equals(CatmeUtil.USER_CREATED))
                {
                    mailSenderService.sendCredentialsToStudent(s, c);
                }
            }

            if (enrollStudentService.enrollStudentsIntoCourse(students, c))
            {
                model.addObject("message", "Students Enrolled");
            } else
            {
                model.addObject("message", "Error Enrolling Students");
            }
        } catch (InvalidFileFormatException e)
        {
            model.addObject("message", e.getMessage());
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (MessagingException e)
        {
            model.addObject("message", e.getMessage());
        }
        return model;
    }
}
