package ea.sample.assignment.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class TopicDeserializer extends JsonDeserializer<Topic> {

    @Override
    public Topic deserialize( JsonParser jsonParser, DeserializationContext deserializationContext )
            throws IOException, JsonProcessingException {

        JsonNode node = jsonParser.getCodec().readTree( jsonParser );
        JsonNode topic = node.get( "topic" );

        String name = topic != null ? topic.asText() : "";

        return new Topic( -1, name );
    }
}
