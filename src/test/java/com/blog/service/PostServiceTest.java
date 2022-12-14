package com.blog.service;

import com.blog.domain.Post;
import com.blog.exception.PostNotFound;
import com.blog.repository.PostRepository;
import com.blog.request.PostCreate;
import com.blog.request.PostEdit;
import com.blog.request.PostSearch;
import com.blog.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

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
                .content("채연남자친구")
                .build();

        // when
        postService.edit(post.getId() , postEdit);

        // then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow( () -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));
        assertEquals("서채연" , changePost.getTitle());
        assertEquals("채연남자친구" , changePost.getContent());
    }

    @Test
    @DisplayName("글 내용 수정")
    void test5() {
        // given
        Post post = Post.builder()
                .title("동인이")
                .content("채연남자친구")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("동인이")
                .content("천하제일멋쟁이")
                .build();

        // when
        postService.edit(post.getId() , postEdit);

        // then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow( () -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));
        assertEquals("동인이" , changePost.getTitle());
        assertEquals("천하제일멋쟁이" , changePost.getContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    void test6() {
        // given
        Post post = Post.builder()
                .title("동인이")
                .content("채연남자친구")
                .build();

        postRepository.save(post);

        // whenl
        postService.delete(post.getId());

        // then
        assertEquals(0,postRepository.count());
    }

    @Test
    @DisplayName("글 1개 조회 - 존재하지 않는 글")
    void test7() {
        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();
        postRepository.save(post);

        // expected
        assertThrows(PostNotFound.class , () -> {
            postService.get(post.getId() + 1);
        });

    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는 글")
    void test8() {
        // given
        Post post = Post.builder()
                .title("동인이")
                .content("채연남자친구")
                .build();

        postRepository.save(post);

        // expected
        assertThrows(PostNotFound.class , () -> {
            postService.get(post.getId() + 1);
        });
    }

    @Test
    @DisplayName("글 내용 수정 - 존재하지 않는 글")
    void test9() {
        // given
        Post post = Post.builder()
                .title("동인이")
                .content("채연남자친구")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("동인이")
                .content("천하제일멋쟁이")
                .build();

        // expected
        assertThrows(PostNotFound.class , () -> {
            postService.edit(post.getId() + 1 , postEdit);
        });

    }

    @Test
    @DisplayName("글 내용 수정")
    void test10() {
        // given
        Post post = Post.builder()
                .title("동인이")
                .content("채연남자친구")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("천하제일멋쟁이")
                .build();

        // when
        postService.edit(post.getId() , postEdit);

        // then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow( () -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));
        assertEquals("동인이" , changePost.getTitle());
        assertEquals("천하제일멋쟁이" , changePost.getContent());
    }


}