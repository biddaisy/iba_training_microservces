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
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
@Document(collection = "confirmations")
public class Confirmation {

    @Id
    @NonNull
    private final String id;

    @NonNull
    private final Long chatId;

    @Field(targetType = FieldType.DATE_TIME)
    @NonNull
    private final LocalDateTime creationTime;

    @NonNull
    private final String email;
}
