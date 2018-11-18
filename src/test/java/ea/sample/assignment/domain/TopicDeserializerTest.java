package ea.sample.assignment.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

public class TopicDeserializerTest {

    /**
     * @Override public EverlinkCardInquiryResponse deserialize( JsonParser jsonParser, DeserializationContext deserializationContext )
     * throws IOException, JsonProcessingException
     * {
     * ObjectCodec codec = jsonParser.getCodec();
     * JsonNode node = codec.readTree( jsonParser );
     * <p>
     * ResponseStatus responseStatus = valueFor( node.get( REASON_CODE_NODE_NAME ).getTextValue() );
     * <p>
     * String userToken = getStringNodeValue( node, USER_TOKEN_NODE_NAME ).orElse( "" );
     * <p>
     * Set<CardLock> cardLocks = getCardLocks( node );
     * <p>
     * return new EverlinkCardInquiryResponse( responseStatus, userToken, cardLocks );
     * }
     * <p>
     * private CardLock createOneCardLock( Map.Entry<String, JsonNode> entry )
     * {
     * return new CardLock( LockType.valueOf( entry.getKey() ), LockValue.valueOf( entry.getValue().getTextValue() ) );
     * }
     * <p>
     * private <T> Stream<T> asStream( Iterator<T> sourceIterator )
     * {
     * Iterable<T> iterable = () -> sourceIterator;
     * return StreamSupport.stream( iterable.spliterator(), false );
     * }
     * <p>
     * private Optional<String> getStringNodeValue( JsonNode node, String nodeName )
     * {
     * JsonNode jsonNode = node.get( nodeName );
     * if ( jsonNode != null )
     * {
     * return Optional.ofNullable( jsonNode.getTextValue() );
     * }
     * else
     * {
     * <p>
     * return Optional.empty();
     * }
     * }
     * <p>
     * private Set<CardLock> getCardLocks( JsonNode node )
     * {
     * JsonNode respList = node.get( RESP_LIST_NODE_NAME );
     * if ( respList != null )
     * {
     * return asStream( respList.getFields() )
     * .map( this::createOneCardLock )
     * .collect( toSet() );
     * }
     * else
     * {
     * return Collections.emptySet();
     * }
     * }
     */
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