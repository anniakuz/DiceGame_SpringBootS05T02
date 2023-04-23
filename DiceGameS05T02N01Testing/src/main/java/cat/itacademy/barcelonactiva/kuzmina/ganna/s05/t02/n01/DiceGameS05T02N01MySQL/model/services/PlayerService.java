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
    //void deleteAllGames(Integer playerId);

    Double getRateForAll();

    List<Player> getPlayerWithTheLowestRate();

    List<Player> getPlayerWithTheHighestRate();

    String login(Player player);
    /*
    //PUT /players: edit player's name //поменять имя
    public Player editPlayer(Integer playerId, Player playerDTO);
*/
    //POST /players/{id}/games/ : one player play 1 game //игрок совершает 1 партию

/*
    //DELETE /players/{id}/games: delete all game of one player // удалнть все партии одного игрока
    public void deleteAllGames(Integer playerId);

    //GET /players/: return the list of players with their average success rate // показать всех игров с их процент успеха
    public List<?> showAllThePlayersAndRates();//????????????????

    //GET /players/{id}/games: return the list of all the game of 1 player // показать все партии одного игрока
    public List<Game> showAllTheGames(Integer playerId);

    //GET /players/ranking: return the average success rate of all the players (only one rate for all) // показать общий процент успеха всех играков
    public Double showGlobalRate();

    //GET /players/ranking/loser: return the player with the lowest rate // показать худшего игрока
    public Player showPlayerWithLowestRate();

    //GET /players/ranking/winner: return the player with the highest rate // показать лучшего игрока
    public Player showPlayerWithHighestRate();*/

}
