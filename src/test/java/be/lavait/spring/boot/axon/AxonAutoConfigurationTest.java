/*
 * Copyright (c) 2015 Tom Soete
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.lavait.spring.boot.axon;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.domain.AggregateRoot;
import org.axonframework.domain.IdentifierFactory;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.EventSourcedAggregateRoot;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventstore.EventStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

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
    public void autoConfiguration() throws Exception {
        this.context.register(AxonAutoConfiguration.class);
        this.context.refresh();
        assertNotNull(this.context.getBean(CommandBus.class));
        assertNotNull(this.context.getBean(CommandGateway.class));
        assertNotNull(this.context.getBean(EventBus.class));
        assertNotNull(this.context.getBean(EventStore.class));
        assertNotNull(this.context.getBean(IdentifierFactory.class));
    }

    @Test
    public void autoEventSourcingRepositoryCreated(){
        this.context.register(AxonAutoConfiguration.class);
        this.context.register(DummyAggregateRoot.class);
        this.context.refresh();
        assertNotNull(this.context.getBean(EventSourcingRepository.class));
    }




}