package cn.zjw.edubackend.controller;


import cn.zjw.edubackend.pojo.bbs.Post;
import cn.zjw.edubackend.pojo.bbs.Result;
import cn.zjw.edubackend.service.PostService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/posts")
//@Tag(name = "/posts")
public class PostController {

    @Autowired
    PostService postService;

    // 使用ResponseEntity这个内置类来查看相应的内容
//    @Operation(summary = "添加新帖子")
    @PostMapping
    public Result addPost( @RequestBody Post post) {
        try {
            postService.createPost(post);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    // 更新帖子
//    @Operation(summary = "更新帖子")
    @PutMapping("/id/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Post post) {
        try {
            post.setId(id);
            postService.updatePost(post);
            return ResponseEntity.ok("Post updated successfully");
        } catch (Exception e){
            return ResponseEntity.ok("failed");
        }
    }

    // 根据标题更新帖子
//    @Operation(summary = "更新帖子")
    @PutMapping("/title/{title}")
    public ResponseEntity<?> updatePostbyTitle(@PathVariable String title, @RequestBody Post post) {
        try {
            post.setTitle(title);
            postService.updatePostbyTitle(post);
            return ResponseEntity.ok("Post updated successfully");
        } catch (Exception e){
            return ResponseEntity.ok("failed");
        }
    }


    // 获取指定帖子
//    @Operation(summary = "获取指定帖子")
    @GetMapping("/id/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(post);
    }

    // 按照标题获取指定帖子
//    @Operation(summary = "获取指定帖子")
    @GetMapping("/title/{title}")
    public ResponseEntity<Post> getPostbyTitle(@PathVariable String title) {
        Post post = postService.getPostByTitle(title);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(post);
    }

    // 获取所有的帖子
//    @Operation(summary = "获取所有的帖子")
    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = postService.getAllPosts();
        if (posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(posts);
        }
        return ResponseEntity.ok(posts);
    }
}
