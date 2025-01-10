package com.example.service;

import com.example.dto.team.TeamRequestDTO;
import com.example.dto.team.TeamResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface TeamService {
    TeamResponseDTO fetchTeamById(Long id);
    List<TeamResponseDTO> fetchAllTeams();
    TeamResponseDTO saveTeam(TeamRequestDTO teamRequestDTO);
    TeamResponseDTO updateTeam(Long id, TeamRequestDTO teamRequestDTO);
    void patchTeamName(Long id, String name);
    void patchTeamCountry(Long id, String country);
    void patchTeamCity(Long id, String city);
    void patchTeamAnnualBudget(Long id, BigDecimal annualBudget);
    void patchTeamCommission(Long id, Double commission);
    void deleteTeam(Long id);
}
