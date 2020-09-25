package demo

import org.springframework.cloud.contract.spec.Contract

Contract.make {

    label "example-event-supplier"

    input {
        triggeredBy('sendExampleEvent()')
    }

    outputMessage {
        sentTo("examples")

        body(file("expectedExampleEvent.json").asString())
    }
}
