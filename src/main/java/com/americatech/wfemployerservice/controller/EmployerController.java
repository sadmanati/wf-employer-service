package com.americatech.wfemployerservice.controller;

import com.americatech.wfemployerservice.entity.EmployerEntity;
import com.americatech.wfemployerservice.service.EmployerCommandService;
import com.americatech.wfemployerservice.service.EmployerQueryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employers")
public class EmployerController {

    private final EmployerQueryService employerQueryService;
    private final EmployerCommandService employerCommandService;

    public EmployerController(EmployerQueryService employerQueryService,
                              EmployerCommandService employerCommandService) {
        this.employerQueryService = employerQueryService;
        this.employerCommandService = employerCommandService;
    }

    @PostMapping
    public ResponseEntity<EmployerEntity> create(@Valid @RequestBody EmployerEntity employer) {
        EmployerEntity created = employerCommandService.create(employer);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public EmployerEntity getById(@PathVariable UUID id) {
        return employerQueryService.getById(id);
    }

    @GetMapping
    public List<EmployerEntity> getAll() {
        return employerQueryService.getAll();
    }

    @PutMapping("/{id}")
    public EmployerEntity update(@PathVariable UUID id, @Valid @RequestBody EmployerEntity employer) {
        return employerCommandService.update(id, employer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        employerCommandService.delete(id);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
