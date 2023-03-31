package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services;

import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Game;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlayGameServiceImpl implements PlayGameService{
    private static final int MIN_NUM = 1;
    private static final int MAX_NUM = 6;

    @Override
    public Game play() {
        int dice1 = getRandomNum();
        int  dice2 = getRandomNum();
        return Game.builder().dice1(dice1).dice2(dice2).win(getWin(dice1,dice2)).build();
    }



    public boolean getWin(int dice1, int dice2 ) {
        if(dice1+dice2 == 7) {
            return true;
        }
        return false;
    }

    public int getRandomNum(){
        return (int) (Math.random() * ( MAX_NUM - MIN_NUM ));
    }



}
