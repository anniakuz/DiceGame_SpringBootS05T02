package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.module_tests;

import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.exceptions.HttpException;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Game;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Player;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Role;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services.GameServiceImpl;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    @Autowired
    @InjectMocks
   private GameServiceImpl gameService;

    @MockBean
    private GameRepository gameRepository;

    @MockBean
    private PlayerServiceImpl playerService;

  private static Game game1;
  private static  Game game2;
  private static Player savedPlayer;
@BeforeAll
public static void init(){
    savedPlayer = Player.builder().playerId(1).build();
    game1 = new Game(1,2,4,savedPlayer,false);
    game2 = new Game(2,3,4,savedPlayer,true);
}



    @Test
    public void  deleteAllGamesWithNotExist(){
        savedPlayer = Player.builder().playerId(1).build();
        when(playerService.getAllPlayers()).thenReturn(List.of(savedPlayer));
       // when(gameRepository.getAllByPlayer(savedPlayer)).thenReturn(new ArrayList<>());
        assertThatThrownBy(() -> {
            gameService.deleteAllGames(1);
        }).isInstanceOf(HttpException.class)
                .hasMessageContaining("There is no one game");

    }
    @Test
    public void  deleteAllGamesWithExist(){
        when(playerService.getAllPlayers()).thenReturn(List.of(savedPlayer));
        when(gameRepository.getAllByPlayer(savedPlayer)).thenReturn(List.of(game1, game2));
        assertTrue(gameService.deleteAllGames(1));

    }



}
