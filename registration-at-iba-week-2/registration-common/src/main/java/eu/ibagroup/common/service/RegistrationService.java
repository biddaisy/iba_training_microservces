package eu.ibagroup.common.service;

import eu.ibagroup.common.mongo.RegistrationRepository;
import eu.ibagroup.common.mongo.collection.Registration;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    public List<Registration> getRegistrationsByChatId(long chatId) {
        LocalDate now = LocalDate.now();
        return registrationRepository.getAllByChat(now, chatId);
    }

    public List<Registration> getRegistrationsByEventId(@NonNull String eventId) {
        LocalDate now = LocalDate.now();
        return registrationRepository.getAllByEvent(now, eventId);
    }

    public Optional<Registration> getRegistration(@NonNull String eventId, long chatId) {
        return registrationRepository.findOne(eventId, chatId);
    }

    public Registration createRegistration(@NonNull Registration registration) {
        return registrationRepository.insert(registration);
    }

    public void deleteRegistration(@NonNull String eventId, long chatId) {
        registrationRepository.deleteByEventId(eventId, chatId);
    }

    public void deleteRegistrations(long chatId) {
        registrationRepository.deleteByChatId(chatId);
    }
}
