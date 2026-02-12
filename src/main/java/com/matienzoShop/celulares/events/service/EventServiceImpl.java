package com.matienzoShop.celulares.events.service;

import com.matienzoShop.celulares.celular.model.Celular;
import com.matienzoShop.celulares.celular.repository.CelularRepository;
import com.matienzoShop.celulares.events.dto.EventRequestDTO;
import com.matienzoShop.celulares.events.model.Event;
import com.matienzoShop.celulares.events.repository.EventRepository;
import com.matienzoShop.celulares.exception.InvalidEventException;
import com.matienzoShop.celulares.user.model.User;
import com.matienzoShop.celulares.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EventServiceImpl implements EventService{

    private final CelularRepository celularRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventServiceImpl (EventRepository eventRepository,
                             UserRepository userRepository,
                             CelularRepository celularRepository){
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.celularRepository = celularRepository;
    }

    @Override
    public void registerEvent (EventRequestDTO request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;

        if (authentication != null &&
            authentication.isAuthenticated() &&
           !"anonymousUser".equals(authentication.getPrincipal())){

            String email = authentication.getName();
            user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));
        }

        if (user == null && request.anonymousId() == null){
            throw new InvalidEventException("Debe existir user o anonymousId");
        }

        Celular celular = celularRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Event event = new Event();
        event.setEventType(request.eventType());
        event.setProduct(celular);
        event.setUser(user);
        event.setAnonymousId(request.anonymousId());
        event.setMetadata(request.metadata());
        event.setCreatedAt(Instant.now());

        eventRepository.save(event);
    }

}
