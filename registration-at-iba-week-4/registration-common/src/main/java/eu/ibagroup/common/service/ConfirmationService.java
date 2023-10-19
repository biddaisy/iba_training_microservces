package eu.ibagroup.common.service;

import eu.ibagroup.common.mongo.ConfirmationRepository;
import eu.ibagroup.common.mongo.collection.Confirmation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationService {

    private final ConfirmationRepository confirmationRepo;

    public Confirmation createConfirmation(Confirmation confirmation) {
        return confirmationRepo.insert(confirmation);
    }

    public Optional<Confirmation> findConfirmation(String id) {
        return confirmationRepo.findById(id);
    }
}
