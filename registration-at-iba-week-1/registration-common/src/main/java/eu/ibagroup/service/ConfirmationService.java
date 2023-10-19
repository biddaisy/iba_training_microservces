package eu.ibagroup.service;

import eu.ibagroup.collection.Confirmation;
import eu.ibagroup.exception.DuplicateEntityException;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static eu.ibagroup.exception.DuplicateEntityException.throwDuplicateEntityException;

/**
 * Service to work with confirmations
 *
 *  @author Mikalai Zaikin (mzaikin@ibagroup.eu)
 *  @since 4Q2022
 */
@Service
public class ConfirmationService {

    private final Map<String, Confirmation> confirmations = new HashMap<>();

    public Confirmation createConfirmation(Confirmation confirmation) throws DuplicateEntityException {
        val id = confirmation.getId();
        return !hasConfirmation(id) ? createConfirmation(confirmation, id) : throwDuplicateEntityException(id);
    }

    private boolean hasConfirmation(String id) {
        return confirmations.containsKey(id);
    }

    public Optional<Confirmation> findConfirmation(String id) {
        return Optional.ofNullable(confirmations.get(id));
    }

    private Confirmation createConfirmation(Confirmation confirmation, String id) {
        confirmations.put(id, confirmation);
        return confirmation;
    }
}
