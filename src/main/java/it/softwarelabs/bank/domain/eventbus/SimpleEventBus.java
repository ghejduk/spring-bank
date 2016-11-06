package it.softwarelabs.bank.domain.eventbus;

import it.softwarelabs.bank.domain.eventstore.Event;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public final class SimpleEventBus implements EventBus {

    private static final String METHOD = "handle";
    private final Map<Class, Set<EventSubscriber>> eventSubscribers = new HashMap<>();

    public SimpleEventBus(List<EventSubscriber> subscribers) {
        registerSubscribers(subscribers);
    }

    private void registerSubscribers(List<EventSubscriber> subscribers) {
        for (EventSubscriber eventSubscriber : subscribers) {
            for (Method method : eventSubscriber.getClass().getMethods()) {
                if (AnnotationUtils.findAnnotation(method, Subscribe.class) != null) {
                    if (!method.getName().equals(METHOD)) {
                        throw new RuntimeException("Subscribe method name is different than '" + METHOD + "'.");
                    }

                    if (method.getParameterCount() != 1) {
                        throw new RuntimeException("Subscriber method can have only one parameter.");
                    }
                    final Class eventClass = method.getParameterTypes()[0];
                    this.eventSubscribers.computeIfAbsent(eventClass, es -> new HashSet<>());
                    this.eventSubscribers.get(eventClass).add(eventSubscriber);
                }
            }
        }
    }

    @Override
    public void publish(Event event) {
        final Class<? extends Event> eventClass = event.getClass();

        for (EventSubscriber eventSubscriber : eventSubscribers.get(eventClass)) {
            try {
                final Method method = eventSubscriber.getClass().getDeclaredMethod(METHOD, eventClass);
                method.invoke(eventSubscriber, event);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException("Could not handle event " + eventClass.toString(), e);
            }
        }
    }

    @Override
    public void publish(Set<Event> events) {
        events.forEach(this::publish);
    }
}
