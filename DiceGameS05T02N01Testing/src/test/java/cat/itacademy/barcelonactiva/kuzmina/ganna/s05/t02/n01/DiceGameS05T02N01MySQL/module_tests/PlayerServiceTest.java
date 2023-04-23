package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.module_tests;

import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.exceptions.HttpException;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Game;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Player;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Role;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services.PlayerServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @MockBean
    PlayerRepository playerRepository;

    @Autowired
    @InjectMocks
    PlayerServiceImpl playerService;

    private static Player playerForSave;
    private static Player savedPlayer;
    private static Player playerForEdit;
    private static Player editedPlayer;


    @BeforeAll
    public static void init(){
        playerForSave = Player.builder().name("Test").password("test").build();
        playerForEdit = Player.builder().name("Test1").build();


    }

    @Test
    public void testAddUser(){
        savedPlayer =  Player.builder().playerId(1).name("Test").password(passwordEncoder.encode("test")).role(Role.USER).build();
        when(playerRepository.save(any())).thenReturn(savedPlayer);
        Player createdPlayer = playerService.createPlayer(playerForSave);
        assertThat(createdPlayer).isEqualToComparingFieldByFieldRecursively(savedPlayer);
    }
    @Test
    public void testEditUserWithExceptionNotRegisteredPlayers(){
        when(playerRepository.findAll()).thenReturn(new ArrayList<Player>());
        assertThatThrownBy(() -> {
           playerService.editPlayer(1,playerForEdit);
        }).isInstanceOf(HttpException.class)
                .hasMessageContaining("There is no registered player");


    }

    @Test
    public void testEditUserWithExceptionNotPlayerById(){
        when(playerRepository.findAll()).thenReturn(List.of(playerForSave));
        when(playerRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(() -> {
            playerService.editPlayer(1,playerForEdit);
        }).isInstanceOf(HttpException.class)
                .hasMessageContaining("Could not find any player with ID "+1);
    }
    @Test
    public void testEditUser() {
        savedPlayer =  Player.builder().playerId(1).name("Test").password(passwordEncoder.encode("test")).role(Role.USER).build();
        when(playerRepository.findAll()).thenReturn(List.of(savedPlayer));
        when(playerRepository.findById(1)).thenReturn(Optional.ofNullable(savedPlayer));
        when(playerRepository.findByName(playerForEdit.getName())).thenReturn(null);
        editedPlayer = Player.builder().playerId(1).name(playerForSave.getName()).password(passwordEncoder.encode("test")).role(Role.USER).build();
        when(playerRepository.save(any())).thenReturn(editedPlayer);
        assertThat(editedPlayer).isEqualToComparingFieldByFieldRecursively
                (playerService.editPlayer(1,playerForEdit));

    }
    @Test
    public void getPlayerTestWithExistPlayer(){
        savedPlayer =  Player.builder().playerId(1).name("Test").password(passwordEncoder.encode("test")).role(Role.USER).build();
        when(playerRepository.findAll()).thenReturn(List.of(savedPlayer));
        when(playerRepository.findById(1)).thenReturn(Optional.ofNullable(savedPlayer));
        assertThat(savedPlayer).isEqualToComparingFieldByFieldRecursively
                (playerService.findPlayerById(1));


    }
    @Test
    public void getPlayerTestWithEmptyRepository(){
        when(playerRepository.findAll()).thenReturn(new ArrayList<>());
        assertThatThrownBy(() -> {
            playerService.findPlayerById(1);
        }).isInstanceOf(HttpException.class)
                .hasMessageContaining("There is no one");


    }
    @Test
    public void getPlayerTestWithNotExistPlayer(){
        Integer playerId = 1;
        savedPlayer =  Player.builder().playerId(playerId).name("Test").password(passwordEncoder.encode("test")).role(Role.USER).build();
        when(playerRepository.findAll()).thenReturn(List.of(savedPlayer));
        when(playerRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(() -> {
            playerService.findPlayerById(playerId);
        }).isInstanceOf(HttpException.class)
                .hasMessageContaining("Could not find any player with ID "+playerId);


    }

    @Test
    public void getPlayerWithTheHighestRateTestOnePlayer(){
       Game game1 = new Game(1,2,4,savedPlayer,false);
       Game game2 = new Game(2,3,4,savedPlayer,true);
       Game game3 = new Game(3,3,4,savedPlayer,true);
       Game game4 = new Game(4,3,4,savedPlayer,true);
       Game game5 = new Game(5,1,4,savedPlayer,false);
       Game game6 = new Game(6,2,4,savedPlayer,false);
       Player playerOne =  Player.builder().playerId(1).name("Test1").
                password(passwordEncoder.encode("test")).role(Role.USER).games(List.of(game1,game2)).build();
       Player playerSecond =  Player.builder().playerId(2).name("Test2").
                password(passwordEncoder.encode("test")).role(Role.USER).games(List.of(game3,game4)).build();
       Player playerThird =  Player.builder().playerId(3).name("Test3").
                password(passwordEncoder.encode("test")).role(Role.USER).games(List.of(game5,game6)).build();

        when(playerRepository.findAll()).thenReturn(List.of(playerOne,playerSecond,playerThird));

        Assertions.assertThat(playerService.getPlayerWithTheHighestRate()).isNotEmpty().hasSize(1)
                .contains(playerSecond);
    }

    @Test
    public void getPlayerWithTheHighestRateTestList(){
        Game game1 = new Game(1,2,4,savedPlayer,false);
        Game game2 = new Game(2,3,4,savedPlayer,true);
        Game game3 = new Game(3,3,4,savedPlayer,true);
        Game game4 = new Game(4,3,4,savedPlayer,true);
        Game game5 = new Game(5,1,4,savedPlayer,false);
        Game game6 = new Game(6,2,4,savedPlayer,false);
        Game game7 = new Game(7,1,6,savedPlayer,true);
        Game game8 = new Game(8,2,5,savedPlayer,true);
        Player playerOne =  Player.builder().playerId(1).name("Test1").
                password(passwordEncoder.encode("test")).role(Role.USER).games(List.of(game1,game2)).build();
        Player playerTwo =  Player.builder().playerId(2).name("Test2").
                password(passwordEncoder.encode("test")).role(Role.USER).games(List.of(game3,game4)).build();
        Player playerThree =  Player.builder().playerId(3).name("Test3").
                password(passwordEncoder.encode("test")).role(Role.USER).games(List.of(game5,game6)).build();
        Player playerFour =  Player.builder().playerId(4).name("Test4").
                password(passwordEncoder.encode("test")).role(Role.USER).games(List.of(game7,game8)).build();

        when(playerRepository.findAll()).thenReturn(List.of(playerOne,playerTwo,playerThree,playerFour));

        Assertions.assertThat(playerService.getPlayerWithTheHighestRate()).isNotEmpty().hasSize(2)
                .contains(playerTwo,playerFour);
    }

    @Test
    public void getPlayerWithTheLowestRateTestOnePlayer(){
        Game game1 = new Game(1,2,4,savedPlayer,false);
        Game game2 = new Game(2,3,4,savedPlayer,true);
        Game game3 = new Game(3,3,4,savedPlayer,true);
        Game game4 = new Game(4,3,4,savedPlayer,true);
        Game game5 = new Game(5,1,4,savedPlayer,false);
        Game game6 = new Game(6,2,4,savedPlayer,false);
        Player playerOne =  Player.builder().playerId(1).name("Test1").
                password(passwordEncoder.encode("test")).role(Role.USER).games(List.of(game1,game2)).build();
        Player playerTwo =  Player.builder().playerId(2).name("Test2").
                password(passwordEncoder.encode("test")).role(Role.USER).games(List.of(game3,game4)).build();
        Player playerThree =  Player.builder().playerId(3).name("Test3").
                password(passwordEncoder.encode("test")).role(Role.USER).games(List.of(game5,game6)).build();

        when(playerRepository.findAll()).thenReturn(List.of(playerOne,playerTwo,playerThree));

        Assertions.assertThat(playerService.getPlayerWithTheLowestRate()).isNotEmpty().hasSize(1)
                .contains(playerThree);
    }

    @Test
    public void getPlayerWithTheLowestRateTestList(){
        Game game1 = new Game(1,2,4,savedPlayer,false);
        Game game2 = new Game(2,3,4,savedPlayer,true);
        Game game3 = new Game(3,3,4,savedPlayer,true);
        Game game4 = new Game(4,3,4,savedPlayer,true);
        Game game5 = new Game(5,1,4,savedPlayer,false);
        Game game6 = new Game(6,2,4,savedPlayer,false);
        Game game7 = new Game(7,1,3,savedPlayer,false);
        Game game8 = new Game(8,2,2,savedPlayer,false);
        Player playerOne =  Player.builder().playerId(1).name("Test1").
                password(passwordEncoder.encode("test")).role(Role.USER).games(List.of(game1,game2)).build();
        Player playerTwo =  Player.builder().playerId(2).name("Test2").
                password(passwordEncoder.encode("test")).role(Role.USER).games(List.of(game3,game4)).build();
        Player playerThree =  Player.builder().playerId(3).name("Test3").
                password(passwordEncoder.encode("test")).role(Role.USER).games(List.of(game5,game6)).build();
        Player playerFour =  Player.builder().playerId(4).name("Test4").
                password(passwordEncoder.encode("test")).role(Role.USER).games(List.of(game7,game8)).build();

        when(playerRepository.findAll()).thenReturn(List.of(playerOne,playerTwo,playerThree,playerFour));

        Assertions.assertThat(playerService.getPlayerWithTheLowestRate()).isNotEmpty().hasSize(2)
                .contains(playerThree,playerFour);
    }



}
