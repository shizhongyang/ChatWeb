package com.shixin.chat.modules.user.web;

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shixin.chat.common.CreateFileUtil;
import com.shixin.chat.common.JsonMapper;
import com.shixin.chat.common.UUIDTool;
import com.shixin.chat.modules.user.entity.MyUser;
import com.shixin.chat.modules.user.service.MyUserService;



@Controller
//@RequestMapping("/myuser")
public class MyUserController {

	@Autowired
	private MyUserService userService;



	@ModelAttribute
	public void getMyUser(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
		if (id != null) {
			map.put("myUser", userService.get(id));
		}
	}

	@ResponseBody
	@RequestMapping("/findMessage")
	public String findMessage() {
		System.out.println("测试进入");
		ArrayList<MyUser> list = (ArrayList<MyUser>) userService.findmessage();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("code", 200);
		map.put("msg", "请求成功");
		map.put("list", list);
		String string = JsonMapper.getInstance().toJson(map);
		System.out.println("--------" + string);
		return string;
	}

	/**
	 * 跳转到注册页面
	 * 
	 * @return
	 */
	// @ResponseBody
	@RequestMapping(value = "/registPage", method = RequestMethod.GET)
	public String registPage() {
		
		System.out.println("--------------------------");
		return "user/regist";
	}

	/**
	 * 注册的接口
	 * 
	 * @param myUser
	 *            用户信息
	 * @param file
	 *            文件
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */

	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public String regist(MyUser myUser, // 接收的bean
			@RequestParam(value = "file", required = false) MultipartFile file, // 接收的图片
			HttpServletRequest request) throws IllegalStateException, IOException {
		String checkcode = request.getParameter("checkcode");
		String code = (String) request.getSession().getAttribute("checkcode");
		if (code.equals(checkcode)) {
			String pathRoot = request.getSession().getServletContext().getRealPath("");
			String path = "";
			if (!file.isEmpty()) {
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				String contentType = file.getContentType();
				String imageName = contentType.substring(contentType.indexOf("/") + 1);
				path = "/static/images/" + uuid + "." + imageName;

				CreateFileUtil.createDir(pathRoot + path);
				file.transferTo(new File(pathRoot + path));
				System.out.println("------------success");
				myUser.setImgAvatar(path);
			}
			userService.save(myUser);
			myUser = userService.findUserByPhone(myUser.getTelephone());
			request.getSession().setAttribute("myUser", myUser);
		}
		System.out.println("-----------" + myUser);

		return "user/index";
	}

	
	@ResponseBody
	@RequestMapping(value = "/registInPhone", method = RequestMethod.POST)
	public String registInPhone(MyUser myUser, // 接收的bean
			@RequestParam(value = "file", required = false) MultipartFile file, // 接收的图片
			HttpServletRequest request) throws IllegalStateException, IOException {
		String pathRoot = request.getSession().getServletContext().getRealPath("");
		String path = "";
		if (!file.isEmpty()) {
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			String contentType = file.getContentType();
			String imageName = contentType.substring(contentType.indexOf("/") + 1);
			path = "/static/images/" + uuid + "." + imageName;

			CreateFileUtil.createDir(pathRoot + path);
			file.transferTo(new File(pathRoot + path));
			System.out.println("------------success");
			myUser.setImgAvatar(path);

			userService.save(myUser);
			myUser = userService.findUserByPhone(myUser.getTelephone());
			request.getSession().setAttribute("myUser", myUser);
			System.out.println("-----------" + myUser);
			return "success";
		}else {
			return "头像为空";
		}
		//return "user/index";
	}

	/**
	 * 前台:注册AJAX验证码.
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkCode", method = RequestMethod.POST)
	public void checkUserName(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String checkCode = request.getParameter("checkcode");
		String code = (String) request.getSession().getAttribute("checkcode");

		System.out.println("------------------" + checkCode);
		response.setContentType("text/html;charset=UTF-8");
		if (!code.equals(checkCode)) {
			// 用户名可以使用的
			response.getWriter().print("验证码错误");
		} else {
			// 用户名已经存在
			response.getWriter().print("验证码验证通过");
		}
	}

	/**
	 * 校验手机号.
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkPhone", method = RequestMethod.POST)
	public void checkPhone(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String phone = request.getParameter("phone");
		MyUser myUser = userService.findUserByPhone(phone);

		System.out.println("------------------" + phone);
		response.setContentType("text/html;charset=UTF-8");
		if (!(myUser == null)) {
			// 用户名可以使用的
			response.getWriter().print("此号码已经注册");
		} else {
			// 用户名已经存在
			response.getWriter().print("此号码可以注册");
		}
	}

	/**
	 * 登录的接口
	 * 
	 * @param request
	 *            请求信息
	 * @param response
	 *            返回信息
	 * @param maps
	 * @return
	 * @throws IOException
	 */
	// @ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response, Map<String, Object> maps)
			throws IOException {
		// System.out.println("测试进入-----");
		String checkCode = request.getParameter("checkcode");
		String code = (String) request.getSession().getAttribute("checkcode");
		if (!code.equals(checkCode)) {
			// 用户名可以使用的
			request.getSession().setAttribute("message", "验证码错误");
			return "user/login";
		}

		String telephone = request.getParameter("telephone");
		String password = request.getParameter("password");
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		System.out.println("测试进入-----" + telephone);
		if (telephone.equals("") || password.equals("")) {
			map.put("code", -1);
			map.put("msg", "用户名密码错误");
			map.put("data", null);
			String string = JsonMapper.getInstance().toJson(map);
			return "user/login";
		} else {
			MyUser user = userService.login(telephone, password);
			if (user != null) {
				System.out.println("-------------" + user);

				// 生成token值
				String token = UUIDTool.getUUID();
				// 设置到user当中
				user.setToken(token);
				HttpSession session = request.getSession();

				session.setAttribute("token", token);
				session.setAttribute("myUser", user);

		/*		UserMessage message = new UserMessage();
				message.setRead(false);
				message.setUser(user);
				message.setContent("----");

				messageService.save(message);*/

				map.put("code", 1);
				map.put("msg", "登录成功");
				map.put("data", user);
				maps.put("myUser", user);
				request.getSession().setAttribute("myUser", user);
				String string = JsonMapper.getInstance().toJson(map);
				return "user/index";
			} else {
				return "user/login";
			}
		}

	}

	@ResponseBody
	@RequestMapping(value = "/loginPhone", method = RequestMethod.POST)
	public String loginPhone(HttpServletRequest request) {
		String telephone = request.getParameter("telephone");
		String password = request.getParameter("password");
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (telephone.equals("") || password.equals("")) {
			map.put("code", -1);
			map.put("msg", "用户名密码错误");
			map.put("data", null);
			String string = JsonMapper.getInstance().toJson(map);
			return string;
		} else {
			MyUser user = userService.login(telephone, password);
			if (user != null) {
				System.out.println("-------------" + user);

				// 生成token值
				String token = UUIDTool.getUUID();
				// 设置到user当中
				user.setToken(token);
				HttpSession session = request.getSession();

				session.setAttribute("token", token);
				session.setAttribute("myUser", user);

				map.put("code", 1);
				map.put("msg", "登录成功");
				map.put("data", user);
				String string = JsonMapper.getInstance().toJson(map);
				return string;
			} else {
				map.put("code", -1);
				map.put("msg", "用户名密码错误");
				map.put("data", null);
				String string = JsonMapper.getInstance().toJson(map);

				return string;
			}
		}
	}

	/**
	 * 更新页面
	 * 
	 * @return 返回登录页面的路径
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(MyUser myUser, HttpServletRequest request) {
		System.out.println("----------hello" + myUser.toString());
		userService.update(myUser);
		System.out.println("----------hello" + myUser);
		HttpSession session = request.getSession();
		session.setAttribute("myUser", myUser);

		return "user/perfectinformation";
	}

	/**
	 * 跳转到登录页面
	 * 
	 * @return 返回登录页面的路径
	 */

	@RequestMapping("/toLoginPage")
	public String toLoginPage() {
		return "user/login";
	}

	/**
	 * 跳转到个人信息页面
	 * 
	 * @return 返回个人信息页面的路径
	 */
	@RequestMapping("/toMyMessagePage")
	public String toMyMessage() {
		return "user/perfectinformation";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));

		// 处理日期类型
		/*
		 * binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
		 * public void setAsText(String value) { try { setValue(new
		 * SimpleDateFormat("yyyy-MM-dd").parse(value)); } catch (ParseException
		 * e) { setValue(null); } } public String getAsText() { return new
		 * SimpleDateFormat("yyyy-MM-dd").format((Date) getValue()); } });
		 */

	}

}
