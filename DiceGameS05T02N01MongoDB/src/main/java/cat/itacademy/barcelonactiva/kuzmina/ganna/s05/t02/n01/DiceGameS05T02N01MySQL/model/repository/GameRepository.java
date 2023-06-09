package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.repository;


import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Game;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {
    public List<Game> getGamesByPlayer(Player player);
}
