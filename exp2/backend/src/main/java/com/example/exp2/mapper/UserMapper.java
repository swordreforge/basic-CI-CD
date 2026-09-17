package com.example.exp2.mapper;

import com.example.exp2.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT id, username, password, email, create_time FROM t_user ORDER BY id")
    List<User> findAll();

    @Select("SELECT id, username, password, email, create_time FROM t_user WHERE id = #{id}")
    User findById(Long id);

    @Insert("INSERT INTO t_user(username, password, email) VALUES(#{username}, #{password}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("UPDATE t_user SET username = #{username}, password = #{password}, email = #{email} WHERE id = #{id}")
    int update(User user);

    @Delete("DELETE FROM t_user WHERE id = #{id}")
    int delete(Long id);
}
