package eu.ibagroup.common.mongo;

import eu.ibagroup.common.mongo.collection.Event;

import java.util.List;

public interface CustomEventRepository {
    void freeOneSeat(List<Event> events);
}
