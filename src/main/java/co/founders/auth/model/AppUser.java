package co.founders.auth.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE app_user SET status = 'INACTIVE' WHERE id = ?")
@SQLRestriction("status = 'ACTIVE'")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    java.util.UUID id;
    String firstname;
    String lastname;
    String phone;
    
    @jakarta.persistence.Column(unique = true)
    String email;
    String password;

    @Column(updatable = false)
    LocalDateTime createdAt;
    LocalDateTime lastUpdateAt;
    LocalDateTime deleteAt;

    @Enumerated(EnumType.STRING)
    Role role;
    @Enumerated(EnumType.STRING)
    Status status;
    @Enumerated(EnumType.STRING)
    Status registrationStatus;


    @ManyToOne
    Enterprise enterprise;

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