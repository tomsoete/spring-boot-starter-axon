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
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.domain.IdentifierFactory;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Files;

@Configuration
@ConditionalOnClass(CommandBus.class)
public class AxonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CommandBus commandBus() {
        return new SimpleCommandBus();
    }

    @Bean
    @ConditionalOnMissingBean
    public CommandGateway commandGateway(CommandBus commandBus) {
        return new DefaultCommandGateway(commandBus);
    }

    @Bean
    @ConditionalOnMissingBean
    public EventBus eventBus() {
        return new SimpleEventBus();
    }

    @Bean
    @ConditionalOnMissingBean
    public EventStore eventStore() throws Exception {
        File baseDir = Files.createTempDirectory("events").toFile();
        return new FileSystemEventStore(new SimpleEventFileResolver(baseDir));
    }

    @Bean
    public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor(CommandBus commandBus) {
        AnnotationCommandHandlerBeanPostProcessor bean = new AnnotationCommandHandlerBeanPostProcessor();
        bean.setCommandBus(commandBus);
        return bean;
    }

    @Bean
    public AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor(EventBus eventBus) {
        AnnotationEventListenerBeanPostProcessor bean = new AnnotationEventListenerBeanPostProcessor();
        bean.setEventBus(eventBus);
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean
    public IdentifierFactory identifierFactory() {
        return IdentifierFactory.getInstance();
    }
}
