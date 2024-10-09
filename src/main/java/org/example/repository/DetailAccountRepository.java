package org.example.repository;

import org.example.entity.DetailAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailAccountRepository extends JpaRepository<DetailAccount, Long> {
}
