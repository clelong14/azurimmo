package bts.sio.azurimmo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "adresseMail", nullable = false, unique = true, length = 150)
    private String adresseMail;

    @Column(name = "motDePasse", nullable = false, length = 100)
    private String motDePasse;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}