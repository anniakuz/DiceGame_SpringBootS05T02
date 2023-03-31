package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services;


import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Game;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Player;

import java.util.List;
import java.util.Map;

public interface PlayerService {
    public Player findPlayerById(Integer playerId);


    //POST: /players: create a player // создать игрока
    public Player createPlayer(Player player);
    public List<Player> getAllPlayers();

    Map<String, Double> getAllPlayersRates();

    Player editPlayer(Integer playerId, Player player);

    //how to get Player if
    void deleteAllGames(Integer playerId);

    Double getRateForAll();

    List<Player> getPlayerWithTheLowestRate();

    List<Player> getPlayerWithTheHighestRate();


}
