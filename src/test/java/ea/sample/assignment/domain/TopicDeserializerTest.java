package ea.sample.assignment.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

public class TopicDeserializerTest {

    @Test
    public void canDeserializeEmptyCollectionOfTopics() throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();

        module.addDeserializer( Topic.class, new TopicDeserializer() );

        assertNotNull( mapper.readValue( "[]", Topic.class ) );
    }

    @Ignore
    public void canDeserializeSingleTopics() throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();

        module.addDeserializer( Topic.class, new TopicDeserializer() );

        Topic topic = mapper.readValue( "[ {\"topic\":\"topic name\", \"id\":123, \"messages\":[12,23,45] }]", Topic.class );

        assertThat( topic.getName() ).isEqualTo( "topic name" );
    }
}