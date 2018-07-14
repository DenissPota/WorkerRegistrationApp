package com.helmes.worker_reg.controller;


import com.helmes.worker_reg.entities.Worker;
import com.helmes.worker_reg.exceptions.WorkerNotFoundException;
import com.helmes.worker_reg.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class WorkerController {

    private final WorkerRepository workerRepository;

    @Autowired
    public WorkerController(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    /**
     * Gets all workers.
     *
     * @return the all workers with corresponding first name, last name and agreed to terms boolean.
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/workers")
    public List<Worker> getAllWorkers() {
        System.out.println(workerRepository.findAll());
        return workerRepository.findAll();

    }
    /**
     * Gets all workers.
     *
     * @return the all workers with corresponding first name, last name and agreed to terms boolean.
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/workersall")
    public List<Worker> findAllWorkers() {
        return workerRepository.findAllWorkers();
    }

    /**
     * Remove worker based on id.
     *
     * @param id of worker that will be deleted. Generated in WorkerController.
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @DeleteMapping("/workers/{id}")
    public void deleteWorker(@PathVariable Long id) throws WorkerNotFoundException {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new WorkerNotFoundException(id));
        workerRepository.deleteById(id);
    }
    /**
     * Update worker based on id.
     *
     * @param id of worker that will be deleted. Generated in WorkerController.
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @PutMapping("/workers/{id}")
    public Worker updateWorker(@PathVariable Long id, @Valid @RequestBody Worker workerRequest) throws WorkerNotFoundException {
        return workerRepository.findById(id).map(worker -> {
            worker.setFirstName(workerRequest.getFirstName());
            worker.setLastName(workerRequest.getLastName());
            worker.setAgreedToTerms(workerRequest.getAgreedToTerms());
            return workerRepository.save(worker);

        }).orElseThrow(() -> new WorkerNotFoundException(id));
    }

    /**
     * Create a worker.
     *
     * @return the worker
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/workers")
    public Worker addWorker(@Valid @RequestBody Worker worker) {
        return workerRepository.save(worker);
    }
}
