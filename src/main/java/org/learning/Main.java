package org.learning;

import io.temporal.api.workflowservice.v1.ResetWorkflowExecutionRequest;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.workflow.Workflow;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

        WorkflowClient client = WorkflowClient.newInstance(service);

        final String WORKFLOW_ID = "HelloWorldWorkflowID";

        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setWorkflowId(WORKFLOW_ID)
                .setTaskQueue(Shared.HELLO_WORLD_TASK_QUEUE)
                .build();

        HelloWorldWorkflow workflow = client.newWorkflowStub(HelloWorldWorkflow.class, options);
        String greeting = workflow.getGreeting("Alice");

        String workflowId = WorkflowStub.fromTyped(workflow).getExecution().getWorkflowId();
        System.out.println(workflowId + " " + greeting);



        // reset the workflow execution
        ResetWorkflowExecutionRequest request = ResetWorkflowExecutionRequest.newBuilder()
                .setNamespace("default")
                .setReason("Simply testing the reset flow")
                .setRequestId(UUID.randomUUID().toString())
                .setWorkflowTaskFinishEventId(4)
                .build();

        Workflow.continueAsNew(request);

        // Testing hello world activities
    }
}