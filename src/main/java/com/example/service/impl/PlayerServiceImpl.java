package com.example.service.impl;

import com.example.dto.player.PlayerRequestDTO;
import com.example.dto.player.PlayerResponseDTO;
import com.example.entity.Player;
import com.example.entity.Team;
import com.example.exceptions.NotFoundException;
import com.example.repository.PlayerRepository;
import com.example.repository.TeamRepository;
import com.example.service.PlayerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;

    public PlayerServiceImpl(TeamRepository teamRepository, ModelMapper modelMapper, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
        this.playerRepository = playerRepository;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public PlayerResponseDTO fetchPlayerById(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found with id: " + id));
        return modelMapper.map(player, PlayerResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<PlayerResponseDTO> fetchAllPlayers() {
        return playerRepository.findAll().stream()
                .map(player -> modelMapper.map(player, PlayerResponseDTO.class))
                .toList();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public PlayerResponseDTO saveRookiePlayer(PlayerRequestDTO playerRequestDTO) {
        Player player = modelMapper.map(playerRequestDTO, Player.class);
        Team team = teamRepository.findById(playerRequestDTO.getTeamId())
                .orElseThrow(() -> new NotFoundException("Team not found with id: " + playerRequestDTO.getTeamId()));
        player.setTeam(team);
        player = playerRepository.save(player);
        return modelMapper.map(player, PlayerResponseDTO.class);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public PlayerResponseDTO updatePlayer(Long id, PlayerRequestDTO playerRequestDTO) {
        Player existingPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found with id: " + id));
        modelMapper.map(playerRequestDTO, existingPlayer);
        Team team = teamRepository.findById(playerRequestDTO.getTeamId())
                .orElseThrow(() -> new NotFoundException("Team not found with id: " + playerRequestDTO.getTeamId()));
        existingPlayer.setTeam(team);
        Player updatedPlayer = playerRepository.save(existingPlayer);
        return modelMapper.map(updatedPlayer, PlayerResponseDTO.class);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public void increaseMarketValueForPlayerById(Long id, BigDecimal increaseAmount) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found with id: " + id));
        player.setMarketValue(player.getMarketValue().add(increaseAmount));
        playerRepository.save(player);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public void increaseSalaryForPlayerById(Long id, BigDecimal increaseAmount) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found with id: " + id));
        player.setSalary(player.getSalary().add(increaseAmount));
        playerRepository.save(player);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public String fetchPlayerNameById(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found with id: " + id));
        return player.getName();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public String fetchPlayersLastNameById(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found with id: " + id));
        return player.getLastName();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public void deletePlayer(Long playerId) {
        Player player = playerRepository.findByIdWithLock(playerId)
                .orElseThrow(() -> new NotFoundException("Player not found"));
        playerRepository.deleteById(playerId);
    }
}
