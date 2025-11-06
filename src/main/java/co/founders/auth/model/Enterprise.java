package co.founders.auth.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@SQLDelete(sql = "UPDATE enterprise SET status = 'INACTIVE' WHERE id = ?")
@SQLRestriction("status = 'ACTIVE'")
public class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    java.util.UUID id;
    String name;
    String nit;
    String phone;
    String address;
    @Column(updatable = false)
    LocalDateTime createdAt;
    LocalDateTime lastUpdateAt;
    LocalDateTime deleteAt;

    @Enumerated(EnumType.STRING)
    Status status;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.lastUpdateAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdateAt = LocalDateTime.now();
    }
    
    
    

    
}
