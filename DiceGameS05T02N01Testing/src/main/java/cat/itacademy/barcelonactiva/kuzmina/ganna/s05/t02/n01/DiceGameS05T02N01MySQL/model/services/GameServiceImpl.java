package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services;

import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.exceptions.HttpException;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Game;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Player;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService{

    private final GameRepository gameRepository;
    private final PlayerService playerService;
    private  final PlayGameServiceImpl playGameService;
    @Override
    public Game playGame(Integer playerId) {
        if(playerService.getAllPlayers().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no one");
        }
        Player player = playerService.findPlayerById(playerId);
        if(player==null){
            throw new HttpException(HttpStatus.NOT_FOUND,"Player with this id is not found");
        }

        Game gamePlayed = playGameService.play();
        gamePlayed.setPlayer(player);

        return gameRepository.save(gamePlayed);
    }


    @Override
    public List<Game> getAllGamesWithPlayerId(Integer playerId){
        if(playerService.getAllPlayers().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no one");
        }
        return gameRepository.getAllByPlayer(Player.builder().playerId(playerId).build());// gameRepository.findAll().stream().filter(game -> Objects.equals(game.getPlayer().getPlayerId(), playerId)).toList();

      //  return allGames;
    }

    @Override
    public boolean deleteAllGames(Integer playerId){
       /* if(playerService.getAllPlayers().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no one");
        }*/
        List<Game> allGames = getAllGamesWithPlayerId(playerId);
        if(allGames.isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no one game");
        }

        for(int i=0;i<allGames.size();i++){
            gameRepository.deleteById(allGames.get(i).getGameId());
        }
        return true;
    }


}
