package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;
    
    @Test
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(aluno.getId());
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
        this.serviceAluno.deleteById(aluno.getId());
    }

    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }
    
    @Test
    public void encontrarUmNomeIgnorandoMaiusculasTest() {
    	Aluno aluno = new Aluno();
        aluno.setNome("Maria");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);
    	List<Aluno> alunoRetorno = this.serviceAluno.findByNomeContainingIgnoreCase("MARIA");
    	Assert.assertEquals("Maria", alunoRetorno.get(0).getNome());
        this.serviceAluno.deleteById(aluno.getId());
    }
    
    @Test
    public void encontrarUmNomePorParteTest() {
    	Aluno aluno = new Aluno();
        aluno.setNome("Mariana");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);
    	List<Aluno> alunoRetorno = this.serviceAluno.findByNomeContainingIgnoreCase("Maria");
    	Assert.assertEquals("Mariana", alunoRetorno.get(0).getNome());
        this.serviceAluno.deleteById(aluno.getId());
    }
    
    @Test
    public void listarTodosCadastradosTest() {
    	Aluno aluno = new Aluno();
    	aluno.setNome("Mariana");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);
        Assert.assertEquals(1, this.serviceAluno.findAll().size());
        this.serviceAluno.deleteById(aluno.getId());
    }
    
        
    @Test
    public void cadastrarNomeTamanhoErradoTest() {
    	Aluno aluno = new Aluno();
    	aluno.setNome("João");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);});
    }
}