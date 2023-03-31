package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services;


import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Game;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Player;

import java.util.List;
import java.util.Map;

public interface PlayerService {
    public Player findPlayerById(String playerId);

    public Player createPlayer(Player player);

    void savePlayer(Player player);

    public List<Player> getAllPlayers();

    Map<String, Double> getAllPlayersRates();

    Player editPlayer(String playerId, Player player);

    //how to get Player if
    void deleteAllGames(String playerId);

    Double getRateForAll();

    List<Player> getPlayerWithTheLowestRate();

    List<Player> getPlayerWithTheHighestRate();
}
