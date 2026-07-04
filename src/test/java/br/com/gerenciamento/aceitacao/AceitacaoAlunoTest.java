package br.com.gerenciamento.aceitacao;

import java.time.Duration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import io.github.bonigarcia.wdm.WebDriverManager;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AceitacaoAlunoTest {
	private WebDriver driver;
	@Autowired
	private ServiceUsuario serviceUsuario;
	
	@Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }
	
	@After
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void cadastroEBuscaDeUmAluno() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		Usuario user = new Usuario();
		user.setEmail("email@gmail.com");
		user.setUser("Usuario");
		user.setSenha("Senha");
		this.serviceUsuario.salvarUsuario(user);

		driver.get("http://localhost:8080");
        driver.findElement(By.name("user")).sendKeys("Usuario");
        driver.findElement(By.name("senha")).sendKeys("Senha");
        driver.findElement(By.xpath("//button[text()='Login']")).click();
        
        wait.until(ExpectedConditions.urlContains("/login"));
        
        driver.findElement(By.xpath("//a[text()='CADASTRAR ALUNO']")).click();
        driver.findElement(By.name("nome")).sendKeys("Maria");
        driver.findElement(By.name("curso")).sendKeys("Maria");
        
        WebElement selectElementCurso = driver.findElement(By.name("curso"));
		Select selectCurso = new Select(selectElementCurso);
		selectCurso.selectByVisibleText("ENFERMAGEM");
		
		driver.findElement(By.xpath("//button[text()='Gerar']")).click();
		
        WebElement selectElementTurno= driver.findElement(By.name("turno"));
		Select selectTurno= new Select(selectElementTurno);
		selectTurno.selectByVisibleText("NOTURNO");

        WebElement selectElementStatus= driver.findElement(By.name("status"));
		Select selectStatus= new Select(selectElementStatus);
		selectStatus.selectByVisibleText("INATIVO");
		
		driver.findElement(By.xpath("//button[text()='Salvar']")).click();

		wait.until(ExpectedConditions.urlContains("/alunos-adicionados"));
		driver.findElement(By.xpath("//a[contains(., 'Home')]")).click();
		
		wait.until(ExpectedConditions.urlContains("/index"));
		
		driver.findElement(By.name("nome")).sendKeys("Maria");
		
		driver.findElement(By.xpath("//button[text()='Pesquisar']")).click();
		
		WebElement tabela = driver.findElement(By.cssSelector("table.table.table-bordered.table-hover"));
		Assert.assertTrue(tabela.isDisplayed());
		Assert.assertTrue(tabela.getText().contains("Maria"));
	}
}