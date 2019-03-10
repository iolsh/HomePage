package me.iolsh.homepage.repositories;

import me.iolsh.homepage.model.RememberMeToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RememberMeTokenRepository extends JpaRepository<RememberMeToken, String> {

    Optional<RememberMeToken> findBySeries(String series);

    Optional<RememberMeToken> findByUserName(String username);

}
