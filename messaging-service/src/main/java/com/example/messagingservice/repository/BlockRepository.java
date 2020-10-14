package com.example.messagingservice.repository;

import com.example.messagingservice.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
}
