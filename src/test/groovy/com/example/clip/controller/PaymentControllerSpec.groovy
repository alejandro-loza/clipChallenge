package com.example.clip.controller

import com.example.clip.repository.PaymentRepository
import com.example.clip.request.PaymentRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentControllerSpec extends Specification {
    @Value('${local.server.port}')
    int port
    RestTemplate rest = new RestTemplate()

    @Autowired
    PaymentRepository paymentRepository

    def "Should create and log Payload Created Successfully"(){
        given:'a body request'
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        PaymentRequest cmd = new PaymentRequest()
        cmd.with {
            userId = 'user'
            amount = 123
        }

        def httpEntity = new HttpEntity<Object>(cmd, headers)


        when:
        def response = rest.exchange("http://localhost:${ port }/api/clip/createPayload", HttpMethod.POST, httpEntity, Map)


        then:
        assert   response.statusCode == HttpStatus.OK

        when:
        def bdRow = paymentRepository.findAll()

        then:
        assert !bdRow.isEmpty()

    }



}
