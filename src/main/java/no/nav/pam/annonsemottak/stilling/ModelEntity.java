package no.nav.pam.annonsemottak.stilling;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public class ModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence")
    @Column
    private Long id;

    private LocalDateTime created = LocalDateTime.now();
    private String createdBy;
    private String createdByDisplayName;

    private LocalDateTime updated = LocalDateTime.now();
    private String updatedBy;
    private String updatedByDisplayName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PrePersist
    @PreUpdate
    protected void onMerge() {
        String userName = "test1234";
        String userDisplayName = "Testuser Displayname";
        LocalDateTime now = LocalDateTime.now();

        if (isNew()) {
            setCreated(now);
            setCreatedBy(userName);
            setCreatedByDisplayName(userDisplayName);
        }

        setUpdated(now);
        setUpdatedBy(userName);
        setUpdatedByDisplayName(userDisplayName);
    }

    public boolean isNew() {
        return id == null;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedByDisplayName() {
        return createdByDisplayName;
    }

    public void setCreatedByDisplayName(String createdByDisplayName) {
        this.createdByDisplayName = createdByDisplayName;
    }

    public String getUpdatedByDisplayName() {
        return updatedByDisplayName;
    }

    public void setUpdatedByDisplayName(String updatedByDisplayName) {
        this.updatedByDisplayName = updatedByDisplayName;
    }
}
