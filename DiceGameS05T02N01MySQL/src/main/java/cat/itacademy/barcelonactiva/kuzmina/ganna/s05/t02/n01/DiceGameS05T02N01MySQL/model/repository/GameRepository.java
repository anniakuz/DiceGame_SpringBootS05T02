package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.repository;


import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
}
