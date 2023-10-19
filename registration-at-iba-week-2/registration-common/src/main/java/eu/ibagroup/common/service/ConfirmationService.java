package eu.ibagroup.common.service;

import eu.ibagroup.common.mongo.ConfirmationRepository;
import eu.ibagroup.common.mongo.collection.Confirmation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationService {

    private final ConfirmationRepository confirmationRepository;

    public Optional<Confirmation> findConfirmation(@NonNull String id) {
        return confirmationRepository.findById(id);
    }
}
