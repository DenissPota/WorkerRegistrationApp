package com.helmes.worker_reg.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WorkerNotFoundException extends Exception {

    private Long workerId;

    public WorkerNotFoundException(Long workerId) {
        this.workerId = workerId;
    }

    @Override
    public String toString(){
        return "Worker with id " + workerId + " is not found";
    }
}
