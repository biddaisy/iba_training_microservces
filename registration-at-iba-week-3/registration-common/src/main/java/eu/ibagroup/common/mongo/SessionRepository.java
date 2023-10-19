package eu.ibagroup.common.mongo;

import eu.ibagroup.common.mongo.collection.Session;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionRepository extends MongoRepository<Session, Long> {
    Session findAllByChatId(Long chatId);
}
