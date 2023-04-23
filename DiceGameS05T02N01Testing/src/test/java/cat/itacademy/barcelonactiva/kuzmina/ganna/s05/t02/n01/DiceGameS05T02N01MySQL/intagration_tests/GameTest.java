package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.intagration_tests;

import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.exceptions.HttpException;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Game;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Player;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Role;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services.GameServiceImpl;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services.PlayGameService;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services.PlayGameServiceImpl;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services.PlayerServiceImpl;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.security.JwtService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestEntityManager
@DataJpaTest
public class GameTest {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    PlayerRepository playerRepository;

    private static Game game1;
    private static  Game game2;
    private static Player savedPlayer;
    private GameServiceImpl gameService;

    private PlayerServiceImpl playerService;
    private PlayGameServiceImpl playGameService = new PlayGameServiceImpl();
    @BeforeAll
    public static void init(){
        savedPlayer =  Player.builder().playerId(1).name("Test").password("test").
                role(Role.USER).registerDate(LocalDate.now()).build();
        game1 = new Game(1,2,4,savedPlayer,false);
        game2 = new Game(2,3,4,savedPlayer,true);
    }



    @Test
    public void  deleteAllGamesWithNotExist(){
        playerService = new PlayerServiceImpl(new JwtService(),new BCryptPasswordEncoder(),playerRepository);
        gameService = new GameServiceImpl(gameRepository,playerService,playGameService);
        //savedPlayer = Player.builder().playerId(1).build();
        playerRepository.save(savedPlayer);
        assertThatThrownBy(() -> {
            gameService.deleteAllGames(1);
        }).isInstanceOf(HttpException.class)
                .hasMessageContaining("There is no one game");

    }

    @Test
    public void playGameTest(){
        playerService = new PlayerServiceImpl(new JwtService(),new BCryptPasswordEncoder(),playerRepository);
        gameService = new GameServiceImpl(gameRepository,playerService,playGameService);
        // savedPlayer = Player.builder().playerId(1).build();
        playerRepository.save(savedPlayer);
        // savedPlayer.setGames(List.of(game1,game2));
        gameService.playGame(1);
        gameService.playGame(1);
        assertEquals(gameRepository.getAllByPlayer(savedPlayer).size(),2);


    }



    @Test
    public void  deleteAllGamesWithExist(){
        playerService = new PlayerServiceImpl(new JwtService(),new BCryptPasswordEncoder(),playerRepository);
        gameService = new GameServiceImpl(gameRepository,playerService,playGameService);
       // savedPlayer = Player.builder().playerId(1).build();
        playerRepository.save(savedPlayer);
       // savedPlayer.setGames(List.of(game1,game2));
        gameService.playGame(1);
        gameService.playGame(1);
        //
       // gameRepository.save(game1);
       // gameRepository.save(game2);
       // assertThat(gameRepository.getAllByPlayer(savedPlayer).isEmpty());
        gameService.deleteAllGames(1);
        assertTrue(gameRepository.getAllByPlayer(savedPlayer).isEmpty());

       // when(playerService.getAllPlayers()).thenReturn(List.of(savedPlayer));
       // when(gameRepository.getAllByPlayer(savedPlayer)).thenReturn(List.of(game1, game2));
       // assertTrue(gameService.deleteAllGames(1));

    }

}
