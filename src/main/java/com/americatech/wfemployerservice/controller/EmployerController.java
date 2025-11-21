package com.americatech.wfemployerservice.controller;

import com.americatech.wfemployerservice.domain.EmployerModel;
import com.americatech.wfemployerservice.mapper.EmployerRequestMapper;
import com.americatech.wfemployerservice.mapper.EmployerResponseMapper;
import com.americatech.wfemployerservice.request.EmployerRequest;
import com.americatech.wfemployerservice.response.EmployerResponse;
import com.americatech.wfemployerservice.service.EmployerCommandService;
import com.americatech.wfemployerservice.service.EmployerQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employers")
@RequiredArgsConstructor
public class EmployerController {

    private final EmployerQueryService employerQueryService;
    private final EmployerCommandService employerCommandService;
    private final EmployerRequestMapper employerRequestMapper;
    private final EmployerResponseMapper employerResponseMapper;


    @PostMapping
    public ResponseEntity<EmployerResponse> create(@Valid @RequestBody EmployerRequest request) {
        EmployerModel model = employerRequestMapper.requestModelToDomainModel(request);
        model = employerCommandService.create(model);
        EmployerResponse response = employerResponseMapper.domainModelToResponseModel(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerResponse> getById(@PathVariable UUID id) {
        EmployerModel model = employerQueryService.getById(id);
        EmployerResponse response = employerResponseMapper.domainModelToResponseModel(model);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public List<EmployerResponse> getAll() {
        return employerResponseMapper.domainModelToResponseModel(employerQueryService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployerResponse> update(@PathVariable UUID id, @Valid @RequestBody EmployerRequest request) {
        EmployerModel model = employerRequestMapper.requestModelToDomainModel(request);
        model = employerCommandService.update(id, model);
        EmployerResponse response = employerResponseMapper.domainModelToResponseModel(model);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        employerCommandService.delete(id);
    }
}
