package eu.ibagroup.common.service;

import eu.ibagroup.common.mongo.collection.Confirmation;
import eu.ibagroup.common.mongo.ConfirmationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationService {

    private final ConfirmationRepository confirmationRepo;

    public Confirmation createConfirmation(Confirmation c) {
        return confirmationRepo.insert(c);
    }

    public Optional<Confirmation> findConfirmation(String id) {
        return confirmationRepo.findById(id);
    }
}
