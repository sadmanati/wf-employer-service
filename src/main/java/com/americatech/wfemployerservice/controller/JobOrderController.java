package com.americatech.wfemployerservice.controller;

import com.americatech.wfemployerservice.entity.JobOrderEntity;
import com.americatech.wfemployerservice.service.JobOrderCommandService;
import com.americatech.wfemployerservice.service.JobOrderQueryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/job-orders")
public class JobOrderController {

    private final JobOrderQueryService queryService;
    private final JobOrderCommandService commandService;

    public JobOrderController(JobOrderQueryService queryService,
                              JobOrderCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    @PostMapping
    public ResponseEntity<JobOrderEntity> create(@Valid @RequestBody JobOrderEntity order) {
        JobOrderEntity created = commandService.create(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public JobOrderEntity getById(@PathVariable UUID id) {
        return queryService.getById(id);
    }

    @GetMapping
    public List<JobOrderEntity> getAll() {
        return queryService.getAll();
    }

    @PutMapping("/{id}")
    public JobOrderEntity update(@PathVariable UUID id, @Valid @RequestBody JobOrderEntity order) {
        return commandService.update(id, order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        commandService.delete(id);
    }

    @ExceptionHandler({EntityNotFoundException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleErrors(RuntimeException ex) {
        HttpStatus status = ex instanceof EntityNotFoundException ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(ex.getMessage());
    }
}
