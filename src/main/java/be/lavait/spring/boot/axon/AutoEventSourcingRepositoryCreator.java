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
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.domain.AggregateRoot;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.EventSourcedAggregateRoot;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class AutoEventSourcingRepositoryCreator implements BeanPostProcessor, BeanFactoryAware {

    private EventStore eventStore;

    private EventBus eventBus;

    private CommandBus commandBus;

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (EventSourcedAggregateRoot.class.isAssignableFrom(bean.getClass())) {
            AggregateRoot aggregateRoot = (AggregateRoot) bean;
            EventSourcingRepository repository = new EventSourcingRepository(bean.getClass(), eventStore);
            repository.setEventBus(eventBus);
            beanFactory.registerSingleton(beanName + "Repository", repository);
            AggregateAnnotationCommandHandler.subscribe(aggregateRoot.getClass(), repository, commandBus);
        }

        return bean;
    }

    public void setEventStore(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void setCommandBus(CommandBus commandBus) {
        this.commandBus = commandBus;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }
}
