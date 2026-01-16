package de.honoka.demo.spring.oauth2.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import de.honoka.demo.spring.oauth2.auth.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User findByUsername(@Param("username") String username);
}
