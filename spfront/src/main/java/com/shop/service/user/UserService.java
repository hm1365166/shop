package com.shop.service.user;

import com.shop.entity.User;

import java.util.List;

public interface UserService {

	/**
	 * 登录验证
	 *
	 * @param phone
	 * @return
	 * @throws Throwable
	 * @author:HM
	 * @date: 17-11-23 11:25:24
	 * @since v1.0.0
	 */
	User checkLoginByPhone(String phone) throws Throwable;

	/**
	 * 查询信息byPhone
	 *
	 * @param phone
	 * @return
	 * @throws Throwable
	 * @author:HM
	 * @date: 17-11-23 11:26:10
	 * @since v1.0.0
	 */
	User selectByUserPhone(String phone) throws Throwable;

	/**
	 * 查询所有用户信息
	 *
	 * @return
	 * @throws Throwable
	 * @author:HM
	 * @date: 17-11-23 11:27:07
	 * @since v1.0.0
	 */
	List<User> getUserInfo() throws Throwable;

}
