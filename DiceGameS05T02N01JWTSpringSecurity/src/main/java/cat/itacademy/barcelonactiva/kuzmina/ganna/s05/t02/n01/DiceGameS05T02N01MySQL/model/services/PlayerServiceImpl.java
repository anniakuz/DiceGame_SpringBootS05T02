package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.services;


import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.exceptions.HttpException;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Player;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain.Role;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final PlayerRepository playerRepository;
    @Override
    public Player findPlayerById(Integer playerId) {
        if(playerRepository.findAll().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no one");
        }
        return playerRepository.findById(playerId).orElseThrow(()-> new HttpException(HttpStatus.NOT_FOUND,
                "Could not find any player with ID "+playerId));

    }

    @Override
    public Player createPlayer(Player player) {
        Player playerForSave = new Player(player.getName());
        playerForSave.setPassword(passwordEncoder.encode(player.getPassword()));
        playerForSave.setRole(Role.USER);
        return playerRepository.save(playerForSave);
    }
    @Override
    public List<Player> getAllPlayers(){
        if(playerRepository.findAll().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no registered player");
        }
        List<Player> allPlayers = playerRepository.findAll();
        allPlayers.forEach(Player::calculateRate);
        return playerRepository.findAll();

    }

    //doesn't work
    @Override
    public Map<String, Double> getAllPlayersRates(){
        if(playerRepository.findAll().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no registered player");
        }
        List<Player> allPlayers = playerRepository.findAll();
        allPlayers.forEach(Player::calculateRate);
        Map<String, Double> allPlayersRates = allPlayers.stream().
                collect(Collectors.toMap(Player::getName, Player::getRate));
        System.out.println(allPlayersRates);
        return allPlayersRates;

    }

    //doesn't work
    @Override
    public Player editPlayer(Integer playerId, Player player) {
        if(playerRepository.findAll().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no registered player");
        }
        playerRepository.findById(playerId).orElseThrow(()-> new HttpException(HttpStatus.NOT_FOUND,
                "Could not find any player with ID "+playerId));
        if(playerRepository.getPlayerByName(player.getName())!=null){
            throw new HttpException(HttpStatus.BAD_REQUEST,"Player with this name already exist");
        }
        Player playerUpdated = playerRepository.findById(playerId).get();
        playerUpdated.setName(player.getName());
        return playerRepository.save(playerUpdated);

    }

    @Override
    public void deleteAllGames(Integer playerId) {
        if(playerRepository.findAll().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no registered player");
        }
            Player player = playerRepository.findById(playerId).orElseThrow(()-> new HttpException(HttpStatus.NOT_FOUND,
                    "Could not find any player with ID "+playerId));
        List.copyOf(player.getGames()).stream().forEach(player.getGames()::remove);
    }

    @Override
    public Double getRateForAll(){
        if(playerRepository.findAll().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"TThere is no registered player");
        }
        Double rate = getAllPlayers().stream().mapToDouble(Player::getRate).sum()/getAllPlayers().size();
        return rate;
    }

    @Override
    public List<Player> getPlayerWithTheLowestRate(){
        List<Player> listPlayersMin = null;
        if(playerRepository.findAll().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no registered player");
        }
        Comparator<Player> comparator = Comparator.comparing(Player::getRate);
        Player playerMin = getAllPlayers().stream().min(comparator).get();
        listPlayersMin = playerRepository.findAll().stream().
                filter(player -> Objects.equals(player.getRate(), playerMin.getRate())).collect(Collectors.toList());
        return listPlayersMin;
    }

    @Override
    public List<Player> getPlayerWithTheHighestRate(){
        List<Player> listPlayersMax = null;
        if(playerRepository.findAll().isEmpty()){
            throw new HttpException(HttpStatus.INSUFFICIENT_STORAGE,"There is no registered player");
        }
        Comparator<Player> comparator = Comparator.comparing(Player::getRate);
        Player playerMax = getAllPlayers().stream().max(comparator).get();
        listPlayersMax = playerRepository.findAll().stream().
                filter(player -> Objects.equals(player.getRate(), playerMax.getRate())).collect(Collectors.toList());
        return listPlayersMax;
    }
    @Override
    public String login(Player player) {
        Player player1 = playerRepository.getPlayerByName(player.getName());
        if(player1==null||!passwordEncoder.matches(player.getPassword(), player1.getPassword())){
            throw new HttpException(HttpStatus.UNAUTHORIZED,"Login or password is not valid");
        }
        return jwtService.generateToken(player1);
    }

}
