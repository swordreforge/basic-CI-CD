package com.example.exp4.mapper;

import com.example.exp4.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT id, username, password, enabled FROM t_user WHERE username = #{username}")
    User findByUsername(String username);

    @Select("SELECT id, username, password, enabled FROM t_user ORDER BY id")
    List<User> findAll();

    @Insert("INSERT INTO t_user(username, password, enabled) VALUES(#{username}, #{password}, #{enabled})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Delete("DELETE FROM t_user WHERE id = #{id}")
    int delete(Long id);

    @Select("SELECT r.code FROM t_role r JOIN t_user_role ur ON r.id = ur.role_id WHERE ur.user_id = #{userId}")
    List<String> findRolesByUserId(Long userId);

    @Select("SELECT p.code FROM t_permission p " +
            "JOIN t_role_permission rp ON p.id = rp.permission_id " +
            "JOIN t_user_role ur ON rp.role_id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    List<String> findPermissionsByUserId(Long userId);

    @Select("SELECT id FROM t_role WHERE code = #{code}")
    Long findRoleIdByCode(String code);

    @Insert("INSERT INTO t_user_role(user_id, role_id) VALUES(#{userId}, #{roleId})")
    int insertUserRole(Long userId, Long roleId);
}
