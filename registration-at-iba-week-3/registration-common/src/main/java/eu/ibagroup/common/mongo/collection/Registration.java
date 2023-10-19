package eu.ibagroup.common.mongo.collection;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Document(collection = "registrations")
@Builder
public class Registration {
    @Id
    @NonNull
    private String id;

    @NonNull
    private final String eventId;

    @NonNull
    private final String eventName;

    @Field(targetType = FieldType.DATE_TIME)
    @NonNull
    private final LocalDate eventStartDate;

    private final long chatId;

    @Field(targetType = FieldType.DATE_TIME)
    @NonNull
    private final LocalDate createdDate;

    @Field(targetType = FieldType.DATE_TIME)
    @NonNull
    private final LocalDate deleteDate;

    @NonNull
    private final String email;

    public String asTelegramString() {
        return "Event ID: <code>%s</code> event name: <code>%s</code> event starts: <code>%s</code>".formatted(eventId, eventName, eventStartDate);
    }

    public String asRestString() {
        return "Event ID: %s | event name: %s | event starts: %s | participant email: %s".formatted(eventId, eventName, eventStartDate, email);
    }

}
