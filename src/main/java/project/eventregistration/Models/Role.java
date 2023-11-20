package project.eventregistration.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;
}
