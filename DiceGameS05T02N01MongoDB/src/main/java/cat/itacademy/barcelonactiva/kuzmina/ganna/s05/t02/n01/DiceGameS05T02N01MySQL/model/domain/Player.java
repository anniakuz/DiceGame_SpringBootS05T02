package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(collection = "player")
public class Player {
    @Id
    private String playerId;
    private String name;
    private LocalDate registerDate;
    @Transient
    Double rate;

    @JsonIgnore
    @DBRef
    private List<Game> games = new ArrayList<>();

    //{games = new ArrayList<>();}

    public Player(String name) {
        this.name = name;
        this.registerDate = LocalDate.now();
    }

    public List<Game> addGame(Game game){
        games.add(game);
        return this.games=games;
    }

    public Double calculateRate(){
        Double rate = 0.0;
        if(games!=null&&!games.isEmpty()) {
            int winGames = games.stream().filter(game -> game.isWin()).toList().size();
            rate = (double) ((winGames * 100) / games.size());
            return this.rate = rate;
        }
        else {
            return this.rate = rate;
        }
    }

}
