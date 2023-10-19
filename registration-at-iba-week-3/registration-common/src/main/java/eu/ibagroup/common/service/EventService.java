package eu.ibagroup.common.service;

import eu.ibagroup.common.mongo.EventRepository;
import eu.ibagroup.common.mongo.collection.Event;
import eu.ibagroup.common.mongo.collection.Registration;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepo;

    public List<Event> getEvents() {
        LocalDate now = LocalDate.now();
        return eventRepo.getAllActual(now);
    }

    public Optional<Event> getEvent(String id) {
        return eventRepo.findById(id);
    }

    public void reserveOneSeat(String id) {
        eventRepo.findById(id)
                .ifPresent(event -> {
                    event.setCurrentNumOfParticipants(event.getCurrentNumOfParticipants() + 1);
                    eventRepo.save(event);
                });
    }

    public void freeOneSeat(String eventId) {
        eventRepo.findById(eventId)
                .ifPresent(event -> {
                    event.setCurrentNumOfParticipants(event.getCurrentNumOfParticipants() - 1);
                    eventRepo.save(event);
                });
    }

    public void freeOneSeat(@NonNull List<Registration> registrations) {
        Set<String> eventIds = registrations.stream().map(Registration::getEventId).collect(Collectors.toSet());
        List<Event> events = eventRepo.getManyFromSet(eventIds);
        eventRepo.freeOneSeat(events);
    }


    public Event createEvent(Event event) {
        return eventRepo.insert(event);
    }

    public Event updateEvent(Event event, String id) {
        return eventRepo.findById(id)
                .map(foundEvent -> updateEvent(event, foundEvent))
                .orElseGet(() -> createEvent(event, id));
    }

    private Event createEvent(Event event, String id) {
        event.setId(id);
        return eventRepo.insert(event);
    }

    private Event updateEvent(Event e, Event updatableEvent) {
        updatableEvent.setName(e.getName());
        updatableEvent.setStartDate(e.getStartDate());
        updatableEvent.setEndDate(e.getEndDate());
        updatableEvent.setMaxNumOfParticipants((e.getMaxNumOfParticipants()));
        return eventRepo.save(updatableEvent);
    }

    public void deleteById(String id) {
        eventRepo.deleteById(id);
    }
}
