package com.blog.service;

import com.blog.domain.Post;
import com.blog.repository.PostRepository;
import com.blog.request.PostCreate;
import com.blog.request.PostEdit;
import com.blog.request.PostSearch;
import com.blog.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }
    @Test
    @DisplayName("글 작성")
    void test1() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when
        postService.write(postCreate);

        // then
        assertEquals(1L , postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다." , post.getTitle());
        assertEquals("내용입니다." , post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        // given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(requestPost);

        // when
        PostResponse response = postService.get(requestPost.getId());

        // then
        assertNotNull(response);
        assertEquals(1,postRepository.count());
        assertEquals("foo" , response.getTitle());
        assertEquals("bar" , response.getContent());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test3() {
        // given
        List<Post> requestPosts = IntStream.range(0,20)
                        .mapToObj(i -> Post.builder()
                                    .title("foo" + i)
                                    .content("bar1" + i)
                                    .build())
                        .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();

        // when
        List<PostResponse> posts = postService.getList(postSearch);

        // then
        assertEquals(10,posts.size());
        assertEquals("foo19",posts.get(0).getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test4() {
        // given
        Post post = Post.builder()
                .title("동인이")
                .content("채연남자친구")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("서채연")
                .content("동인여자친구")
                .build();

        // when
        postService.edit(post.getId() , postEdit);

        // then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow( () -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));
        Assertions.assertEquals("서채연" , changePost.getTitle());
    }

}