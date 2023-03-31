package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services;


import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.exceptions.HttpException;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Game;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Player;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;


    @Override
    public Player findPlayerById(String playerId) {
        if(playerRepository.findAll().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no one");
        }
        return playerRepository.findById(playerId).orElseThrow(()-> new HttpException(HttpStatus.NOT_FOUND,
                "Could not find any player with ID "+playerId));

    }

    @Override
    public Player createPlayer(Player player) {
        if(playerRepository.getPlayerByName(player.getName())!=null){
            throw new HttpException(HttpStatus.BAD_REQUEST,"Player with this name already exist");
        }
        Player playerForSave = new Player(player.getName());
        return playerRepository.save(playerForSave);
    }

    @Override
    public void savePlayer(Player player){
        playerRepository.save(player);
    }
    @Override
    public List<Player> getAllPlayers(){
        if(playerRepository.findAll().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no registered player");
        }
        List<Player> allPlayers = playerRepository.findAll();
        allPlayers.forEach(Player::calculateRate);
        return playerRepository.findAll();

    }

    @Override
    public Map<String, Double> getAllPlayersRates(){
        if(playerRepository.findAll().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no registered player");
        }
        List<Player> allPlayers = playerRepository.findAll();
        allPlayers.forEach(Player::calculateRate);
        Map<String, Double> allPlayersRates = allPlayers.stream().
                collect(Collectors.toMap(Player::getName, Player::calculateRate));
        System.out.println(allPlayersRates);
        return allPlayersRates;

    }

    @Override
    public Player editPlayer(String playerId, Player player) {
        if(playerRepository.findAll().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no registered player");
        }
        playerRepository.findById(playerId).orElseThrow(()-> new HttpException(HttpStatus.NOT_FOUND,
                "Could not find any player with ID "+playerId));
        if(playerRepository.getPlayerByName(player.getName())!=null){
            throw new HttpException(HttpStatus.BAD_REQUEST,"Player with this name already exist");
        }
        Player playerUpdated = playerRepository.findById(playerId).get();
        playerUpdated.setName(player.getName());
        return playerRepository.save(playerUpdated);

    }

    @Override
    public void deleteAllGames(String playerId) {
        if(playerRepository.findAll().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no registered player");
        }
        Player player = playerRepository.findById(playerId).orElseThrow(()-> new HttpException(HttpStatus.NOT_FOUND,
                "Could not find any player with ID "+playerId));
        List.copyOf(player.getGames()).forEach(player.getGames()::remove);
    }

    @Override
    public Double getRateForAll(){
        if(playerRepository.findAll().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"TThere is no registered player");
        }
        Double rate = getAllPlayers().stream().mapToDouble(Player::calculateRate).sum()/getAllPlayers().size();
        return rate;
    }

    @Override
    public List<Player> getPlayerWithTheLowestRate(){
        List<Player> listPlayersMin = null;
        if(playerRepository.findAll().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no registered player");
        }
        Comparator<Player> comparator = Comparator.comparing(Player::calculateRate);
        Player playerMin = getAllPlayers().stream().min(comparator).get();
        listPlayersMin = playerRepository.findAll().stream().
                filter(player -> Objects.equals(player.calculateRate(), playerMin.calculateRate())).collect(Collectors.toList());
        return listPlayersMin;
    }

    @Override
    public List<Player> getPlayerWithTheHighestRate(){
        List<Player> listPlayersMax = null;
        if(playerRepository.findAll().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no registered player");
        }
        Comparator<Player> comparator = Comparator.comparing(Player::calculateRate);
        Player playerMax = getAllPlayers().stream().max(comparator).get();
        listPlayersMax = playerRepository.findAll().stream().
                filter(player -> Objects.equals(player.calculateRate(), playerMax.calculateRate())).collect(Collectors.toList());
        return listPlayersMax;
    }

}
