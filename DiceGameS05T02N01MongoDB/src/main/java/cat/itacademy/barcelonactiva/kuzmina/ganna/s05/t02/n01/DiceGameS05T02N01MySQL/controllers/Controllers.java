package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.controllers;

import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.exceptions.HttpException;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Game;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Player;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services.GameService;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/players")
public class Controllers {
    private final PlayerService playerService;
    private final GameService gameService;
    @PostMapping("/add")
    public ResponseEntity<?> addPlayer(@RequestBody Player player){
        Player savedPlayer;
        try {
            savedPlayer = playerService.createPlayer(player);
        }catch(HttpException ex){
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPlayer);
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllPlayers(){
        try{
            playerService.getAllPlayers();
        }catch(HttpException ex){
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(playerService.getAllPlayers());
    }

    @GetMapping("/rates")
    public ResponseEntity<?> getAllPlayersRates(){
        try{
            playerService.getAllPlayersRates();
        }catch(HttpException ex){
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(playerService.getAllPlayersRates());
    }



   @PutMapping("/editName/{id}")
    public ResponseEntity<?> updatePlayer(@RequestBody Player player, @PathVariable String id){
        try{
            Player updatedPlayer = playerService.editPlayer(id,player);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedPlayer);
        }catch(HttpException ex){
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }

    }

    @PostMapping("/{id}/games")
    public ResponseEntity<?> playGame(@PathVariable String id) {

        Game gameToSave;
        try {
            gameToSave = gameService.playGame(id);
        } catch (HttpException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
            return ResponseEntity.status(HttpStatus.CREATED).body(gameToSave);

    }

    @DeleteMapping("/{id}/games")
    public ResponseEntity<?> deleteAllGamesByPlayerId(@PathVariable String id){
        try {
            playerService.deleteAllGames(id);
            gameService.deleteAllGames(id);
        } catch (HttpException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(playerService.getAllPlayers());
    }

    @GetMapping("/{id}/games")
    public ResponseEntity<?> getAllGameOfPlayer(@PathVariable String id){
        try {
            gameService.getAllGamesWithPlayerId(id);
        } catch (HttpException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getAllGamesWithPlayerId(id));
    }

    @GetMapping("/ranking")
    public ResponseEntity<?> getRankingForAll(){
        try{
            playerService.getRateForAll();
        }catch(HttpException ex){
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(playerService.getRateForAll());
    }

    @GetMapping("/ranking/loser")
    public ResponseEntity<?> getLoser(){
        try {

            List<Player> losers =  playerService.getPlayerWithTheLowestRate();

        } catch (HttpException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(playerService.getPlayerWithTheLowestRate());
    }


    @GetMapping("/ranking/winner")
    public ResponseEntity<?> getWinner() {
        try {

            List<Player> winners =  playerService.getPlayerWithTheHighestRate();

        } catch (HttpException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(playerService.getPlayerWithTheHighestRate());
    }


}
