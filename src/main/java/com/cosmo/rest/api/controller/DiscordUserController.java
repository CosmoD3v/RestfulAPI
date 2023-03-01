package com.cosmo.rest.api.controller;

import com.cosmo.rest.api.model.DiscordUser;
import com.cosmo.rest.api.service.DiscordUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/discord-users")
public class DiscordUserController {

    private DiscordUserService discordUserService;

    @Autowired
    public DiscordUserController(DiscordUserService discordUserService) {
        this.discordUserService = discordUserService;
    }

    @RequestMapping( value = "", method = RequestMethod.POST )
    public void saveUser(@RequestBody DiscordUser discordUser) {
        discordUserService.createDiscordUser( discordUser );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public DiscordUser getDiscordUser( @PathVariable("id") String id ) {
        return discordUserService.getDiscordUser( id );
    }

    @RequestMapping( value = "", method = RequestMethod.PUT )
    public void updateUser( @RequestBody DiscordUser discordUser ) {
        discordUserService.updateDiscordUser( discordUser );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
    public void deleteUser( @PathVariable String id ) {
        discordUserService.deleteDiscordUser( id );
    }

}
