package com.gdu.myapp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.myapp.dto.BlogDto;

@Mapper
public interface BlogMapper {
  int insertBlog(BlogDto blog);
  int getBlogCount();
  List<BlogDto> getBlogList(Map<String, Object> map);
  BlogDto getBlogByNo(int blogNo);
}
