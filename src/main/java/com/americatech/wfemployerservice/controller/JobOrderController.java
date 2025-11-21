package com.americatech.wfemployerservice.controller;

import com.americatech.wfemployerservice.entity.JobOrderEntity;
import com.americatech.wfemployerservice.mapper.JobOrderRequestMapper;
import com.americatech.wfemployerservice.mapper.JobOrderResponseMapper;
import com.americatech.wfemployerservice.service.JobOrderCommandService;
import com.americatech.wfemployerservice.service.JobOrderQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/job-orders")
@RequiredArgsConstructor
public class JobOrderController {

    private final JobOrderQueryService queryService;
    private final JobOrderCommandService commandService;
    private final JobOrderRequestMapper requestMapper;
    private final JobOrderResponseMapper responseMapper;


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
    public void delete(@PathVariable UUID id) {
        commandService.delete(id);
    }
}
