package cibertec.pe.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import cibertec.pe.entity.Comment;

@FeignClient(name = "COMMENT-CLIENT",url = "https://jsonplaceholder.typicode.com")
public interface CommentFeigntClient {


    @GetMapping("/comments")
    public List<Comment> getComments();
}


