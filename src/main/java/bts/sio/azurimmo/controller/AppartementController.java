package bts.sio.azurimmo.controller;

import bts.sio.azurimmo.model.Appartement;
import bts.sio.azurimmo.service.AppartementService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController // Gère les requêtes HTTP et retourne du JSON.
@RequestMapping("/api/appartements") // Chemin de base de l'API.
public class AppartementController {
	
   @Autowired // Injection de la couche Service.
    private AppartementService appartementService;

	
    @PostMapping("/") // Endpoint de création (POST).
    public Appartement createAppartement(@RequestBody Appartement appartement) {
    	// @RequestBody : Récupère l'objet depuis le corps de la requête.
        return appartementService.saveAppartement(appartement);
    }
    
    @GetMapping("/ville/{ville}") // Endpoint de recherche par ville.
    public List<Appartement> findByVille(@PathVariable String ville) {
    	// @PathVariable : Récupère la valeur depuis l'URL.
        return appartementService.findByVille(ville);
    }
    
    @GetMapping("/batiment/{batimentId}") // Endpoint de recherche par ID de bâtiment.
    public List<Appartement> getAppartementsParBatiment(@PathVariable long batimentId) {
            return appartementService.getAppartementsParBatiment(batimentId);
    }
    
    @GetMapping("/surface/{surface}") // Endpoint de recherche par surface minimum.
    public List<Appartement> findBySurfaceGreaterThan(@PathVariable Integer surface) {
            return appartementService.findBySurfaceGreaterThan(surface);
    }

}