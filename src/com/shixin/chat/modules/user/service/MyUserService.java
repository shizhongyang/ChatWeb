package com.shixin.chat.modules.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shixin.chat.modules.user.dao.MyUserDao;
import com.shixin.chat.modules.user.entity.MyUser;



@Service
public class MyUserService {

	@Autowired
	private MyUserDao userDao;
	
	public List findmessage(){
		List<MyUser> list = userDao.findmessage();
		return list;
	}

	//查询登录
	public MyUser login(String telephone, String password) {
		return userDao.login(telephone,password);
	}

	public MyUser get(String id) {
		// TODO Auto-generated method stub
		return userDao.get(id);
	}

	public void update(MyUser myUser) {
		userDao.update(myUser);		
	}

	public void save(MyUser myUser) {
		// TODO Auto-generated method stub
		userDao.save(myUser);
	}

	public MyUser findUserByPhone(String phone) {
		// TODO Auto-generated method stub
		return userDao.findUserByPhone(phone);
	}
	
	
	public MyUser findUserById(String uid) {
		// TODO Auto-generated method stub
		return userDao.findUserById(uid);
	}
	
	
	
}
