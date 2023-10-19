package eu.ibagroup.common.mongo.collection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Document(collection = "events")
public class Event {
    @Id
    @NonNull
    private String id;

    @NonNull
    private String name;

    @Field(targetType = FieldType.DATE_TIME)
    @NonNull
    private LocalDate startDate;

    @Field(targetType = FieldType.DATE_TIME)
    @NonNull
    private LocalDate endDate;

    @NonNull
    private Integer maxNumOfParticipants;

    private Integer currentNumOfParticipants = 0;

    public String asTelegramString() {
        return """
                Event ID: <code>%s</code> \
                event name: <code>%s</code> \
                starts: <code>%s</code> \
                ends: <code>%s</code> \
                available seats: <code>%d</code>""".formatted(id, name, startDate, endDate, maxNumOfParticipants - currentNumOfParticipants);
    }
}
