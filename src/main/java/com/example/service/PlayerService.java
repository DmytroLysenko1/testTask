package com.example.service;

import com.example.dto.player.PlayerRequestDTO;
import com.example.dto.player.PlayerResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface PlayerService {
    PlayerResponseDTO fetchPlayerById(Long id);
    List<PlayerResponseDTO> fetchAllPlayers();
    PlayerResponseDTO saveRookiePlayer(PlayerRequestDTO playerRequestDTO);
    PlayerResponseDTO updatePlayer(Long id, PlayerRequestDTO playerRequestDTO);
    void increaseMarketValueForPlayerById(Long id, BigDecimal increaseAmount);
    void increaseSalaryForPlayerById(Long id, BigDecimal increaseAmount);
    String fetchPlayerNameById(Long id);
    String fetchPlayersLastNameById(Long id);
    void deletePlayer(Long id);
}
