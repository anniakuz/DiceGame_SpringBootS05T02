package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

@SpringBootApplication//(exclude = JpaRepositoriesAutoConfiguration.class)
public class DiceGameS05T02N01MySqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiceGameS05T02N01MySqlApplication.class, args);
	}

}
