package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.ListenerRequestDTO;
import com.develhope.spring.dtos.responses.ListenerResponseDTO;
import com.develhope.spring.entities.Listener;
import com.develhope.spring.models.Response;
import com.develhope.spring.servicies.interfaces.ListenerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/listener")
public class ListenerController {

    @Autowired
    private ListenerService listenerService; // Servizio responsabile delle operazioni relative agli ascoltatori

    @Autowired
    private ModelMapper modelMapper; // Mapper per la conversione tra DTO e entità

    // Endpoint per ottenere tutti gli ascoltatori
    @GetMapping("/")
    public List<Listener> getAllListeners() {
        return listenerService.getAllListeners();
    }

    // Endpoint per ottenere un ascoltatore tramite ID
    @GetMapping("/{id}")
    public ResponseEntity<Response> getListenerById(@PathVariable Long id) {
        Optional<Listener> listener = listenerService.findListener(id);
        if (listener.isPresent()) {
            return ResponseEntity.ok().body(new Response(200, "Listener found", listener));
        } else {
            return ResponseEntity.status(404).body(new Response(404, "Listener not found"));
        }
    }

    // Endpoint per creare un nuovo ascoltatore
    @PostMapping("/")
    public ResponseEntity<Response> createListener(@RequestBody ListenerRequestDTO listenerDTO) {
        Listener listener = modelMapper.map(listenerDTO, Listener.class);
        Listener newListener = listenerService.createListener(listener);

        if (newListener != null) {
            return ResponseEntity.ok().body(new Response(200, "Listener created successfully", newListener));
        } else {
            return ResponseEntity.status(400).body(
                    new Response(
                            400,
                            "Failed to create listener"
                    )
            );
        }
    }

    // Endpoint per aggiornare un ascoltatore esistente
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateListener(@PathVariable long id, @RequestBody ListenerRequestDTO listenerDTO) {
        Listener listenerToUpdate = modelMapper.map(listenerDTO, Listener.class);
        Listener updatedListener = listenerService.updateListener(id, listenerToUpdate);
        if (updatedListener != null) {
            return ResponseEntity.ok().body(new Response(200, "Listener updated successfully", updatedListener));
        } else {
            return ResponseEntity.status(404).body(new Response(404, "Listener not found"));
        }
    }

    // Endpoint per eliminare un ascoltatore tramite ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteListener(@PathVariable long id) {
        boolean deleted = listenerService.deleteListener(id);
        if (deleted) {
            return ResponseEntity.ok().body(new Response(200, "Listener deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(new Response(404, "Listener not found"));
        }
    }
}