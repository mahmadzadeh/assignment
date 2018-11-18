package ea.sample.assignment;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;
import ea.sample.assignment.domain.User;
import ea.sample.assignment.exeptions.UserNotFoundException;
import ea.sample.assignment.service.TopicService;
import ea.sample.assignment.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TopicController.class)
public class UserControllerTest {

    private final User USER = new User( 1, "name", "email", 0 );

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService mockUserService;

    @MockBean
    private TopicService mockTopicService;

    @Test
    public void getUserShouldReturnAllUsers() throws Exception {

        Set<User> t = new HashSet<>();

        t.add( USER );

        when( mockUserService.getUsers() ).thenReturn( t );

        mockMvc.perform( MockMvcRequestBuilders.get( "/users" ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.users", hasSize( 1 ) ) );
    }


    @Test
    public void givenNonUserThenGetUsersShouldReturnNotFound() throws Exception {
        when( mockUserService.getUser( anyLong() ) ).thenThrow( new UserNotFoundException( "Invalid user id " ) );

        mockMvc.perform( MockMvcRequestBuilders.get( "/users/2222" ) ).andExpect( status().isNotFound() );

        verify( mockUserService ).getUser( anyLong() );
    }

    @Test
    public void givenInvalidUserThenGetMessagesReturnsNotFound() throws Exception {
        when( mockUserService.getUserMessages( anyLong() ) ).thenThrow( new UserNotFoundException( "some error" ) );

        mockMvc.perform( MockMvcRequestBuilders.get( "/users/111/messages" ) ).andExpect( status().isNotFound() );

        verify( mockUserService ).getUserMessages( anyLong() );
    }

    @Test
    public void givenValidUserIdThenGetMessagesReturnsAllMessagesForUser() throws Exception {
        Set<Message> messages = getListOfTestMessages( 10 );

        when( mockUserService.getUserMessages( anyLong() ) ).thenReturn( messages );

        mockMvc.perform( MockMvcRequestBuilders.get( "/users/111/messages" )
                .contentType( MediaType.APPLICATION_XML ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.messages", hasSize( messages.size() ) ) );

        verify( mockUserService ).getUserMessages( anyLong() );
    }

    @Test
    public void givenValidUserIdThenGetSubsReturnsAllTopicsSubedByUser() throws Exception {
        Set<String> subscriptions = getListOfTestTopics( 2 );

        when( mockUserService.getUserSubscribedTopics( anyLong() ) ).thenReturn( subscriptions );
        when( mockTopicService.getTopic( anyString() ) )
                .thenReturn( new Topic( 222, "sports" ) )
                .thenReturn( new Topic( 223, "comedy" ) );

        mockMvc.perform( MockMvcRequestBuilders.get( "/users/111/subscriptions" )
                .contentType( MediaType.APPLICATION_XML )
                .characterEncoding( "utf-8" ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.topics", hasSize( subscriptions.size() ) ) );

        verify( mockUserService ).getUserSubscribedTopics( anyLong() );
    }

    @Test
    public void givenNonExistentTopicThenCreateTopicAndMessage() throws Exception {

        String subscriptionTopic = "{\"topic\":\"sports\" }";

        Topic sportsTopic = new Topic( 111, "sports" );

        when( mockUserService.createSubscriptionFor( anyLong(), any( Topic.class ) ) ).thenReturn( sportsTopic );
        when( mockTopicService.getTopic( anyString() ) ).thenReturn( new Topic( 222, "sports" ) );

        mockMvc.perform(
                post( "/users/1111/subscriptions" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( subscriptionTopic )
                        .characterEncoding( "utf-8" ) )
                .andExpect( status().isOk() )
                .andDo( print() )
                .andExpect( jsonPath( "name", is( "sports" ) ) );

        verify( mockUserService ).createSubscriptionFor( anyLong(), any( Topic.class ) );
    }

    private Set<Message> getListOfTestMessages( int count ) {

        Set<Message> messages = new HashSet<>();

        for ( int i = 0; i < count; i++ ) {
            messages.add( new Message( i, UUID.randomUUID().toString(), 0, 0 ) );
        }

        return messages;
    }

    private Set<String> getListOfTestTopics( int count ) {

        Set<String> topics = new HashSet<>();

        for ( int i = 0; i < count; i++ ) {
            topics.add( UUID.randomUUID().toString() );
        }

        return topics;
    }

}