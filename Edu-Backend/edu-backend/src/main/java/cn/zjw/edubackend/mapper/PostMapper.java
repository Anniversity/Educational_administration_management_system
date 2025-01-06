package cn.zjw.edubackend.mapper;



import cn.zjw.edubackend.pojo.bbs.Post;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PostMapper extends BaseMapper<Post>{

    // 创建新的post
    @Insert("INSERT INTO posts (title, content, user_id, create_time, update_time) " +
            "VALUES (#{title}, #{content}, #{userId}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createPost(Post post);

    // 更新post
    @Update("UPDATE posts SET title = #{title}, content = #{content}, " +
            "user_id = #{userId}, update_time = #{updateTime} WHERE id = #{id}")
    void updatePost(Post post);

    // 根据标题更新post
    @Update("UPDATE posts SET title = #{title}, content = #{content}, " +
            "user_id = #{userId}, update_time = #{updateTime} WHERE title = #{title}")
    void updatePostbyTitle(Post post);

    @Select("SELECT id, title, content, user_id, create_time, update_time FROM posts WHERE id = #{id}")
    Post findById(Long id);

    // 按照标题查找post
    @Select("SELECT id, title, content, user_id, create_time, update_time FROM posts WHERE title= #{title}")
    Post findByTitle(String title);

    @Select("SELECT id, title, content, user_id, create_time, update_time FROM posts")

    List<Post> findAll();
}
