package com.liuning.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuning.dao.UserMapper;
import com.liuning.entity.User;
import com.liuning.entity.UserVo;
import com.liuning.service.UserService;
import com.liuning.utils.Md5Utils;

/**
 * 
 * @author Zhang旭涛
 *
 */
@Service
public class UserServiceImpl implements UserService {

	/**
	 * 
	 */
	@Autowired
	UserMapper userMapper;

	/**
	 *  登录
	 */
	@Override
	public User login(User user) {
		// TODO Auto-generated method stub
		User loginUser = userMapper.findByName(user.getUsername());
		// 用户存在 而且 密码不为空  且加密后的密码与数据库中的保存一致
		if(loginUser!=null && user.getPassword() !=null 
				&& Md5Utils.md5(user.getPassword())
				.equals(loginUser.getPassword())){
			return loginUser;
		}
		return null;
	}

	/**
	 * 普通用户注册
	 */
	@Override
	public User register(User user) {
		// TODO Auto-generated method stub
		user.setCreateTime(new Date());//设置注册时间
		user.setLocked(0); // 不锁定
		user.setRole("0");//设置角色  普通用户
		user.setPassword(Md5Utils.md5(user.getPassword()));
		
		if(userMapper.add(user)>0) {
			// 添加成功
			return user;
		}else {
			return null;
		}
		
	}

	/**
	 * 根据用户名查找
	 */
	@Override
	public User findByName(String name) {
		// TODO Auto-generated method stub
		return userMapper.findByName(name);
	}

	/**
	 * 条件查询
	 */
	@Override
	public User query(UserVo uservo) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 修改用户
	 */
	@Override
	public int update(User user) {
		// TODO Auto-generated method stub
		return userMapper.update(user);
	}

	/**
	 * 根据id查找用户
	 */
	@Override
	public User findById(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.findById(id);
	}

	/**
	 * 
	 */
	@Override
	public PageInfo<User> search(int pageNumber, int pageSize, String name) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNumber,pageSize);
		List<User> users = userMapper.queryList(name);
		return new PageInfo<User>(users);
		
	}

}
