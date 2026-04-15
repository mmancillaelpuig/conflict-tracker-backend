package com.example.conflict_tracker2.DTO;

import com.example.conflict_tracker2.model.Conflict;
import java.time.LocalDate;
import java.util.Set;

public class ConflictRespuestaDTO {

    private Long id;
    private String name;
    private LocalDate startDate;
    private Conflict.status conflictStatus;
    private String description;
    private Set<Long> countryIds;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public Conflict.status getConflictStatus() { return conflictStatus; }
    public void setConflictStatus(Conflict.status conflictStatus) { this.conflictStatus = conflictStatus; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Set<Long> getCountryIds() { return countryIds; }
    public void setCountryIds(Set<Long> countryIds) { this.countryIds = countryIds; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}