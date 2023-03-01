package com.cosmo.rest.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "discord_user")
public class DiscordUser {

    @Id
    private String id;

    @Column
    private String nickname;

    @Column
    private String username;

    @Column
    private String discriminator;

    @Column
    private Integer currency;

    public DiscordUser() {}

    public DiscordUser(String id, String nickname, String username, String discriminator, Integer currency) {
        this.id = id;
        this.nickname = nickname;
        this.username = username;
        this.discriminator = discriminator;
        this.currency = currency;
    }

    public boolean hasValidId() {
        return this.id != null;
    }

    @JsonIgnore
    public boolean isComplete() {
        return ( this.id != null && this.username != null && this.discriminator != null && this.currency != null );
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() { return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

}
