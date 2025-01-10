package com.example.service;

import com.example.dto.player.PlayerRequestDTO;
import com.example.dto.player.PlayerResponseDTO;
import com.example.entity.Player;
import com.example.entity.Team;
import com.example.exceptions.NotFoundException;
import com.example.repository.PlayerRepository;
import com.example.repository.TeamRepository;
import com.example.service.impl.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetchPlayerById_ShouldReturnPlayerResponseDTO() {
        Long playerId = 1L;
        Player player = new Player();
        PlayerResponseDTO playerResponseDTO = new PlayerResponseDTO();

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(modelMapper.map(player, PlayerResponseDTO.class)).thenReturn(playerResponseDTO);

        PlayerResponseDTO result = playerService.fetchPlayerById(playerId);

        assertNotNull(result);
        verify(playerRepository).findById(playerId);
        verify(modelMapper).map(player, PlayerResponseDTO.class);
    }

    @Test
    void fetchPlayerById_ShouldThrowNotFoundException() {
        Long playerId = 1L;

        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> playerService.fetchPlayerById(playerId));
        verify(playerRepository).findById(playerId);
    }

    @Test
    void fetchAllPlayers_ShouldReturnListOfPlayerResponseDTO() {
        List<Player> players = List.of(new Player());
        List<PlayerResponseDTO> playerResponseDTOs = List.of(new PlayerResponseDTO());

        when(playerRepository.findAll()).thenReturn(players);
        when(modelMapper.map(any(Player.class), eq(PlayerResponseDTO.class))).thenReturn(new PlayerResponseDTO());

        List<PlayerResponseDTO> result = playerService.fetchAllPlayers();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(playerRepository).findAll();
        verify(modelMapper, times(players.size())).map(any(Player.class), eq(PlayerResponseDTO.class));
    }

    @Test
    void savePlayer_ShouldReturnPlayerResponseDTO() {
        PlayerRequestDTO playerRequestDTO = new PlayerRequestDTO();
        playerRequestDTO.setTeamId(1L);
        Player player = new Player();
        PlayerResponseDTO playerResponseDTO = new PlayerResponseDTO();
        Team team = new Team();

        when(modelMapper.map(playerRequestDTO, Player.class)).thenReturn(player);
        when(playerRepository.save(player)).thenReturn(player);
        when(modelMapper.map(player, PlayerResponseDTO.class)).thenReturn(playerResponseDTO);
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team)); // Mock teamRepository to return a valid Team

        PlayerResponseDTO result = playerService.saveRookiePlayer(playerRequestDTO);

        assertNotNull(result);
        verify(modelMapper).map(playerRequestDTO, Player.class);
        verify(playerRepository).save(player);
        verify(modelMapper).map(player, PlayerResponseDTO.class);
        verify(teamRepository).findById(1L);
    }

    @Test
    void updatePlayer_ShouldReturnUpdatedPlayerResponseDTO() {
        Long playerId = 1L;
        PlayerRequestDTO playerRequestDTO = new PlayerRequestDTO();
        playerRequestDTO.setTeamId(1L); // Ensure teamId is set
        Player existingPlayer = new Player();
        PlayerResponseDTO playerResponseDTO = new PlayerResponseDTO();
        Team team = new Team();

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(existingPlayer));
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team)); // Mock teamRepository to return a valid Team
        doAnswer(invocation -> {
            PlayerRequestDTO source = invocation.getArgument(0);
            Player destination = invocation.getArgument(1);
            destination.setName(source.getName());
            return null;
        }).when(modelMapper).map(playerRequestDTO, existingPlayer);
        when(playerRepository.save(existingPlayer)).thenReturn(existingPlayer);
        when(modelMapper.map(existingPlayer, PlayerResponseDTO.class)).thenReturn(playerResponseDTO);

        PlayerResponseDTO result = playerService.updatePlayer(playerId, playerRequestDTO);

        assertNotNull(result);
        verify(playerRepository).findById(playerId);
        verify(teamRepository).findById(1L);
        verify(modelMapper).map(playerRequestDTO, existingPlayer);
        verify(playerRepository).save(existingPlayer);
        verify(modelMapper).map(existingPlayer, PlayerResponseDTO.class);
    }

    @Test
    void deletePlayer_ShouldDeletePlayer() {
        Long playerId = 1L;
        Player existingPlayer = new Player();
        existingPlayer.setId(playerId); // Make sure playerId is set.

        // Mock the behavior of the playerRepository to return the player when findById is called.
        when(playerRepository.findByIdWithLock(playerId)).thenReturn(Optional.of(existingPlayer));

        // Call the service method
        playerService.deletePlayer(playerId);

        // Verify that the findById method was called with the correct playerId.
        verify(playerRepository).findByIdWithLock(playerId);

        // Verify that deleteById was called with the correct playerId.
        verify(playerRepository).deleteById(playerId);
    }
}
