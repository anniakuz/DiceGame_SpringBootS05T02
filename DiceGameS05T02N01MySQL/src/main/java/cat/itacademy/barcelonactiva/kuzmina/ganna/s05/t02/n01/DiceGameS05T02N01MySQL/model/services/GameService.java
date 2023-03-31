package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services;

import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Game;

import java.util.List;

public interface GameService {
    public Game playGame(Integer playerId);

    List<Game> getAllGamesWithPlayerId(Integer playerId);

    public void deleteAllGames(Integer playerId);
}
