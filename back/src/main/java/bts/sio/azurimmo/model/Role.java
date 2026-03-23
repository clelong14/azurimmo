package bts.sio.azurimmo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="libelle")
    private String libelle;

    @OneToMany(mappedBy = "role")
    private List<User> users;
}
