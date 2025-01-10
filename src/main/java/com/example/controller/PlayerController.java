package com.example.controller;


import com.example.dto.player.PlayerRequestDTO;
import com.example.dto.player.PlayerResponseDTO;
import com.example.entity.Player;
import com.example.service.PlayerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;
    private final ModelMapper modelMapper;

    public PlayerController(PlayerService playerService, ModelMapper modelMapper) {
        this.playerService = playerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<Player>> fetchAllPlayers() {
        List<PlayerResponseDTO> playerResponseDTOList = playerService.fetchAllPlayers();
        return ResponseEntity.ok(playerResponseDTOList.stream()
                .map(playerResponseDTO -> modelMapper.map(playerResponseDTO, Player.class))
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> fetchPlayerById(@PathVariable Long id) {
        PlayerResponseDTO playerResponseDTO = playerService.fetchPlayerById(id);
        return ResponseEntity.ok(modelMapper.map(playerResponseDTO, Player.class));
    }

    @GetMapping("/{id}/name")
    public ResponseEntity<String> fetchPlayerNameById(@PathVariable Long id) {
        String playerName = playerService.fetchPlayerNameById(id);
        return ResponseEntity.ok(playerName);
    }

    @GetMapping("/{id}/last-name")
    public ResponseEntity<String> fetchPlayersLastNameById(@PathVariable Long id) {
        String playerLastName = playerService.fetchPlayersLastNameById(id);
        return ResponseEntity.ok(playerLastName);
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        PlayerRequestDTO playerDTO = modelMapper.map(player, PlayerRequestDTO.class);
        PlayerResponseDTO createdPlayerDTO = playerService.saveRookiePlayer(playerDTO);
        Player createdPlayer = modelMapper.map(createdPlayerDTO, Player.class);
        return ResponseEntity.ok(createdPlayer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody Player player) {
        PlayerRequestDTO playerDTO = modelMapper.map(player, PlayerRequestDTO.class);
        PlayerResponseDTO updatedPlayerDTO = playerService.updatePlayer(id, playerDTO);
        Player updatedPlayer = modelMapper.map(updatedPlayerDTO, Player.class);
        return ResponseEntity.ok(updatedPlayer);
    }

    @PatchMapping("/{id}/market-value")
    public ResponseEntity<Void> updateMarketValue(@PathVariable Long id, @RequestBody BigDecimal increaseAmount) {
        playerService.increaseMarketValueForPlayerById(id, increaseAmount);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/salary")
    public ResponseEntity<Void> updateSalary(@PathVariable Long id, @RequestBody BigDecimal increaseAmount) {
        playerService.increaseSalaryForPlayerById(id, increaseAmount);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
