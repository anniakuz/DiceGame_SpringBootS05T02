package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.module_tests;

import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Game;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Player;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@SpringBootTest
public class PlayerTest {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void calculateRateTest(){

        Player player = Player.builder().playerId(1).name("Test1").
                password(passwordEncoder.encode("test")).role(Role.USER).build();

        Game game1 = new Game(1,2,4,player,false);
        Game game2 = new Game(2,3,4,player,true);
        Game game3 = new Game(3,3,4,player,true);
        Game game4 = new Game(4,3,4,player,true);
        Game game5 = new Game(5,1,4,player,false);
        Game game6 = new Game(6,2,4,player,false);

        player =  Player.builder().games(List.of(game1,game2, game3, game4, game5, game6)).build();
        player.calculateRate();
        Double ratePlayer = (double)(player.getGames().stream().filter(game -> game.isWin()).toList().size())*100/player.getGames().size();

        Assertions.assertThat(player.getRate()).isEqualTo(ratePlayer);
    }

    @Test
    public void calculateRateTestNoRate(){
        Player player = Player.builder().playerId(1).name("Test1").
                password(passwordEncoder.encode("test")).role(Role.USER).build();
        Assertions.assertThat(player.getRate()).isNull();
    }
}
