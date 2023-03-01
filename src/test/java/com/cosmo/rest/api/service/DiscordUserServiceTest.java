package com.cosmo.rest.api.service;

import com.cosmo.rest.api.exception.DiscordUserAlreadyExistsException;
import com.cosmo.rest.api.exception.DiscordUserInvalidException;
import com.cosmo.rest.api.exception.DiscordUserNotFoundException;
import com.cosmo.rest.api.model.DiscordUser;
import com.cosmo.rest.api.repository.DiscordUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class DiscordUserServiceTest {

    @MockBean
    DiscordUserRepository discordUserRepository;

    @Autowired
    DiscordUserService discordUserService;

    @Test
    public void createDiscordUser_With_InvalidId_ShouldThrow_DiscordUserInvalid() {
        DiscordUser discordUser = new DiscordUser( null, "Nickname","Username", "1234", 0 );
        assertThrows( DiscordUserInvalidException.class, () -> discordUserService.createDiscordUser( discordUser ) );
    }

    @Test
    public void createDiscordUser_With_IncompleteDiscordUser_ShouldThrow_DiscordUserInvalid() {
        DiscordUser discordUserNullUsername = new DiscordUser( "1", "Nickname",null, "1234", 0 );
        DiscordUser discordUserNullDiscriminator = new DiscordUser( "1", "Nickname","Username", null, 0 );
        DiscordUser discordUserNullCurrency = new DiscordUser( "1", "Nickname","Name", "1234", null );
        assertThrows( DiscordUserInvalidException.class, () -> discordUserService.createDiscordUser( discordUserNullUsername ) );
        assertThrows( DiscordUserInvalidException.class, () -> discordUserService.createDiscordUser( discordUserNullDiscriminator ) );
        assertThrows( DiscordUserInvalidException.class, () -> discordUserService.createDiscordUser( discordUserNullCurrency ) );
    }

    @Test
    public void createDiscordUser_WhileDiscordUserWithSameIdAlreadyExistsInDatabase_ShouldThrow_DiscordUserAlreadyExistsException() {
        DiscordUser discordUser = new DiscordUser( "1", "Nickname","Username", "1234", 0 );
        when( discordUserRepository.existsById( "1" ) ).thenReturn( true );
        assertThrows( DiscordUserAlreadyExistsException.class, () -> discordUserService.createDiscordUser( discordUser ) );
    }

    @Test
    public void createDiscordUser_AsComplete_ShouldNotThrow_AnException() {
        DiscordUser discordUser = new DiscordUser( "1", "Nickname","Username", "1234", 0 );
        assertDoesNotThrow( () -> discordUserService.createDiscordUser( discordUser ) );
    }

    @Test
    public void getDiscordUser_WhenIdIsNull_ShouldThrow_DiscordUserInvalidException() {
        assertThrows( DiscordUserInvalidException.class, () -> discordUserService.getDiscordUser( null ) );
    }

    @Test
    public void getDiscordUser_WhileDiscordUserDoesNotExistInDatabase_ShouldThrow_DiscordUserNotFoundException() {
        when( discordUserRepository.existsById( "1" ) ).thenReturn( false );
        assertThrows( DiscordUserNotFoundException.class, () -> discordUserService.getDiscordUser( "1" ) );
    }

    @Test
    public void getDiscordUser_WhenIdIsValid_AndDiscordUserExistsInDatabase_ShouldReturn_DiscordUser() {
        DiscordUser discordUser = new DiscordUser( "1", "Nickname","Username", "1234", 0 );
        when( discordUserRepository.existsById( "1" ) ).thenReturn( true );
        when ( discordUserRepository.findById( "1" ) ).thenReturn( Optional.of( discordUser ) );
        assertEquals( discordUser, discordUserService.getDiscordUser( "1" ) );
    }

    @Test
    public void updateDiscordUser_WithInvalidId_ShouldThrow_DiscordUserInvalidException() {
        DiscordUser discordUser = new DiscordUser( null, "Nickname","Username", "1234", 0 );
        assertThrows( DiscordUserInvalidException.class, () -> discordUserService.updateDiscordUser( discordUser ) );
    }

    @Test
    public void updateDiscordUser_WhileDiscordUserDoesNotExistInDatabase_ShouldThrow_DiscordUserNotFoundException() {
        DiscordUser discordUser = new DiscordUser( "1", "Nickname","Username", "1234", 0 );
        when( discordUserRepository.existsById( discordUser.getId() ) ).thenReturn( false );
        assertThrows( DiscordUserNotFoundException.class, () -> discordUserService.updateDiscordUser( discordUser ) );
    }

    @Test
    public void updateDiscordUser_WithNonCompleteDiscordUser_OnlyOverridesNonNullDatabaseBeanValues() {
        DiscordUser discordUserUpdateInput = new DiscordUser( "1", "Punjabi",null, null, 100 );
        DiscordUser discordUserFromDatabase = new DiscordUser( "1", "Nickname","Username", "1234", 0 );

        when( discordUserRepository.existsById( discordUserUpdateInput.getId() ) ).thenReturn( true );
        when ( discordUserRepository.findById( discordUserUpdateInput.getId() ) ).thenReturn( Optional.of( discordUserFromDatabase ) );
        discordUserService.updateDiscordUser( discordUserUpdateInput );

        ArgumentCaptor<DiscordUser> argumentCaptor = ArgumentCaptor.forClass( DiscordUser.class );
        verify( discordUserRepository ).save( argumentCaptor.capture() );
        DiscordUser updatedDiscordUser = argumentCaptor.getValue();

        assertThat( updatedDiscordUser ).usingRecursiveComparison().isEqualTo( new DiscordUser(
                discordUserFromDatabase.getId(),
                discordUserUpdateInput.getNickname(),
                discordUserFromDatabase.getUsername(),
                discordUserFromDatabase.getDiscriminator(),
                discordUserUpdateInput.getCurrency()
        ) );
    }

    @Test
    public void deleteDiscordUser_WhenIdIsNull_ShouldThrow_DiscordUserInvalidException() {
        assertThrows( DiscordUserInvalidException.class, () -> discordUserService.deleteDiscordUser( null ) );
    }

    @Test
    public void deleteDiscordUser_WhileDiscordUserDoesNotExistInDatabase_ShouldThrow_DiscordUserNotFoundException() {
        when( discordUserRepository.existsById( "1" ) ).thenReturn( false );
        assertThrows( DiscordUserNotFoundException.class, () -> discordUserService.deleteDiscordUser( "1" ) );
    }

    @Test
    public void deleteDiscordUser_WhileDiscordUserDoesExistInDatabase_ShouldNotThrow_AnException() {
        when( discordUserRepository.existsById( "1" ) ).thenReturn( true );
        assertDoesNotThrow( () -> discordUserService.deleteDiscordUser( "1" ) );
    }

}