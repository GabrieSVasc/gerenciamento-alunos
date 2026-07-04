package br.com.gerenciamento.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import br.com.gerenciamento.util.Util;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private ServiceUsuario serviceUsuario;
	
	@Autowired
	private UsuarioController usuarioController;
	
	@Test
	public void realizarLoginInformacoesInvalidasTest() throws Exception {
		mockMvc.perform(post("/login")
				.param("user", "Username")
				.param("senha", "SenhaErrada"))
				.andExpect(view().name("login/cadastro"));
	}
	
	@Test
	public void realizarLoginComSucessoTest() throws Exception, Exception {
		Usuario user = new Usuario();
		user.setEmail("email@gmail.com");
		user.setSenha("Senha");
		user.setUser("Username");
        
        this.serviceUsuario.salvarUsuario(user);

        mockMvc.perform(post("/login")
                        .param("user", "Username")
                        .param("senha", "Senha"))
                		.andExpect(view().name("home/index"));
	}
	
	@Test
	public void cadastraUmUsuarioComSucesso() throws Exception {
		mockMvc.perform(post("/salvarUsuario")
				.param("user", "Username1")
				.param("senha", "Senha1")
				.param("email", "Mail@gmail.com"))
				.andExpect(view().name("redirect:/"));
	}

	@Test
	public void realizaLogoutComSucesso() throws Exception {
		mockMvc.perform(post("/logout"))
			.andExpect(status().isOk())
			.andExpect(view().name("login/login"));
	}

}