package com.example.service.impl;

import com.example.dto.team.TeamRequestDTO;
import com.example.dto.team.TeamResponseDTO;
import com.example.entity.Team;
import com.example.exceptions.NotFoundException;
import com.example.repository.TeamRepository;
import com.example.service.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;

    public TeamServiceImpl(TeamRepository teamRepository, ModelMapper modelMapper) {
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public TeamResponseDTO fetchTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Team not found with id: " + id));
        return modelMapper.map(team, TeamResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<TeamResponseDTO> fetchAllTeams() {
        return teamRepository.findAll().stream()
                .map(team -> modelMapper.map(team, TeamResponseDTO.class))
                .toList();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public TeamResponseDTO saveTeam(TeamRequestDTO teamRequestDTO) {
        Team team = modelMapper.map(teamRequestDTO, Team.class);
        team = teamRepository.save(team);
        return modelMapper.map(team, TeamResponseDTO.class);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public TeamResponseDTO updateTeam(Long id, TeamRequestDTO teamRequestDTO) {
        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Team not found with id: " + id));
        modelMapper.map(teamRequestDTO, existingTeam);
        teamRepository.save(existingTeam);
        return modelMapper.map(existingTeam, TeamResponseDTO.class);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public void patchTeamName(Long id, String name) {
        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Team not found with id: " + id));
        existingTeam.setName(name);
        teamRepository.save(existingTeam);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public void patchTeamCountry(Long id, String country) {
        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Team not found with id: " + id));
        existingTeam.setCountry(country);
        teamRepository.save(existingTeam);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public void patchTeamCity(Long id, String city) {
        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Team not found with id: " + id));
        existingTeam.setCity(city);
        teamRepository.save(existingTeam);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void patchTeamAnnualBudget(Long id, BigDecimal annualBudget) {
        Team existingTeam = teamRepository.findByIdWithLock(id)
                .orElseThrow(() -> new NotFoundException("Team not found with id: " + id));
        if (annualBudget.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Annual budget cannot be negative");
        }
        existingTeam.setAnnualBudget(annualBudget);
        teamRepository.save(existingTeam);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void patchTeamCommission(Long id, Double commission) {
        if (commission < 0 || commission > 10) {
            throw new IllegalArgumentException("Commission must be between 0 and 10");
        }
        Team existingTeam = teamRepository.findByIdWithLock(id)
                .orElseThrow(() -> new NotFoundException("Team not found with id: " + id));
        existingTeam.setCommission(commission);
        teamRepository.save(existingTeam);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void deleteTeam(Long id) {
        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Team not found with id: " + id));
        teamRepository.delete(existingTeam);
    }
}

