package service;

import entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
	User selectByUserPhone(String phone) throws Throwable;

	List<User> getUserInfo() throws Throwable;

}
