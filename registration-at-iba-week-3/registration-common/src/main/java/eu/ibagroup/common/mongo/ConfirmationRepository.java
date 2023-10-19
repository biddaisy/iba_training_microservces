package eu.ibagroup.common.mongo;

import eu.ibagroup.common.mongo.collection.Confirmation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfirmationRepository extends MongoRepository<Confirmation, String> {

}
