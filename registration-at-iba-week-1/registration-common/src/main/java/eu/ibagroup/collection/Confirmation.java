package eu.ibagroup.collection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

/**
 * Confirmation POJO
 *
 * @author Mikalai Zaikin (mzaikin@ibagroup.eu)
 * @since 4Q2022
 */
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@RequiredArgsConstructor
public class Confirmation {

    @NonNull
    private final String id;

    @NonNull
    private final Long chatId;

    @NonNull
    private final LocalDateTime creationTime;

    @NonNull
    private final String email;

}
