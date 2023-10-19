package eu.ibagroup.common.mongo;

import eu.ibagroup.common.mongo.collection.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class CustomEventRepositoryImpl implements CustomEventRepository {

    public static final String CURRENT_NUM_OF_PARTICIPANTS = "currentNumOfParticipants";
    public static final String ID = "id";
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void freeOneSeat(List<Event> events) {
        if (events.isEmpty()) return;
        List<Pair<Query, Update>> updates = events.stream().map(this::getQueryUpdatePair).toList();
        upsertEvents(updates);
    }

    private void upsertEvents(List<Pair<Query, Update>> updates) {
        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Event.class);
        bulkOperations.upsert(updates);
        bulkOperations.execute();
    }

    private Pair<Query, Update> getQueryUpdatePair(Event event) {
        return Pair.of(
                Query.query(Criteria.where(ID).is(event.getId())),
                Update.update(CURRENT_NUM_OF_PARTICIPANTS, event.getCurrentNumOfParticipants() - 1)
        );
    }
}
