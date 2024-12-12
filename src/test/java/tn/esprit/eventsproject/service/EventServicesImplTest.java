package tn.esprit.eventsproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.entities.Tache;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;
import tn.esprit.eventsproject.services.EventServicesImpl;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EventServicesImplTest {

    @InjectMocks
    private EventServicesImpl eventServices;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddParticipant() {
        Participant participant = new Participant();
        participant.setIdPart(1);
        participant.setNom("John");

        when(participantRepository.save(any(Participant.class))).thenReturn(participant);

        Participant savedParticipant = eventServices.addParticipant(participant);

        assertNotNull(savedParticipant);
        assertEquals("John", savedParticipant.getNom());
        verify(participantRepository, times(1)).save(participant);
    }

    @Test
    void testAddAffectEvenParticipant_WithExistingParticipant() {
        Participant participant = new Participant();
        participant.setIdPart(1);
        participant.setEvents(new HashSet<>());

        Event event = new Event();
        event.setIdEvent(100);
        event.setDescription("Test Event");

        when(participantRepository.findById(1)).thenReturn(Optional.of(participant));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event savedEvent = eventServices.addAffectEvenParticipant(event, 1);

        assertNotNull(savedEvent);
        assertTrue(participant.getEvents().contains(event));
        verify(eventRepository, times(1)).save(event);
    }


    @Test
    void testGetLogisticsDates() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        Event event = new Event();
        Logistics logistics = new Logistics();
        logistics.setReserve(true);
        logistics.setPrixUnit(100f);
        logistics.setQuantite(2);
        event.setLogistics(Set.of(logistics));

        when(eventRepository.findByDateDebutBetween((startDate), (endDate)))
                .thenReturn(List.of(event));

        List<Logistics> logisticsList = eventServices.getLogisticsDates(startDate, endDate);

        assertNotNull(logisticsList);
        assertEquals(1, logisticsList.size());
        assertEquals(logistics, logisticsList.get(0));
        verify(eventRepository, times(1)).findByDateDebutBetween(startDate, endDate);
    }


    @Test
    void testCalculCout() {
        Event event = new Event();
        event.setDescription("Test Event");
        Logistics logistics = new Logistics();
        logistics.setReserve(true);
        logistics.setPrixUnit(100f);
        logistics.setQuantite(2);
        event.setLogistics(Set.of(logistics));

        when(eventRepository.findByParticipants_NomAndParticipants_PrenomAndParticipants_Tache(
                ("Tounsi"), ("Ahmed"), (Tache.ORGANISATEUR)))
                .thenReturn(List.of(event));

        eventServices.calculCout();

        verify(eventRepository, times(1)).save(event);
        assertEquals(200f, event.getCout());
    }
}
