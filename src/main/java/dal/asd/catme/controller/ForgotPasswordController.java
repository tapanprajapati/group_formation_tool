package dal.asd.catme.controller;

import dal.asd.catme.beans.User;
import dal.asd.catme.config.SystemConfig;
import dal.asd.catme.exception.CatmeException;
import dal.asd.catme.service.IMailSenderService;
import dal.asd.catme.service.IPasswordPolicyCheckerService;
import dal.asd.catme.service.IPasswordResetService;
import dal.asd.catme.util.CatmeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;

import static dal.asd.catme.util.CatmeUtil.*;

@Controller
@RequestMapping("/")
public class ForgotPasswordController
{

    IPasswordResetService passwordResetService;

    IPasswordPolicyCheckerService passwordPolicyCheckerService;

    IMailSenderService mailSenderService;

    String bannerid;

    @RequestMapping("forgotPassword")
    public String forgotPassword()
    {
        return CatmeUtil.FORGOT_PASSWORD_PAGE;
    }
    @PostMapping("forgotPassword")
    public String resetPassword(@RequestParam("bannerid") String bannerid, Model model)
    {
        System.out.println("Reseting password");
        passwordResetService= SystemConfig.instance().getPasswordResetService();
        User u = passwordResetService.generateResetLink(bannerid);

        if(u==null)
        {
            model.addAttribute("message","User does not exist");
            return CatmeUtil.FORGOT_PASSWORD_PAGE;
        }

        try
        {
            mailSenderService=SystemConfig.instance().getMailSenderService();
            mailSenderService.sendResetLink(u);
            model.addAttribute("success","Link Sent Successfully");
            return CatmeUtil.FORGOT_PASSWORD_PAGE;
        }
        catch (MessagingException e)
        {
            model.addAttribute("message","Error sending mail. Try again");
            return CatmeUtil.FORGOT_PASSWORD_PAGE;
        }
    }

    @GetMapping("reset-password")
    public String showPasswordResetPage(@RequestParam(name = "token") String token)
    {
        passwordResetService = SystemConfig.instance().getPasswordResetService();
        bannerid = passwordResetService.validateToken(token);

        if(bannerid==null)
        {
            System.out.println("Invalid Link");
            return ERROR_PAGE;
        }

        return RESET_PASSWORD_PAGE;
    }

    @PostMapping("reset-password")
    public String updatePassword(@RequestParam(name = "password") String password, Model model)
    {
        passwordResetService = SystemConfig.instance().getPasswordResetService();
        passwordPolicyCheckerService = SystemConfig.instance().getPasswordPolicyCheckerService();

        User u = new User();
        u.setBannerId(bannerid);
        u.setPassword(password);

        try
        {
            if(!passwordPolicyCheckerService.enforcePasswordPolicy(u))
            {
                model.addAttribute("message","Password Does not meet requirements");
                return RESET_PASSWORD_PAGE;
            }
        } catch (CatmeException e)
        {
            e.printStackTrace();
        }

        try
        {
            passwordResetService.resetPassword(bannerid,password);
            model.addAttribute("message","Password Reset Successfully");
            return RESET_PASSWORD_PAGE;
        } catch (CatmeException e)
        {
            model.addAttribute("message",e.getMessage());
            return RESET_PASSWORD_PAGE;
        }
    }
}