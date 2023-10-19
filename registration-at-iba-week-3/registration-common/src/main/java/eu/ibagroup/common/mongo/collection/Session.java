package eu.ibagroup.common.mongo.collection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "chatId")
@RequiredArgsConstructor
@Document(collection = "sessions")
public class Session {

    @Id
    @NonNull
    private Long chatId;

    @Field(targetType = FieldType.STRING)
    private State state = State.DEFAULT;

    @Field(targetType = FieldType.DATE_TIME)
    private LocalDateTime confirmedTime;

    @Field(targetType = FieldType.BOOLEAN)
    private boolean isConfirmed;

    @Field(targetType = FieldType.STRING)
    private String email;

    @Field(targetType = FieldType.STRING)
    private String name;
}
