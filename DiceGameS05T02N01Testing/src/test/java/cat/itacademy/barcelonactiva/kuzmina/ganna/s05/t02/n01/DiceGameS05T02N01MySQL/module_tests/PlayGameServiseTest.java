package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.module_tests;

import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Game;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services.PlayGameServiceImpl;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PlayGameServiseTest {

    @Autowired
   // @InjectMocks
    PlayGameServiceImpl playGameService;

    @Test
            public void lostGameTest(){
        try (MockedStatic<Utils> utilities = Mockito.mockStatic(Utils.class)) {
            utilities.when(Utils::getRandomNum)
                    .thenReturn(4);

            Game game = playGameService.play();
            assertThat(game.isWin()).isEqualTo(false);
            assertThat(game.getDice1()).isEqualTo(4);
            assertThat(game.getDice2()).isEqualTo(4);

        }

    }

    @Test
    public void winGameTest(){
        try (MockedStatic<Utils> utilities = Mockito.mockStatic(Utils.class)) {
            utilities.when(Utils::getRandomNum)
                    .thenReturn(4,3);

            Game game = playGameService.play();
            assertThat(game.isWin()).isEqualTo(true);
            assertThat(game.getDice1()).isEqualTo(4);
            assertThat(game.getDice2()).isEqualTo(3);

        }

    }
    @Test
    public void getWinTest(){
        assertThat(playGameService.getWin(3,4)).isEqualTo(true);
        assertThat(playGameService.getWin(4,4)).isEqualTo(false);
    }
    }
