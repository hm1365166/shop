package com.shop.controller;

import com.shop.entity.User;
import com.shop.service.user.UserService;
import com.shop.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
	final private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	UserService userService;

	@RequestMapping("/")
	public String toLogino() {
		return "index";
	}

	@RequestMapping("/login")
	public String toLogint() {
		return "index";
	}

	@RequestMapping("/_toLogin")
	public ModelAndView checkLogin(HttpServletRequest request, @RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "userPass", required = true) String userPass) {
		ModelAndView mv = new ModelAndView();
		Result rt = new Result();

		if (!userPass.isEmpty() && !userName.isEmpty()) {
			try {
				User user = userService.selectByUserPhone(userName);
				if (user != null && user.getPassWord().equals(userPass)) {
					request.getSession().setAttribute("user",user);
					return new ModelAndView("redirect:/file/toFile");
				} else {
					rt.setErrorMessage("账号或密码错误！");
					mv.addObject("rt",rt);
					mv.setViewName("index");
					return mv;
				}
			} catch (Throwable throwable) {
				logger.error("查询账号异常" + throwable.getCause());
				throwable.printStackTrace();
				rt.setErrorMessage("网络异常！");
				mv.addObject("rt",rt);
				mv.setViewName("index");
				return mv;
			}
		}
		rt.setErrorMessage("账号或密码不为空！");
		mv.addObject("rt",rt);
		mv.setViewName("index");
		return mv;

	}

}
