package de.company.demosender.function

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier
import org.springframework.context.ApplicationEventPublisher
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMessageVerifier
class DemoSenderBaseClass {

    @Autowired
    lateinit var eventPublisher: ApplicationEventPublisher

    /**
     * function to create ExampleEvent on "examples" topic in test
     * this function -> async listener -> supplier sink -> "examples" topic
     */
    fun sendExampleEvent() {
        val event = ExampleEvent(type = "12345")
        eventPublisher.publishEvent(event)
    }
}
