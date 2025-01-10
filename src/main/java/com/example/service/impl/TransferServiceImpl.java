package com.example.service.impl;

import com.example.entity.Player;
import com.example.entity.Team;
import com.example.exceptions.NotFoundException;
import com.example.repository.PlayerRepository;
import com.example.repository.TeamRepository;
import com.example.service.TransferService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class TransferServiceImpl implements TransferService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public TransferServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void transfer(Long playerId, Long teamId) {
        Player player = loadPlayer(playerId);
        Team currentTeam = loadCurrentTeam(player);
        Team targetTeam = loadTargetTeam(teamId);

        validateTeam(currentTeam, targetTeam);

        BigDecimal transferValue = calculateTransferValue(player);
        BigDecimal commission = calculateCommission(currentTeam, transferValue);
        BigDecimal totalTransferAmount = transferValue.add(commission);

        validateBudget(targetTeam, totalTransferAmount);

        executeTransfer(player, currentTeam, targetTeam, totalTransferAmount);
    }

    private Player loadPlayer(Long playerId) {
        return playerRepository.findByIdWithLock(playerId).orElseThrow(() -> new NotFoundException("Player not found"));
    }

    private Team loadCurrentTeam(Player player) {
        Team currentTeam = player.getTeam();
        if (currentTeam == null) {
            throw new IllegalStateException("Player is not in a team");
        }
        return currentTeam;
    }

    private Team loadTargetTeam(Long targetTeamId) {
        return teamRepository.findByIdWithLock(targetTeamId).orElseThrow(() -> new NotFoundException("Team not found"));
    }

    private void validateTeam(Team currentTeam, Team targetTeam) {
        if (currentTeam.equals(targetTeam)) {
            throw new IllegalArgumentException("Player is already in the target team");
        }
    }

    private void executeTransfer(Player player, Team currentTeam, Team targetTeam, BigDecimal totalTransferAmount) {
        targetTeam.setAnnualBudget(targetTeam.getAnnualBudget().subtract(totalTransferAmount));
        currentTeam.setAnnualBudget(currentTeam.getAnnualBudget().add(totalTransferAmount));

        player.setTeam(targetTeam);

        playerRepository.save(player);
        teamRepository.save(currentTeam);
        teamRepository.save(targetTeam);
    }

    private void validateBudget(Team targetTeam, BigDecimal totalTransferAmount) {
        if (targetTeam.getAnnualBudget().compareTo(totalTransferAmount) < 0) {
            throw new IllegalArgumentException("Target team does not have enough budget");
        }
    }

    private BigDecimal calculateCommission(Team currentTeam, BigDecimal transferValue) {
        double commissionPercentage = currentTeam.getCommission();
        if (commissionPercentage < 0 || commissionPercentage > 100) {
            throw new IllegalArgumentException("Commission percentage must be between 0 and 10.");
        }
        return transferValue.multiply(BigDecimal.valueOf(commissionPercentage))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTransferValue(Player player) {
        int monthsOfExperience = player.getExperienceMonths();
        int ageInYears = player.getAge();

        if (ageInYears <= 0){
            throw new IllegalArgumentException("Player's age must be greater than 0");
        }

        return BigDecimal.valueOf(monthsOfExperience)
                .multiply(BigDecimal.valueOf(100000))
                .divide(BigDecimal.valueOf(ageInYears), 2, RoundingMode.HALF_UP);
    }

}
