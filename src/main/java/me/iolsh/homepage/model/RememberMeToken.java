package me.iolsh.homepage.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "remember_me_tokens")
public class RememberMeToken {

    @Id
    private String series;
    @Column
    private String userName;
    @Column
    private String tokenValue;
    @Column
    private Date date;

    public RememberMeToken(PersistentRememberMeToken token) {
        this.series = token.getSeries();
        this.date = token.getDate();
        this.tokenValue = token.getTokenValue();
        this.userName = token.getUsername();
    }
}
