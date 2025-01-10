package com.example.dto.transfer;



public class TransferRequestDTO {
    private Long playerId;
    private Long teamId;

    public TransferRequestDTO(Long playerId, Long teamId) {
        this.playerId = playerId;
        this.teamId = teamId;
    }

    public TransferRequestDTO() {
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
}
