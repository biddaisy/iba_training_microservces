package eu.ibagroup.bot;

import java.util.StringJoiner;
import java.util.stream.Collector;

public interface Utils {

    static Collector<CharSequence, StringJoiner, String> getEmptyValueCollector(String emptyValue){
        return Collector.of(
                () -> new StringJoiner("\n").setEmptyValue(emptyValue),
                StringJoiner::add,
                StringJoiner::merge,
                StringJoiner::toString);
    }
}
