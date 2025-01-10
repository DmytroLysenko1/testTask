package com.example.controller;

import com.example.dto.team.TeamRequestDTO;
import com.example.dto.team.TeamResponseDTO;
import com.example.entity.Team;
import com.example.service.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {
    private final TeamService teamService;
    private final ModelMapper modelMapper;

    public TeamController(TeamService teamService, ModelMapper modelMapper) {
        this.teamService = teamService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<Team>> fetchAllTeams() {
        List<TeamResponseDTO> teamResponseDTOList = teamService.fetchAllTeams();
        return ResponseEntity.ok(teamResponseDTOList.stream()
                .map(teamResponseDTO -> modelMapper.map(teamResponseDTO, Team.class))
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> fetchTeamById(@PathVariable Long id) {
        TeamResponseDTO teamResponseDTO = teamService.fetchTeamById(id);
        return ResponseEntity.ok(modelMapper.map(teamResponseDTO, Team.class));
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        TeamRequestDTO teamDTO = modelMapper.map(team, TeamRequestDTO.class);
        TeamResponseDTO createdTeamDTO = teamService.saveTeam(teamDTO);
        Team createdTeam = modelMapper.map(createdTeamDTO, Team.class);
        return ResponseEntity.ok(createdTeam);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team team) {
        TeamRequestDTO teamDTO = modelMapper.map(team, TeamRequestDTO.class);
        TeamResponseDTO updatedTeamDTO = teamService.updateTeam(id, teamDTO);
        Team updatedTeam = modelMapper.map(updatedTeamDTO, Team.class);
        return ResponseEntity.ok(updatedTeam);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<Void> patchTeamName(@PathVariable Long id, @RequestBody String name) {
        teamService.patchTeamName(id, name);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/country")
    public ResponseEntity<Void> patchTeamCountry(@PathVariable Long id, @RequestBody String country) {
        teamService.patchTeamCountry(id, country);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/city")
    public ResponseEntity<Void> patchTeamCity(@PathVariable Long id, @RequestBody String city) {
        teamService.patchTeamCity(id, city);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/annual-budget")
    public ResponseEntity<Void> patchTeamAnnualBudget(@PathVariable Long id, @RequestBody BigDecimal annualBudget) {
        teamService.patchTeamAnnualBudget(id, annualBudget);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/commission")
    public ResponseEntity<Void> patchTeamCommission(@PathVariable Long id, @RequestBody Double commission) {
        teamService.patchTeamCommission(id, commission);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}
