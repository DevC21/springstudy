package com.gdu.myapp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.myapp.dto.BbsDto;

@Mapper
public interface BbsMapper {
	int insertBbs(BbsDto bbs);
	int getBbsCount();
	List<BbsDto> getBbsList(Map<String, Object> map);
	int updateGroupOrder(BbsDto bbs);
	int insertReply(BbsDto reply);
	int removeBbs(int bbsNo);
	int getSearchCount(Map<String, Object> map);
	List<BbsDto> getSearchBbsList(Map<String, Object> map);
}
