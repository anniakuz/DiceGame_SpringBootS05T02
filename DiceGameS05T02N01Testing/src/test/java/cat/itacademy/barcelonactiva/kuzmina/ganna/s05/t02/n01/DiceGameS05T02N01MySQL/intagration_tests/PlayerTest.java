package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.intagration_tests;

import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.exceptions.HttpException;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Game;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Player;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Role;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services.PlayerServiceImpl;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.security.JwtService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestEntityManager
@DataJpaTest//(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class))
//@Import(PlayerServiceImpl.class)
//@SpringBootTest
public class PlayerTest {
    @Autowired
    private PlayerRepository playerRepository;

   //@Autowired
   private static PlayerServiceImpl playerService;

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
playerService = new PlayerServiceImpl(new JwtService(),new BCryptPasswordEncoder(),playerRepository);
        Player createdPlayer = playerService.createPlayer(playerForSave);
        assertThat(createdPlayer.getPlayerId()).isEqualTo(1);
        assertThat(createdPlayer.getName()).isEqualTo(playerForSave.getName());

    }

    @Test
    public void getPlayerTestWithExistPlayer(){
        playerService= new PlayerServiceImpl(new JwtService(),new BCryptPasswordEncoder(),playerRepository);
        savedPlayer =  Player.builder().playerId(1).name("Test").password("test").
                role(Role.USER).registerDate(LocalDate.now()).build();
        playerRepository.save(savedPlayer);
        assertThat(savedPlayer).isEqualToComparingFieldByFieldRecursively
                (playerService.findPlayerById(1));
    }

    @Test
    public void getPlayerTestWithNotExistPlayer(){
        Integer playerId = 2;
        playerService= new PlayerServiceImpl(new JwtService(),new BCryptPasswordEncoder(),playerRepository);
        savedPlayer =  Player.builder().playerId(1).name("Test").password("test").
                role(Role.USER).registerDate(LocalDate.now()).build();
        playerRepository.save(savedPlayer);
        assertThatThrownBy(() -> {
            playerService.findPlayerById(playerId);
        }).isInstanceOf(HttpException.class)
                .hasMessageContaining("Could not find any player with ID "+playerId);
    }

    @Test
    public void getPlayerWithTheHighestRateTestOnePlayer(){
        playerService= new PlayerServiceImpl(new JwtService(),new BCryptPasswordEncoder(),playerRepository);
        Game game1 = new Game(1,2,4,savedPlayer,false);
        Game game2 = new Game(2,3,4,savedPlayer,true);
        Game game3 = new Game(3,3,4,savedPlayer,true);
        Game game4 = new Game(4,3,4,savedPlayer,true);
        Game game5 = new Game(5,1,4,savedPlayer,false);
        Game game6 = new Game(6,2,4,savedPlayer,false);
        Player playerOne =  Player.builder().name("Test1").
                password("test").role(Role.USER).registerDate(LocalDate.now()).games(List.of(game1,game2)).build();
        Player playerTwo =  Player.builder().name("Test2").
                password("test").role(Role.USER).registerDate(LocalDate.now()).games(List.of(game3,game4)).build();
        Player playerThree =  Player.builder().name("Test3").
                password("test").role(Role.USER).registerDate(LocalDate.now()).games(List.of(game5,game6)).build();
        playerRepository.save(playerOne);
        playerRepository.save(playerTwo);
        playerRepository.save(playerThree);

        Assertions.assertThat(playerService.getPlayerWithTheHighestRate()).isNotEmpty().hasSize(1)
                .contains(playerTwo);
    }

    @Test
    public void getPlayerWithTheHighestRateTestList(){
        playerService= new PlayerServiceImpl(new JwtService(),new BCryptPasswordEncoder(),playerRepository);
        Game game1 = new Game(1,2,4,savedPlayer,false);
        Game game2 = new Game(2,3,4,savedPlayer,true);
        Game game3 = new Game(3,3,4,savedPlayer,true);
        Game game4 = new Game(4,3,4,savedPlayer,true);
        Game game5 = new Game(5,1,4,savedPlayer,false);
        Game game6 = new Game(6,2,4,savedPlayer,false);
        Game game7 = new Game(7,1,6,savedPlayer,true);
        Game game8 = new Game(8,2,5,savedPlayer,true);
        Player playerOne =  Player.builder().name("Test1").
                password("test").role(Role.USER).registerDate(LocalDate.now()).games(List.of(game1,game2)).build();
        Player playerTwo =  Player.builder().name("Test2").
                password("test").role(Role.USER).registerDate(LocalDate.now()).games(List.of(game3,game4)).build();
        Player playerThree =  Player.builder().name("Test3").
                password("test").role(Role.USER).registerDate(LocalDate.now()).games(List.of(game5,game6)).build();
        Player playerFour =  Player.builder().name("Test4").
                password("test").role(Role.USER).registerDate(LocalDate.now()).games(List.of(game7,game8)).build();
        playerRepository.save(playerOne);
        playerRepository.save(playerTwo);
        playerRepository.save(playerThree);
        playerRepository.save(playerFour);

        Assertions.assertThat(playerService.getPlayerWithTheHighestRate()).isNotEmpty().hasSize(2)
                .contains(playerTwo,playerFour);
    }

    @Test
    public void getPlayerWithTheLowestRateTestOnePlayer(){
        playerService= new PlayerServiceImpl(new JwtService(),new BCryptPasswordEncoder(),playerRepository);
        Game game1 = new Game(1,2,4,savedPlayer,false);
        Game game2 = new Game(2,3,4,savedPlayer,true);
        Game game3 = new Game(3,3,4,savedPlayer,true);
        Game game4 = new Game(4,3,4,savedPlayer,true);
        Game game5 = new Game(5,1,4,savedPlayer,false);
        Game game6 = new Game(6,2,4,savedPlayer,false);
        Player playerOne =  Player.builder().name("Test1").
                password("test").role(Role.USER).registerDate(LocalDate.now()).games(List.of(game1,game2)).build();
        Player playerTwo =  Player.builder().name("Test2").
                password("test").role(Role.USER).registerDate(LocalDate.now()).games(List.of(game3,game4)).build();
        Player playerThree =  Player.builder().name("Test3").
                password("test").role(Role.USER).registerDate(LocalDate.now()).games(List.of(game5,game6)).build();
        playerRepository.save(playerOne);
        playerRepository.save(playerTwo);
        playerRepository.save(playerThree);

        Assertions.assertThat(playerService.getPlayerWithTheLowestRate()).isNotEmpty().hasSize(1)
                .contains(playerThree);
    }

    @Test
    public void getPlayerWithTheLowestRateTestList(){
        playerService= new PlayerServiceImpl(new JwtService(),new BCryptPasswordEncoder(),playerRepository);
        Game game1 = new Game(1,2,4,savedPlayer,false);
        Game game2 = new Game(2,3,4,savedPlayer,true);
        Game game3 = new Game(3,3,4,savedPlayer,true);
        Game game4 = new Game(4,3,4,savedPlayer,true);
        Game game5 = new Game(5,1,4,savedPlayer,false);
        Game game6 = new Game(6,2,4,savedPlayer,false);
        Game game7 = new Game(7,1,4,savedPlayer,false);
        Game game8 = new Game(8,2,4,savedPlayer,false);
        Player playerOne =  Player.builder().name("Test1").
                password("test").role(Role.USER).registerDate(LocalDate.now()).games(List.of(game1,game2)).build();
        Player playerTwo =  Player.builder().name("Test2").
                password("test").role(Role.USER).registerDate(LocalDate.now()).games(List.of(game3,game4)).build();
        Player playerThree =  Player.builder().name("Test3").
                password("test").role(Role.USER).registerDate(LocalDate.now()).games(List.of(game5,game6)).build();
        Player playerFour =  Player.builder().name("Test4").
                password("test").role(Role.USER).registerDate(LocalDate.now()).games(List.of(game7,game8)).build();
        playerRepository.save(playerOne);
        playerRepository.save(playerTwo);
        playerRepository.save(playerThree);
        playerRepository.save(playerFour);

        Assertions.assertThat(playerService.getPlayerWithTheLowestRate()).isNotEmpty().hasSize(2)
                .contains(playerThree,playerFour);
    }






}


