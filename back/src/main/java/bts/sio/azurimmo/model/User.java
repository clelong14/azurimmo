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

    @Column(name="adresseMail")
    private String adresseMail;

    @Column(name="motDePasse")
    private String motDePasse;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}