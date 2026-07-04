package br.com.gerenciamento.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {
	@Autowired
	private ServiceUsuario serviceUsuario;
	
	@Test
	public void salvarUsuarioSemSucessoTest() throws Exception {
		Usuario user1 = new Usuario();
		user1.setEmail("email@gmail.com");
		user1.setUser("PrimeiroUsuario");
		user1.setSenha("SENHA123");
		this.serviceUsuario.salvarUsuario(user1);
		Usuario user2 = new Usuario();
		user2.setEmail("email@gmail.com");
		user2.setUser("SegundoUsuario");
		user2.setSenha("senha222");
		Assert.assertThrows(EmailExistsException.class, ()->{
			this.serviceUsuario.salvarUsuario(user2);});
	}
	
	@Test
	public void salvarUmUsuarioTest() throws Exception {
		Usuario user = new Usuario();
		user.setEmail("EmailNOVO@gmail.com");
		user.setUser("UsuarioNOVO");
		user.setSenha("SenhaNOVO");
		this.serviceUsuario.salvarUsuario(user);
	}
	
	
	//Não passa pois o login não converte a senha para md5 antes de buscar no banco
	@Test
	public void realizarLoginComSucessoTest() throws Exception {
		Usuario user = new Usuario();
		user.setEmail("EmailQualquer@gmail.com");
		user.setUser("NovoUsuario");
		user.setSenha("SenhaNovoUsuario");
		this.serviceUsuario.salvarUsuario(user);
		Usuario logado = this.serviceUsuario.loginUser("NovoUsuario", "SenhaNovoUsuario");
		Assert.assertEquals(user.getId(), logado.getId());
	}
	
	@Test
	public void loginSemSucessoTest() {
		Usuario logado = this.serviceUsuario.loginUser("USUARIONAOCADASTRADO", "SENHAINEXISTENTE");
		Assert.assertNull(logado);
	}
}