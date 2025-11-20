package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.entity.DemandLetterEntity;
import com.americatech.wfemployerservice.entity.EmployerEntity;
import com.americatech.wfemployerservice.entity.JobOrderEntity;
import com.americatech.wfemployerservice.repository.DemandLetterRepository;
import com.americatech.wfemployerservice.repository.EmployerRepository;
import com.americatech.wfemployerservice.repository.JobOrderRepository;
import com.americatech.wfemployerservice.service.JobOrderCommandService;
import com.americatech.wfemployerservice.service.JobOrderQueryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class JobOrderCommandServiceImpl implements JobOrderCommandService {

    private static final Set<String> ALLOWED_STATUS = Set.of(
            "draft", "submitted", "under_validation", "validated",
            "open_for_sourcing", "filled", "deployed", "closed"
    );

    private final JobOrderRepository jobOrderRepository;
    private final EmployerRepository employerRepository;
    private final DemandLetterRepository demandLetterRepository;
    private final JobOrderQueryService jobOrderQueryService;

    public JobOrderCommandServiceImpl(JobOrderRepository jobOrderRepository,
                                      EmployerRepository employerRepository,
                                      DemandLetterRepository demandLetterRepository,
                                      JobOrderQueryService jobOrderQueryService) {
        this.jobOrderRepository = jobOrderRepository;
        this.employerRepository = employerRepository;
        this.demandLetterRepository = demandLetterRepository;
        this.jobOrderQueryService = jobOrderQueryService;
    }

    @Override
    public JobOrderEntity create(JobOrderEntity order) {
        order.setId(null);
        attachRelations(order);
        applyDefaults(order);
        validate(order);
        return jobOrderRepository.save(order);
    }

    @Override
    public JobOrderEntity update(UUID id, JobOrderEntity input) {
        JobOrderEntity existing = jobOrderQueryService.getById(id);

        if (input.getEmployer() != null && input.getEmployer().getId() != null) {
            existing.setEmployer(findEmployer(input.getEmployer().getId()));
        }
        // demand letter is optional; allow setting or clearing
        if (input.getDemandLetter() != null) {
            if (input.getDemandLetter().getId() != null) {
                existing.setDemandLetter(findDemandLetter(input.getDemandLetter().getId()));
            } else {
                existing.setDemandLetter(null);
            }
        }

        existing.setOrderNumber(input.getOrderNumber());
        existing.setJobTitle(input.getJobTitle());
        existing.setJobCategory(input.getJobCategory());
        existing.setRequiredQuantity(input.getRequiredQuantity());
        existing.setFilledQuantity(input.getFilledQuantity());
        existing.setJobDescription(input.getJobDescription());
        existing.setSalaryMin(input.getSalaryMin());
        existing.setSalaryMax(input.getSalaryMax());
        existing.setCurrency(input.getCurrency());
        existing.setContractDurationMonths(input.getContractDurationMonths());
        existing.setProbationPeriodMonths(input.getProbationPeriodMonths());
        existing.setWorkingHours(input.getWorkingHours());
        existing.setBenefits(input.getBenefits());
        existing.setRequiredStartDate(input.getRequiredStartDate());
        existing.setStatus(input.getStatus());
        existing.setValidatedBy(input.getValidatedBy());
        existing.setValidatedAt(input.getValidatedAt());
        existing.setCreatedBy(input.getCreatedBy());

        applyDefaults(existing);
        validate(existing);
        return jobOrderRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        if (!jobOrderRepository.existsById(id)) {
            throw new EntityNotFoundException("Job order not found: " + id);
        }
        jobOrderRepository.deleteById(id);
    }

    private void attachRelations(JobOrderEntity order) {
        if (order.getEmployer() == null || order.getEmployer().getId() == null) {
            throw new IllegalArgumentException("employer.id is required");
        }
        EmployerEntity employer = findEmployer(order.getEmployer().getId());
        order.setEmployer(employer);

        if (order.getDemandLetter() != null && order.getDemandLetter().getId() != null) {
            DemandLetterEntity letter = findDemandLetter(order.getDemandLetter().getId());
            order.setDemandLetter(letter);
        }
    }

    private EmployerEntity findEmployer(UUID employerId) {
        return employerRepository.findById(employerId)
                .orElseThrow(() -> new EntityNotFoundException("Employer not found: " + employerId));
    }

    private DemandLetterEntity findDemandLetter(UUID letterId) {
        return demandLetterRepository.findById(letterId)
                .orElseThrow(() -> new EntityNotFoundException("Demand letter not found: " + letterId));
    }

    private void applyDefaults(JobOrderEntity order) {
        if (order.getStatus() == null || order.getStatus().isBlank()) {
            order.setStatus("draft");
        }
        if (order.getCurrency() == null || order.getCurrency().isBlank()) {
            order.setCurrency("AED");
        }
        if (order.getFilledQuantity() == null) {
            order.setFilledQuantity(0);
        }
        if (order.getProbationPeriodMonths() == null) {
            order.setProbationPeriodMonths(6);
        }
    }

    private void validate(JobOrderEntity order) {
        // required string/refs
        if (order.getEmployer() == null) {
            throw new IllegalArgumentException("employer is required");
        }
        if (order.getOrderNumber() == null || order.getOrderNumber().isBlank()) {
            throw new IllegalArgumentException("orderNumber is required");
        }
        if (order.getJobTitle() == null || order.getJobTitle().isBlank()) {
            throw new IllegalArgumentException("jobTitle is required");
        }
        if (order.getJobCategory() == null || order.getJobCategory().isBlank()) {
            throw new IllegalArgumentException("jobCategory is required");
        }
        if (order.getJobDescription() == null || order.getJobDescription().isBlank()) {
            throw new IllegalArgumentException("jobDescription is required");
        }
        if (order.getCreatedBy() == null) {
            throw new IllegalArgumentException("createdBy is required");
        }

        // numeric and enum constraints mirroring DB checks
        if (order.getRequiredQuantity() == null || order.getRequiredQuantity() <= 0) {
            throw new IllegalArgumentException("requiredQuantity must be > 0");
        }
        if (order.getFilledQuantity() == null || order.getFilledQuantity() < 0) {
            throw new IllegalArgumentException("filledQuantity must be >= 0");
        }
        if (order.getFilledQuantity() > order.getRequiredQuantity()) {
            throw new IllegalArgumentException("filledQuantity must be <= requiredQuantity");
        }
        BigDecimal min = order.getSalaryMin();
        BigDecimal max = order.getSalaryMax();
        if (min == null || min.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("salaryMin must be > 0");
        }
        if (max == null || max.compareTo(min) < 0) {
            throw new IllegalArgumentException("salaryMax must be >= salaryMin");
        }
        if (order.getContractDurationMonths() == null ||
                order.getContractDurationMonths() <= 0 ||
                order.getContractDurationMonths() > 60) {
            throw new IllegalArgumentException("contractDurationMonths must be between 1 and 60");
        }
        if (order.getProbationPeriodMonths() == null ||
                order.getProbationPeriodMonths() < 0 ||
                order.getProbationPeriodMonths() > 12) {
            throw new IllegalArgumentException("probationPeriodMonths must be between 0 and 12");
        }
        if (!ALLOWED_STATUS.contains(order.getStatus())) {
            throw new IllegalArgumentException("Invalid status: " + order.getStatus());
        }

        // relationship validations if demand letter provided
        if (order.getDemandLetter() != null) {
            DemandLetterEntity letter = order.getDemandLetter();
            if (letter.getEmployer() == null || letter.getEmployer().getId() == null ||
                    order.getEmployer() == null || order.getEmployer().getId() == null ||
                    !letter.getEmployer().getId().equals(order.getEmployer().getId())) {
                throw new IllegalArgumentException("Demand letter does not belong to the specified employer");
            }
            if (letter.getJobCategory() != null && order.getJobCategory() != null &&
                    !letter.getJobCategory().equals(order.getJobCategory())) {
                throw new IllegalArgumentException("jobCategory must match the demand letter jobCategory");
            }
        }
    }
}
