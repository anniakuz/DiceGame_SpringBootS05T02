package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name="games")
public class Game {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer gameId;
    private int dice1;
    private int dice2;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="player_id")
    Player player;
    private boolean win;


/*
    public void roll(){
        dice1 = setRandomNum();
        dice2 = setRandomNum();
    }


    public boolean getWin() {
        if(dice1+dice2 == 7) {
            this.win = true;
        }else{
            this.win= false;
        }
        return this.win;
    }

    public int setRandomNum(){
        int min_num = 1;
        int max_num = 6;
        return (int) (Math.random() * ( max_num - min_num ));
    }*/
}
