package com.liuning.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.liuning.entity.Channel;

@Mapper
public interface ChannelMapper {

	@Select("select * from cms_channel order by id")//查询频道sql语句  根据id进行排序
	@ResultType(Channel.class)
	List<Channel> getChannels();
	
}
