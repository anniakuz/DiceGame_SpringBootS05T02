package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services;

import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Game;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlayGameServiceImpl implements PlayGameService{


    @Override
    public Game play() {
        int dice1 = Utils.getRandomNum();
        int  dice2 = Utils.getRandomNum();
        return Game.builder().dice1(dice1).dice2(dice2).win(getWin(dice1,dice2)).build();
    }



    public boolean getWin(int dice1, int dice2 ) {
        if(dice1+dice2 == 7) {
            return true;
        }
        return false;
    }





}
