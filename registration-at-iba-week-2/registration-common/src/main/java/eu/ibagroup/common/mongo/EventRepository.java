package eu.ibagroup.common.mongo;

import eu.ibagroup.common.mongo.collection.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface EventRepository extends MongoRepository<Event, String>, CustomEventRepository {
    @Query("{startDate : {$gt: ?0}}")
    List<Event> getAllActual(LocalDate today);

    @Query("{id : {$in: ?0}}")
    List<Event> getAllByIds(Set<String> eventIds);
}
