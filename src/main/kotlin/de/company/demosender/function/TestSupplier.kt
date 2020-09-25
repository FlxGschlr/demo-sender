package de.company.demosender.function

import mu.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import reactor.core.publisher.DirectProcessor
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

private val logger = KotlinLogging.logger {}

@Configuration
class TestSupplier {

    private val processor: DirectProcessor<ExampleEvent> =
            DirectProcessor.create()

    internal val sink: FluxSink<ExampleEvent> = processor.sink()

    /**
     * Supplier output is bound to "examples" destination.
     * Forwards events to kafka topic.
     */
    @Bean
    fun exampleEventsSupplier(): Supplier<Flux<Message<ExampleEvent>>> = Supplier {
        processor
                .map { it.toMessage() }
                .doOnNext { logger.info { "Sent $it" } }
    }

    private fun ExampleEvent.toMessage() = MessageBuilder
            .withPayload(this)
            .build()


    /**
     * Async listener which forwards application events of type ExampleEvents to the sink/input of the Supplier
     */
    @Async
    @EventListener
    fun publishRuleModificationEvent(ruleModificationEvent: ExampleEvent): CompletableFuture<Void> =
            Mono.fromCallable { sink.next(ruleModificationEvent) }
                    .publishOn(Schedulers.boundedElastic())
                    .then()
                    .toFuture()
}
