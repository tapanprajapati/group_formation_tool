package dal.asd.catme.password;

import dal.asd.catme.accesscontrol.*;
import dal.asd.catme.exception.CatmeException;
import dal.asd.catme.util.CatmeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;

import static dal.asd.catme.util.CatmeUtil.ERROR_PAGE;
import static dal.asd.catme.util.CatmeUtil.RESET_PASSWORD_PAGE;

@Controller
@RequestMapping("/")
public class ForgotPasswordController
{
    IPasswordAbstractFactory passwordAbstractFactory = PasswordAbstractFactoryImpl.instance();
    IAccessControlAbstractFactory accessControlAbstractFactory = AccessControlAbstractFactoryImpl.instance();

    String bannerid;

    IAccessControlModelAbstractFactory accessControlModelAbstractFactory = AccessControlModelAbstractFactoryImpl.instance();

    @RequestMapping("forgotPassword")
    public String forgotPassword()
    {
        return CatmeUtil.FORGOT_PASSWORD_PAGE;
    }

    @PostMapping("forgotPassword")
    public String resetPassword(@RequestParam("bannerid") String bannerid, Model model)
    {
        IPasswordResetService passwordResetService = passwordAbstractFactory.getPasswordResetService();
        IMailSenderService mailSenderService = accessControlAbstractFactory.getMailSenderService();

        IUser u = passwordResetService.generateResetLink(bannerid);

        if (u == null)
        {
            model.addAttribute("message", "User does not exist");
            return CatmeUtil.FORGOT_PASSWORD_PAGE;
        }

        try
        {
            mailSenderService.sendResetLink(u);
            model.addAttribute("success", "Link Sent Successfully");
            return CatmeUtil.FORGOT_PASSWORD_PAGE;
        } catch (MessagingException e)
        {
            model.addAttribute("message", "Error sending mail. Try again");
            return CatmeUtil.FORGOT_PASSWORD_PAGE;
        }
    }

    @GetMapping("reset-password")
    public String showPasswordResetPage(@RequestParam(name = "token") String token)
    {
        IPasswordResetService passwordResetService = passwordAbstractFactory.getPasswordResetService();
        bannerid = passwordResetService.validateToken(token);

        if (bannerid == null)
        {
            System.out.println("Invalid Link");
            return ERROR_PAGE;
        }

        return RESET_PASSWORD_PAGE;
    }

    @PostMapping("reset-password")
    public String updatePassword(@RequestParam(name = "password") String password, Model model)
    {
        IPasswordResetService passwordResetService = passwordAbstractFactory.getPasswordResetService();
        IPasswordPolicyCheckerService passwordPolicyCheckerService = passwordAbstractFactory.getPasswordPolicyCheckerService();

        IUser u = accessControlModelAbstractFactory.createUser();
        u.setBannerId(bannerid);
        u.setPassword(password);

        try
        {
            if (passwordPolicyCheckerService.enforcePasswordPolicy(u) == false)
            {
                model.addAttribute("message", "Password Does not meet requirements");
                return RESET_PASSWORD_PAGE;
            }
        } catch (CatmeException e)
        {
            e.printStackTrace();
        }

        try
        {
            passwordResetService.resetPassword(bannerid, password);
            model.addAttribute("message", "Password Reset Successfully");
            return RESET_PASSWORD_PAGE;
        } catch (CatmeException e)
        {
            model.addAttribute("message", e.getMessage());
            return RESET_PASSWORD_PAGE;
        }
    }
}
