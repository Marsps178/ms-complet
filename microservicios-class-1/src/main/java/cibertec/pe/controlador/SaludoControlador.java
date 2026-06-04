package cibertec.pe.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cibertec.pe.entity.Comment;
import cibertec.pe.entity.Post;
import cibertec.pe.feignclients.PostFeignClient;
import cibertec.pe.feignclients.CommentFeigntClient;

@RestController
@RequestMapping("api/saludo")
public class SaludoControlador {

	@Autowired
	private PostFeignClient post;

	@Autowired
	private CommentFeigntClient commentClient;

	@GetMapping("/SaludarCibertec")
	public String saludoCibertec() {
		return "Hola Desarrollo Web II";
	}

	@GetMapping("/finAllPosts")
	public List<Post> listarPost(){
		return post.getPost();
	}

	@GetMapping("/finAllComments")
	public List<Comment> listarComents(){
		return commentClient.getComments();
	}

}
