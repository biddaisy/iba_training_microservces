package eu.ibagroup.collection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

/**
 * Event POJO
 *
 * @author Mikalai Zaikin (mzaikin@ibagroup.eu)
 * @since 4Q2022
 */
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Event {

    @NonNull
    private String id;

    @NonNull
    private String name;

    @NonNull
    private LocalDate startDate;

    @NonNull
    private LocalDate endDate;

    @NonNull
    private Integer maxNumOfParticipants;

    private Integer currentNumOfParticipants = 0;
}
