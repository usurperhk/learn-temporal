package helloworldapp;

import io.temporal.client.WorkflowOptions;
import io.temporal.testing.TestWorkflowRule;
import org.junit.Rule;
import org.junit.Test;
import org.learning.HelloWorldActivitiesImpl;
import org.learning.HelloWorldWorkflow;
import org.learning.HelloWorldWorkflowImpl;

import static org.junit.Assert.assertEquals;

public class HelloWorldWorkflowTest {

    @Rule
    public TestWorkflowRule testWorkflowRule =
            TestWorkflowRule.newBuilder()
                    .setWorkflowTypes(HelloWorldWorkflowImpl.class)
                    .setDoNotStart(true)
                    .build();

    @Test
    public void testMockedGreeting() {
        testWorkflowRule.getWorker().registerActivitiesImplementations(new HelloWorldActivitiesImpl());
        testWorkflowRule.getTestEnvironment().start();

        HelloWorldWorkflow workflow =
                testWorkflowRule
                        .getWorkflowClient()
                        .newWorkflowStub(
                                HelloWorldWorkflow.class,
                                WorkflowOptions.newBuilder().setTaskQueue(testWorkflowRule.getTaskQueue()).build());
        String greeting = workflow.getGreeting("John");
        assertEquals("Hello John!", greeting);
        testWorkflowRule.getTestEnvironment().shutdown();
    }
}
