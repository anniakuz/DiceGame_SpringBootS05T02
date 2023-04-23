package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
@Document(collection = "player")
public class Player {
    @Id
    private String playerId;
    private String name;
    private LocalDate registerDate;
    @Transient
    Double rate;

    @JsonIgnore
    @DBRef(lazy = true)
    private List<Game> games;

    public Player(String name) {
        this.name = name;
        this.registerDate = LocalDate.now();
    }

   /* public List<Game> addGame(Game game){
        games.add(game);
        return this.games=games;
    }*/

    public Double calculateRate(){
        Double rate = 0.0;
        if(games!=null&&!games.isEmpty()) {
            int winGames = games.stream().filter(game -> game.isWin()).toList().size();
            rate = (double) ((winGames * 100) / games.size());
            this.rate = rate;
            return rate;
        }
        else {
            this.rate = rate;
            return   rate;
        }
    }

}
