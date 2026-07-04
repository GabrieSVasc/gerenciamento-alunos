package br.com.gerenciamento.aceitacao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.bonigarcia.wdm.WebDriverManager;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AceitacaoUsuarioTest {
	private WebDriver driver;
	
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
    public void cadastroEPrimeiroLoginTest() {
        driver.get("http://localhost:8080");
        driver.findElement(By.xpath("//a[text()='Clique aqui para se cadastrar']")).click();
        driver.findElement(By.name("email")).sendKeys("newuser@gmail.com");
        driver.findElement(By.name("user")).sendKeys("Usuario");
        driver.findElement(By.name("senha")).sendKeys("Senha");
        driver.findElement(By.xpath("//button[text()='Cadastrar']")).click();
        
        driver.findElement(By.name("user")).sendKeys("Usuario");
        driver.findElement(By.name("senha")).sendKeys("Senha");
        driver.findElement(By.xpath("//button[text()='Login']")).click();
        
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
    }

}