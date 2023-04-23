package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.utils;

public class Utils {
    private static final int MIN_NUM = 1;
    private static final int MAX_NUM = 6;
    public static int getRandomNum(){
        return (int) (Math.random() * ( MAX_NUM - MIN_NUM ));
    }
}
