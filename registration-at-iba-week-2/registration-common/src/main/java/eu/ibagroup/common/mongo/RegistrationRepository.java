package eu.ibagroup.common.mongo;

import eu.ibagroup.common.mongo.collection.Registration;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends MongoRepository<Registration, String> {
    @Query("{deleteDate : {$gt: ?0}, 'chatId' : {$eq: ?1}}")
    List<Registration> getAllByChat(LocalDate today, long chatId);

    @Query("{deleteDate : {$gt: ?0}, 'eventId' : {$eq: ?1}}")
    List<Registration> getAllByEvent(LocalDate date, String eventId);

    @Query("{deleteDate : {$gt: ?0}}")
    List<Registration> getAllActual(LocalDate date);

    @Query("{eventId : {$eq: ?0}, 'chatId' : {$eq: ?1}}")
    Optional<Registration> findOne(String eventId, long chatId);

    @DeleteQuery("{eventId : {$eq: ?0}, 'chatId' : {$eq: ?1}}")
    int deleteByEventId(String eventId, long chatId);

    @DeleteQuery("{'chatId' : {$eq: ?0}}")
    int deleteByChatId(long chatId);
}
