package org.learning;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface HelloWorldActivities {

    @ActivityMethod
    String composeGreeting(String name);
}
