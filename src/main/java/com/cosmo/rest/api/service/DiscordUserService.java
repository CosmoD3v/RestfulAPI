package com.cosmo.rest.api.service;

import com.cosmo.rest.api.exception.DiscordUserAlreadyExistsException;
import com.cosmo.rest.api.exception.DiscordUserInvalidException;
import com.cosmo.rest.api.exception.DiscordUserNotFoundException;
import com.cosmo.rest.api.model.DiscordUser;
import com.cosmo.rest.api.repository.DiscordUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscordUserService {

    private DiscordUserRepository discordUserRepository;

    @Autowired
    public DiscordUserService(DiscordUserRepository discordUserRepository) {
        this.discordUserRepository = discordUserRepository;
    }

    public void createDiscordUser( DiscordUser discordUser ) throws DiscordUserInvalidException, DiscordUserAlreadyExistsException {
        if ( !discordUser.hasValidId() || !discordUser.isComplete() ) {
            throw new DiscordUserInvalidException();
        }

        boolean discordUserExists = discordUserRepository.existsById( discordUser.getId() );
        if ( discordUserExists ) {
            throw new DiscordUserAlreadyExistsException();
        }

        discordUserRepository.save(discordUser);
    }
    public DiscordUser getDiscordUser( String id ) throws DiscordUserInvalidException, DiscordUserNotFoundException {
        if ( id == null ) {
            throw new DiscordUserInvalidException();
        }

        boolean discordUserExists = discordUserRepository.existsById( id );
        if ( !discordUserExists ) {
            throw new DiscordUserNotFoundException();
        }

        return discordUserRepository.findById( id ).get();
    }

    public void updateDiscordUser( DiscordUser discordUser ) throws DiscordUserInvalidException, DiscordUserNotFoundException {
        if ( !discordUser.hasValidId() ) {
            throw new DiscordUserInvalidException();
        }

        boolean discordUserExists = discordUserRepository.existsById( discordUser.getId() );
        if ( !discordUserExists ) {
            throw new DiscordUserNotFoundException();
        }

        DiscordUser updatedDiscordUser = discordUserRepository.findById( discordUser.getId() ).get();
        updatedDiscordUser.setCurrency( discordUser.getCurrency() == null ? updatedDiscordUser.getCurrency() : discordUser.getCurrency() );
        updatedDiscordUser.setNickname( discordUser.getNickname() == null ? updatedDiscordUser.getNickname() : discordUser.getNickname() );
        updatedDiscordUser.setUsername( discordUser.getUsername() == null ? updatedDiscordUser.getUsername() : discordUser.getUsername() );
        updatedDiscordUser.setDiscriminator( discordUser.getDiscriminator() == null ? updatedDiscordUser.getDiscriminator() : discordUser.getDiscriminator() );
        discordUserRepository.save( updatedDiscordUser );
    }

    public void deleteDiscordUser( String id ) throws DiscordUserInvalidException, DiscordUserNotFoundException {
        if ( id == null ) {
            throw new DiscordUserInvalidException();
        }

        boolean discordUserExists = discordUserRepository.existsById( id );
        if ( !discordUserExists ) {
            throw new DiscordUserNotFoundException();
        }
        discordUserRepository.deleteById( id );
    }

}
