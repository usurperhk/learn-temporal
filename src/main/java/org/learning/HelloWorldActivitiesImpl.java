package org.learning;

public class HelloWorldActivitiesImpl implements HelloWorldActivities {
    @Override
    public String composeGreeting(String name) {
        return "Hello " + name + "!";
    }
}
