package com.shixin.chat.modules.user.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.shixin.chat.modules.user.entity.MyUser;
import javax.annotation.Resource;







@Repository
public class MyUserDao extends HibernateDaoSupport {
	@Resource
	public void setSessionFactory0(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List findmessage() {
		String hql = "from MyUser";
		List<MyUser> list = (List<MyUser>) this.getHibernateTemplate().find(hql);
		return list;
	}
	
	//查询登录
	public MyUser login(String telephone, String password) {
		String hql = "from MyUser where telephone = ? and pwd = ?";
		List<MyUser> list = (List<MyUser>) this.getHibernateTemplate().find(hql, telephone,password);
		if (list.size()!=0) {
			return list.get(0);			
		}
		return null;
	}

	public MyUser get(String id) {
		String hql = "from MyUser where id = ?";
		List<MyUser> list = (List<MyUser>) this.getHibernateTemplate().find(hql, id);
		if (list.size()!=0) {
			return list.get(0);			
		}
		return null;
	}

	public void update(MyUser myUser) {
		this.getHibernateTemplate().update(myUser);		
	}

	public void save(MyUser myUser) {
		this.getHibernateTemplate().save(myUser);				
	}

	public MyUser findUserByPhone(String phone) {
		String hql = "from MyUser where telephone = ?";
		List<MyUser> list = (List<MyUser>) this.getHibernateTemplate().find(hql, phone);
		if (list.size()!=0) {
			return list.get(0);			
		}
		return null;
	}

	public MyUser findUserById(String uid) {
		
		String hql = "from MyUser where id = ?";
		List<MyUser> list = (List<MyUser>) this.getHibernateTemplate().find(hql, uid);
		if (list.size()!=0) {
			return list.get(0);			
		}
		return null;
	}

}
