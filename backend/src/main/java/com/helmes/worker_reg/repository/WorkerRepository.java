package com.helmes.worker_reg.repository;

import com.helmes.worker_reg.entities.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> { }
