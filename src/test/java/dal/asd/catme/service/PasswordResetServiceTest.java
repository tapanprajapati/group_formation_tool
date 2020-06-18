package dal.asd.catme.service;

import dal.asd.catme.beans.Role;
import dal.asd.catme.beans.User;
import dal.asd.catme.dao.IUserDao;
import dal.asd.catme.dao.UserDaoMock;
import dal.asd.catme.exception.CatmeException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PasswordResetServiceTest
{

    ArrayList<User> users = getUsers();
    IUserDao userDao = new UserDaoMock(users);
    @Test
    void userExists()
	{

		 IPasswordResetService service = new PasswordResetService(userDao);

		 assertTrue(service.userExists(users.get(0).getBannerId()));

		 assertFalse(service.userExists("B00123456"));

	}

	@Test
    void generateResetLinkTest()
    {
        IPasswordResetService service = new PasswordResetService(userDao);

        assertNotNull(service.generateResetLink(users.get(0).getBannerId()));

        assertNull(service.generateResetLink("ASDV"));
    }

    @Test
    void validateTokenTest()
    {
        IPasswordResetService service = new PasswordResetService(userDao);

        assertNotNull(service.validateToken("@@@@"));

        assertNull(service.validateToken("!!!!"));
    }

    @Test
    void resetPasswordTest()
    {
        IPasswordResetService service = new PasswordResetService(userDao);

        try
        {
            service.resetPassword("B00121212","ABCD");
            service.resetPassword("B00121212",null);
        } catch (CatmeException e)
        {
            fail();
            e.printStackTrace();
        }
    }

    ArrayList<User> getUsers()
    {
        ArrayList<User> users = new ArrayList<>();

        User u = new User("B00121212","Last","First","abc@123.com");
        ArrayList<Role> roles = new ArrayList<>();
        u.setRole(roles);

        users.add(u);
        return users;
    }
}