package eu.ibagroup.common.mongo;

import eu.ibagroup.common.mongo.collection.Session;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface SessionRepository extends MongoRepository<Session, Long> {
    Session findAllByChatId(Long chatId);
}
