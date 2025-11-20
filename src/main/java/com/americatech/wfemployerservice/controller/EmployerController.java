package com.americatech.wfemployerservice.controller;

import com.americatech.wfemployerservice.domain.EmployerModel;
import com.americatech.wfemployerservice.mapper.EmployerRequestMapper;
import com.americatech.wfemployerservice.mapper.EmployerResponseMapper;
import com.americatech.wfemployerservice.request.EmployerRequest;
import com.americatech.wfemployerservice.response.EmployerResponse;
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
    private final EmployerRequestMapper employerRequestMapper;
    private final EmployerResponseMapper employerResponseMapper;

    public EmployerController(EmployerQueryService employerQueryService,
                              EmployerCommandService employerCommandService,
                              EmployerRequestMapper employerRequestMapper,
                              EmployerResponseMapper employerResponseMapper) {
        this.employerQueryService = employerQueryService;
        this.employerCommandService = employerCommandService;
        this.employerRequestMapper = employerRequestMapper;
        this.employerResponseMapper = employerResponseMapper;
    }

    @PostMapping
    public ResponseEntity<EmployerResponse> create(@Valid @RequestBody EmployerRequest request) {
        EmployerModel model = employerRequestMapper.requestModelToDomainModel(request);
        EmployerModel created = employerCommandService.create(model);
        EmployerResponse response = employerResponseMapper.domainModelToResponseModel(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public EmployerResponse getById(@PathVariable UUID id) {
        EmployerModel model = employerQueryService.getById(id);
        return employerResponseMapper.domainModelToResponseModel(model);
    }

    @GetMapping
    public List<EmployerResponse> getAll() {
        return employerResponseMapper.domainModelToResponseModel(employerQueryService.getAll());
    }

    @PutMapping("/{id}")
    public EmployerResponse update(@PathVariable UUID id, @Valid @RequestBody EmployerRequest request) {
        EmployerModel model = employerRequestMapper.requestModelToDomainModel(request);
        EmployerModel updated = employerCommandService.update(id, model);
        return employerResponseMapper.domainModelToResponseModel(updated);
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
