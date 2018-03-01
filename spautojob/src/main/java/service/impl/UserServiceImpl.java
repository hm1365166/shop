package service.impl;

import dao.UserDao;
import entity.User;
import service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userDao;

	@Override
	public User selectByUserPhone(String userId) throws Throwable {
		return userDao.selectByUserPhone(userId);
	}

	@Override
	public List<User> getUserInfo() throws Throwable {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		return userDao.getUserInfo();
	}

}
