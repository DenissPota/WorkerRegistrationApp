package com.helmes.worker_reg.controller;


import com.helmes.worker_reg.entities.Worker;
import com.helmes.worker_reg.exceptions.WorkerNotFoundException;
import com.helmes.worker_reg.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class WorkerController {

    private final WorkerRepository workerRepository;

    @Autowired
    public WorkerController(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    /**
     * Create a worker with first name, last name, sector(id and name) and agreed to terms boolean.
     *
     * @return the worker object.
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/workers")
    public Worker addWorker(@Valid @RequestBody Worker worker) {
        return workerRepository.save(worker);
    }

    /**
     * Gets all workers present in database.
     *
     * @return the all workers with corresponding first name, last name, sector(id and name) and agreed to terms boolean.
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/workers")
    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    /**
     * Update worker based on id.
     *
     * @param id of worker that will be deleted.
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @PutMapping("/workers/{id}")
    public Worker updateWorker(@PathVariable Long id, @Valid @RequestBody Worker workerRequest) throws WorkerNotFoundException {
        return workerRepository.findById(id).map(worker -> {
            worker.setFirstName(workerRequest.getFirstName());
            worker.setLastName(workerRequest.getLastName());
            worker.setAgreedToTerms(workerRequest.getAgreedToTerms());
            worker.setSector(workerRequest.getSector());
            return workerRepository.save(worker);

        }).orElseThrow(() -> new WorkerNotFoundException(id));
    }

    /**
     * Remove worker based on id.
     *
     * @param id of worker that will be deleted.
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @DeleteMapping("/workers/{id}")
    public void deleteWorker(@PathVariable Long id) throws WorkerNotFoundException {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new WorkerNotFoundException(id));
        workerRepository.deleteById(id);
    }
    /**
     * Remove worker based on id.
     *
     * @param id of worker that will be deleted.
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/workers/{id}")
    public Optional<Worker> getWorker(@PathVariable Long id) throws WorkerNotFoundException {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new WorkerNotFoundException(id));
        return workerRepository.findById(id);
    }

    @GetMapping("/")
    public @ResponseBody String greeting() {
        return "Hello World";
    }
}
