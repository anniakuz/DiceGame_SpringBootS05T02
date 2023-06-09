package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services;

import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.exceptions.HttpException;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Game;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Player;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService{

    private final GameRepository gameRepository;
    private final PlayerService playerService;
    private  final PlayGameServiceImpl playGameService;
    @Override
    public Game playGame(String playerId) {
        if(playerService.getAllPlayers().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no registered player");
        }
        Player player = playerService.findPlayerById(playerId);
        if(player==null){
            throw new HttpException(HttpStatus.NOT_FOUND,"Player with this id is not found");
        }

        Game gamePlayed = playGameService.play();
        gamePlayed.setPlayer(player);
        //player.calculateRate();
        //gameRepository.save(gamePlayed);
        if(player.getGames()==null){
            player.setGames(new ArrayList<>());
        }
        Game savedGame = gameRepository.save(gamePlayed);
        player.getGames().add(savedGame);
        playerService.savePlayer(player);
        return savedGame;
    }


    @Override
    public List<Game> getAllGamesWithPlayerId(String playerId){
        if(playerService.getAllPlayers().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no registered player");
        }
        //List<Game> allGames = gameRepository.findAll().stream()
        //        .filter(game -> game.getPlayer().getPlayerId().equals(playerId)).toList();
Player player = Player.builder().playerId(playerId).build();
        return gameRepository.getGamesByPlayer(player);
    }

    @Override
    public void deleteAllGames(String playerId){
        if(playerService.getAllPlayers().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no one");
        }
        List<Game> allGames = getAllGamesWithPlayerId(playerId);
        for(Game game:allGames) {
            gameRepository.deleteById(game.getGameId());
        }

    }


}
