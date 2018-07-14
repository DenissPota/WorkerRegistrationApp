package com.helmes.worker_reg.repository;

import com.helmes.worker_reg.entities.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Long> {


    @Query("SELECT w.firstName, w.lastName,sec.sectorName, w.agreedToTerms FROM Worker w INNER JOIN Sector sec ON w.id = sec.id")
    List<Worker> findAllWorkers();

    List<Worker> findAllBySector_SectorName(String n);
}
