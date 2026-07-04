package br.com.gerenciamento.controller;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;

@SuppressWarnings("unchecked")
@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {
	@Autowired
	private AlunoController alunoController;
	
	@Test
	public void pesquisarAlunoRetornaListagemCompletaTest() throws Exception {
		Aluno aluno = new Aluno();
		aluno.setNome("Jorge");
		aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        BindingResult bindingResult =
                new BeanPropertyBindingResult(aluno, "aluno");
        this.alunoController.inserirAluno(aluno, bindingResult);
		ModelAndView mv = this.alunoController.pesquisarAluno("");
		List<Aluno> listaAlunos = (List<Aluno>) mv.getModel().get("ListaDeAlunos");
		Assert.assertFalse(listaAlunos.isEmpty());
	}
	
	@Test
	public void pesquisarApenasUmAlunoTest() throws Exception {
		Aluno aluno = new Aluno();
		aluno.setNome("Maria");
		aluno.setTurno(Turno.NOTURNO);
		aluno.setCurso(Curso.INFORMATICA);
		aluno.setStatus(Status.ATIVO);
		aluno.setMatricula("987654");
		BindingResult b = new BeanPropertyBindingResult(aluno, "aluno");
		this.alunoController.inserirAluno(aluno, b);
		ModelAndView mv = this.alunoController.pesquisarAluno("Maria");
		List<Aluno> alunosRetorno = (List<Aluno>) mv.getModel().get("ListaDeAlunos");
		Assert.assertEquals(1, alunosRetorno.size());
	}
	
	@Test
	public void editandoAlunoComSucessoTest() throws Exception {
		Aluno aluno = new Aluno();
		aluno.setNome("Eduardo");
		aluno.setTurno(Turno.NOTURNO);
		aluno.setCurso(Curso.CONTABILIDADE);
		aluno.setStatus(Status.INATIVO);
		aluno.setMatricula("2020");
		BindingResult b = new BeanPropertyBindingResult(aluno, "aluno");
		this.alunoController.inserirAluno(aluno, b);
		aluno.setStatus(Status.ATIVO);
		this.alunoController.editar(aluno);
		ModelAndView mv = this.alunoController.pesquisarAluno("Eduardo");
		List<Aluno> alunos = (List<Aluno>) mv.getModel().get("ListaDeAlunos");
		Assert.assertEquals(Status.ATIVO, alunos.get(0).getStatus());
	}
	
	@Test
	public void editandoAlunoInexistenteTest() throws Exception {
		Aluno aluno = new Aluno();
		aluno.setNome("Miriam");
		aluno.setTurno(Turno.NOTURNO);
		aluno.setCurso(Curso.CONTABILIDADE);
		aluno.setStatus(Status.ATIVO);
		aluno.setMatricula("40256");
		BindingResult b = new BeanPropertyBindingResult(aluno, "aluno");
		this.alunoController.inserirAluno(aluno, b);
		this.alunoController.removerAluno(aluno.getId());
		ModelAndView mv = this.alunoController.pesquisarAluno("Miriam");
		List<Aluno> alunos = (List<Aluno>) mv.getModel().get("ListaDeAlunos");
		Assert.assertTrue(alunos.isEmpty());

	}
}