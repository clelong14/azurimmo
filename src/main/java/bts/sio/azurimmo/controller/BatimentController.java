package bts.sio.azurimmo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bts.sio.azurimmo.model.Appartement;
import bts.sio.azurimmo.model.Batiment;
import bts.sio.azurimmo.service.AppartementService;

@RestController 
@RequestMapping("/api/batiments")
public class BatimentController {
	
	@Autowired
    private AppartementService batimentService;
	
	@PostMapping("/") // Endpoint de création (POST).
    public Batiment createBatiment(@RequestBody Batiment batiment) {
    	// @RequestBody : Récupère l'objet depuis le corps de la requête.
        return batimentService.saveBatiment(batiment);
    }
	
	@GetMapping("/{batimentId}")
	 public Optional <BatimentDTO> getBatimentDTO(@PathVariable long batimentId) {
	 return batimentService.getBatimentDTO(batimentId);
	 }
}
