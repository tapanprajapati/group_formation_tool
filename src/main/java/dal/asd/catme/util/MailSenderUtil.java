package dal.asd.catme.util;

public class MailSenderUtil
{

    public static String HOST = "smtp.gmail.com";
    public static String USERNAME = "adv.sdc.g.16@gmail.com";
    public static String PASSWORD = "SDCGROUP16";
    public static int PORT = 587;
    public static boolean STARTTLS_ENABLE = true;

    public static final String TEMPLATE_USERNAME = "{username}";
    public static final String TEMPLATE_BANNERID = "{bannerid}";
    public static final String TEMPLATE_PASSWORD = "{password}";
    public static final String TEMPLATE_COURSE = "{course}";
    public static final String TEMPLATE_RESETLINK = "{resetlink}";

    public static final String RESETLINK = "localhost:8080/reset-password?token=";

    public static final String PATH_TO_NEW_STUDENT_TEMPLATE = "src/main/resources/templates/new_student_template.html";
    public static final String NEW_STUDENT_EMAIL_SUBJECT = "Registration of new course in CATME";

    public static final String PATH_TO_FORGOT_PASSWORD_TEMPLATE = "src/main/resources/templates/forgot_password_template.html";
    public static final String FORGOT_PASSWORD_EMAIL_SUBJECT = "Reset Your Password";

    public static final int TOKEN_LENGTH = 8;
}