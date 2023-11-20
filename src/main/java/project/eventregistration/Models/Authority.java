package project.eventregistration.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;
}
