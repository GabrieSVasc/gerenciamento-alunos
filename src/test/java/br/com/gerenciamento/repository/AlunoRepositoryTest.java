package br.com.gerenciamento.repository;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

	@Autowired
	private AlunoRepository alunoRepository;
	
	@After
	public void afterEach() {
		this.alunoRepository.deleteAll();
	}
	
	@Test
	public void encontrarStatusInativoTest() {
		Aluno aluno = new Aluno();
    	aluno.setNome("Mariana");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.alunoRepository.save(aluno);
        Aluno aluno2 = new Aluno();
    	aluno2.setNome("Joana");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("123456");
        this.alunoRepository.save(aluno2);
    	List<Aluno> alunoRetorno = this.alunoRepository.findByStatusInativo();
    	Assert.assertEquals(1, alunoRetorno.size());
	}
	
	@Test
	public void encontrarStatusAtivoTest() {
		Aluno aluno = new Aluno();
    	aluno.setNome("Mariana");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.alunoRepository.save(aluno);
        Aluno aluno2 = new Aluno();
    	aluno2.setNome("Joana");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("123456");
        this.alunoRepository.save(aluno2);
    	List<Aluno> alunoRetorno = this.alunoRepository.findByStatusAtivo();
    	Assert.assertEquals(1, alunoRetorno.size());
	}

	@Test
	public void encontrarPessoasComMesmaInicialTest() {
		Aluno aluno = new Aluno();
    	aluno.setNome("Mariana");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.alunoRepository.save(aluno);
        Aluno aluno2 = new Aluno();
    	aluno2.setNome("Maria");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("123456");
        this.alunoRepository.save(aluno2);
        List<Aluno> alunoRetorno = this.alunoRepository.findByNomeContainingIgnoreCase("M");
        Assert.assertEquals(2, alunoRetorno.size());
	}
	
	@Test
	public void naoEncontraPessoasTest() {
		List<Aluno> retorno = this.alunoRepository.findByNomeContainingIgnoreCase("Marcos");
		Assert.assertTrue(retorno.isEmpty());
	}
}