package me.iolsh.homepage.repositories;

import me.iolsh.homepage.model.RememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component("tokenRepository")
public class PersistentTokenRepositoryImpl implements PersistentTokenRepository {

    RememberMeTokenRepository rememberMeTokenRepository;

    public PersistentTokenRepositoryImpl(RememberMeTokenRepository rememberMeTokenRepository) {
        this.rememberMeTokenRepository = rememberMeTokenRepository;
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        RememberMeToken newToken = new RememberMeToken(token);
        rememberMeTokenRepository.save(newToken);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        Optional<RememberMeToken> tokenOptional = rememberMeTokenRepository.findBySeries(series);
        if(tokenOptional.isPresent()) {
            RememberMeToken token = tokenOptional.get();
            token.setTokenValue(tokenValue);
            token.setDate(lastUsed);
            rememberMeTokenRepository.save(token);
        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        Optional<RememberMeToken> tokenOptional = rememberMeTokenRepository.findBySeries(seriesId);
        RememberMeToken token = tokenOptional.orElse(null);
        PersistentRememberMeToken persistentRememberMeToken = null;
        if (token != null) {
            persistentRememberMeToken = new PersistentRememberMeToken(
                    token.getUserName(), token.getSeries(), token.getTokenValue(), token.getDate());
        }
        return persistentRememberMeToken;
    }

    @Override
    public void removeUserTokens(String username) {
        Optional<RememberMeToken> tokenOptional = rememberMeTokenRepository.findByUserName(username);
        if(tokenOptional.isPresent()) {
            rememberMeTokenRepository.delete(tokenOptional.get());
        }
    }
}
