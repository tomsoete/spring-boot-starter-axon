package be.lavait.spring.boot.axon;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.domain.IdentifierFactory;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventstore.EventStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;

public class AxonAutoConfigurationTest {

    private AnnotationConfigApplicationContext context;

    @Before
    public void setUp() {
        this.context = new AnnotationConfigApplicationContext();
    }

    @After
    public void tearDown() {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void autoConfigured() throws Exception {
        this.context.register(AxonAutoConfiguration.class);
        this.context.refresh();
        assertNotNull(this.context.getBean(CommandBus.class));
        assertNotNull(this.context.getBean(CommandGateway.class));
        assertNotNull(this.context.getBean(EventBus.class));
        assertNotNull(this.context.getBean(EventStore.class));
        assertNotNull(this.context.getBean(IdentifierFactory.class));
    }

}