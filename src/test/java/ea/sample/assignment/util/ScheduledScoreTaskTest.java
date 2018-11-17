package ea.sample.assignment.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ScheduledScoreTaskTest {

    @Mock
    private ThreadPoolManager fakeManager;


    private ScheduledScoreTask scoreTask;

    @Before
    public void setUp() {
        scoreTask = new ScheduledScoreTask( fakeManager, 1 );
    }


    @Test
    public void runMethodDelegatesToPoolManager() {

        doNothing().when( fakeManager ).processMessagesToBeScored( anyInt() );

        scoreTask.run();

        verify( fakeManager ).processMessagesToBeScored( anyInt() );
    }

}