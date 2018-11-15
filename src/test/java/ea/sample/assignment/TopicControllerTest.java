package ea.sample.assignment;

import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ea.sample.assignment.domain.Topic;

@RunWith( SpringRunner.class )
@WebMvcTest( TopicController.class )
public class TopicControllerTest
{
	@Autowired
	MockMvc mockMvc;

	@MockBean
	private TopicService mockTopicsService;

	@Test
	public void getTopics_shouldReturnTopics() throws Exception
	{
		HashSet<Topic> t = new HashSet<>();
		t.add( new Topic( "name" ) );

		when( mockTopicsService.getTopics() ).thenReturn( t );

		mockMvc.perform( MockMvcRequestBuilders.get( "/topics" ) )
				.andExpect( status().isOk() )
				.andExpect( jsonPath( "$", hasSize( 1 ) ) );
	}

	@Test
	public void givenNonExistentTopic_GetTopicShouldReturnNotFound() throws Exception
	{
		when( mockTopicsService.getTopic( anyString() ) ).thenThrow( new TopicNotFoundException( "some error" ) );

		mockMvc.perform( MockMvcRequestBuilders.get( "/topics/sport" ) ).andExpect( status().isNotFound() );

		verify( mockTopicsService ).getTopic( anyString() );
	}

}
