package eu.ibagroup.common.service;

import eu.ibagroup.common.mongo.RegistrationRepository;
import eu.ibagroup.common.mongo.collection.Registration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepo;

    public List<Registration> getRegistrations(long chatId) {
        LocalDate now = LocalDate.now();
        return registrationRepo.getAllByUser(now, chatId);
    }

    public List<Registration> getRegistrations(String eventId) {
        LocalDate now = LocalDate.now();
        return registrationRepo.getAllByEvent(now, eventId);
    }


    public Optional<Registration> getRegistration(String eventId, long chatId) {
        return registrationRepo.findOne(eventId, chatId);
    }

    public Registration createRegistration(Registration registration) {
        return registrationRepo.insert(registration);
    }

    public void deleteRegistration(String eventId, long chatId) {
        registrationRepo.deleteByEventId(eventId, chatId);
    }

    public void deleteRegistrations(long chatId) {
        registrationRepo.deleteByChatId(chatId);
    }
}
