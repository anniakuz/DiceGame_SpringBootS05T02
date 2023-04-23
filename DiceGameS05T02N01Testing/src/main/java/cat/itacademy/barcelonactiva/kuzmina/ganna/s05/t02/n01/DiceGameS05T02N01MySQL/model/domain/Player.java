package cat.itacademy.barcelonactiva.kuzmina.ganna.s05.t02.n01.DiceGameS05T02N01MySQL.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name="players")
public class Player implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer playerId;
    @Column(nullable = false, unique = true,name="name")
    private String name;
    @Column(nullable = false,name="registerDate")
    private LocalDate registerDate;
    @Transient
    Double rate;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "player")
    private List<Game> games;

    public Player(String name) {
        this.name = name;
        this.registerDate = LocalDate.now();
    }

    public void calculateRate(){
        if(games!=null&&!games.isEmpty()) {
            int winGames = games.stream().filter(game -> game.isWin()).toList().size();
            Double rate = (double) ((winGames * 100) / games.size());
            this.rate=rate;
        }
        else {
            this.rate = 0.0;
        }
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
