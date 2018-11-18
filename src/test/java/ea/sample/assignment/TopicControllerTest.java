package ea.sample.assignment;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;
import ea.sample.assignment.exeptions.MessageNotFoundException;
import ea.sample.assignment.exeptions.TopicNotFoundException;
import ea.sample.assignment.service.TopicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TopicController.class)
public class TopicControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TopicService mockTopicsService;

    @Test
    public void getTopics_shouldReturnTopics() throws Exception {
        HashSet<Topic> t = new HashSet<>();
        t.add( new Topic( 1, "name" ) );

        when( mockTopicsService.getTopics() ).thenReturn( t );

        mockMvc.perform( MockMvcRequestBuilders.get( "/topics/" ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.topics", hasSize( 1 ) ) );
    }

    @Test
    public void givenNonExistentTopic_GetTopicShouldReturnNotFound() throws Exception {
        when( mockTopicsService.getTopic( anyString() ) ).thenThrow( new TopicNotFoundException( "some error" ) );

        mockMvc.perform( MockMvcRequestBuilders.get( "/topics/sport" ) ).andExpect( status().isNotFound() );

        verify( mockTopicsService ).getTopic( anyString() );
    }

    @Test
    public void givenInvalidTopicThenGetMessagesReturnsNotFound() throws Exception {
        when( mockTopicsService.getTopicMessages( anyString(), anyInt() ) ).thenThrow( new TopicNotFoundException( "some error" ) );

        mockMvc.perform( MockMvcRequestBuilders.get( "/topics/sport/messages" ) ).andExpect( status().isNotFound() );

        verify( mockTopicsService ).getTopicMessages( anyString(), anyInt() );
    }

    @Test
    public void givenValidTopicThenGetMessagesReturnsLastTenMessages() throws Exception {
        List<Message> messages = getListOfTestMessages( 10 );

        when( mockTopicsService.getTopicMessages( anyString(), anyInt() ) ).thenReturn( messages );

        mockMvc.perform( MockMvcRequestBuilders.get( "/topics/sport/messages" )
                .contentType( MediaType.APPLICATION_XML ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.messages", hasSize( messages.size() ) ) );

        verify( mockTopicsService ).getTopicMessages( anyString(), anyInt() );
    }

    @Test
    public void givenInvalidTopicNameThenGetSingleMessageReturns_404() throws Exception {

        when( mockTopicsService.getTopicMessage( anyString(), anyLong() ) )
                .thenThrow( new TopicNotFoundException( "" ) );

        mockMvc.perform( MockMvcRequestBuilders.get( "/topics/bogus/messages/2" ) )
                .andExpect( status().isNotFound() );

        verify( mockTopicsService ).getTopicMessage( anyString(), anyLong() );
    }

    @Test
    public void givenValidTopicNameAndInvalidMessageIdThenGetSingleMessageReturns_404() throws Exception {

        when( mockTopicsService.getTopicMessage( anyString(), anyLong() ) )
                .thenThrow( new MessageNotFoundException( "bogus mesage" ) );

        mockMvc.perform( MockMvcRequestBuilders.get( "/topics/sports/messages/2" ) )
                .andExpect( status().isNotFound() );

        verify( mockTopicsService ).getTopicMessage( anyString(), anyLong() );
    }

    @Test
    public void givenValidTopicNameAndValidMessageIdThenGetSingleMessageReturnsIt() throws Exception {

        int expectedId = 2;

        when( mockTopicsService.getTopicMessage( anyString(), anyLong() ) )
                .thenReturn( new Message( expectedId, "msg", 0, 0 ) );

        mockMvc.perform( MockMvcRequestBuilders.get( "/topics/sports/messages/2" ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "id", is( expectedId ) ) );

        verify( mockTopicsService ).getTopicMessage( anyString(), anyLong() );
    }

    @Test
    public void givenNonExistentTopicThenCreateTopicAndMessage() throws Exception {

        String newMsgJson = "{\"message\":\"newly created message\", \"userId\":222 }";

        when( mockTopicsService.createMessageForTopic( anyString(), any( Message.class ) ) )
                .thenReturn( new Message( 1, "newly created message", 0, 0 ) );

        mockMvc.perform(
                post( "/topics/sports/messages" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( newMsgJson ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "id", is( 1 ) ) );

        verify( mockTopicsService ).createMessageForTopic( anyString(), any( Message.class ) );
    }

    private List<Message> getListOfTestMessages( int count ) {

        List<Message> messages = new ArrayList<>();

        for ( int i = 0; i < count; i++ ) {
            messages.add( new Message( i, UUID.randomUUID().toString(), 0, 0 ) );
        }

        return messages;
    }

}
