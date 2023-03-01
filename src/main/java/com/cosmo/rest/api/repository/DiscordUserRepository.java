package com.cosmo.rest.api.repository;

import com.cosmo.rest.api.model.DiscordUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscordUserRepository extends JpaRepository<DiscordUser, String> {
}
