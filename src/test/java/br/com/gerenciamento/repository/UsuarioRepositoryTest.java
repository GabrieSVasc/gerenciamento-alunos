package br.com.gerenciamento.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@After
	public void afterEach() {
		this.usuarioRepository.deleteAll();
	}
	
	@Test
	public void realizaLoginBemSucedidoTest() {
		Usuario user = new Usuario();
		user.setEmail("email@gmail.com");
		user.setUser("Usuario");
		user.setSenha("Senha");
		this.usuarioRepository.save(user);
		Usuario logado = this.usuarioRepository.buscarLogin("Usuario", "Senha");
		Assert.assertEquals(user.getId(), logado.getId());
	}
	
	@Test
	public void realizaLoginSenhaErradaTest() {
		Usuario user = new Usuario();
		user.setEmail("email@gmail.com");
		user.setUser("Usuario");
		user.setSenha("Senha");
		this.usuarioRepository.save(user);
		Usuario logado = this.usuarioRepository.buscarLogin("Usuario", "SenhaErrada");
		Assert.assertNull(logado);
	}
	
	@Test
	public void encontraEmailExistenteTest() {
		Usuario user = new Usuario();
		user.setEmail("email@gmail.com");
		user.setUser("Usuario");
		user.setSenha("Senha");
		this.usuarioRepository.save(user);
		Usuario logado = this.usuarioRepository.findByEmail("email@gmail.com");
		Assert.assertEquals(user.getId(), logado.getId());
	}
	
	@Test
	public void encontraEmailInexistenteTest() {
		Usuario user = new Usuario();
		user.setEmail("email@gmail.com");
		user.setUser("Usuario");
		user.setSenha("Senha");
		this.usuarioRepository.save(user);
		Usuario logado = this.usuarioRepository.findByEmail("emailInexistente@gmail.com");
		Assert.assertNull(logado);
	}
}