package bts.sio.azurimmo.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;

    @NotBlank(message = "L'adresse mail est obligatoire")
    @Email(message = "L'adresse mail est invalide")
    private String adresseMail;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, max = 100, message = "Le mot de passe doit contenir entre 8 et 100 caractères")
    private String motDePasse;

    private Long role;
    private String roleLibelle;
}
