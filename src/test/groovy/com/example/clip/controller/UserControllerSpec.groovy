package com.example.clip.controller

import com.example.clip.dtos.PaymentDto
import com.example.clip.dtos.ReportDto
import com.example.clip.dtos.TransactionDto
import com.example.clip.enums.Status
import com.example.clip.model.Transaction
import com.example.clip.repository.PaymentRepository
import com.example.clip.repository.TransactionRepository
import com.example.clip.request.LoginRequest
import com.example.clip.request.PaymentRequest
import com.example.clip.request.UserRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.*
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerSpec extends Specification {
    @Value('${local.server.port}')
    int port
    RestTemplate rest = new RestTemplate()

    @Autowired
    PaymentRepository paymentRepository

    @Autowired
    TransactionRepository transactionRepository

    def "Should get all payments"(){
        given:'a body login request'
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        LoginRequest loginRequest = new LoginRequest()
        loginRequest.with {
            user = "pinky"
            password = "pwd"
        }

        def httpEntity = new HttpEntity<Object>(loginRequest, headers)

        when:
        def response = rest.exchange("http://localhost:${ port }/login",
                HttpMethod.POST, httpEntity, UserRequest)


        then:
        assert response.statusCode == HttpStatus.OK
        assert response.body
        response.body.with {
            assert token
        }


        headers.set("Authorization", "Bearer "+response.body.token)
        PaymentRequest cmd = new PaymentRequest()
        cmd.with {
            userId = 'user'
            amount = 123
        }

        def httpEntityPost = new HttpEntity<Object>(cmd, headers)


        when:
        def resp = rest.exchange("http://localhost:${ port }/api/clip/createPayload", HttpMethod.POST, httpEntityPost, Map)


        then:
        assert   resp.statusCode == HttpStatus.OK

        when:
        def bdRow = paymentRepository.findAll()

        then:
        assert !bdRow.isEmpty()

    }

    def "should get all user payments"() {
        given:'a body login request'
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        LoginRequest loginRequest = new LoginRequest()
        loginRequest.with {
            user = "pinky"
            password = "pwd"
        }

        def httpEntity = new HttpEntity<Object>(loginRequest, headers)

        when:
        def response = rest.exchange("http://localhost:${ port }/login",
                HttpMethod.POST, httpEntity, UserRequest)


        then:
        assert response.statusCode == HttpStatus.OK
        assert response.body
        response.body.with {
            assert token
        }


        headers.set("Authorization", "Bearer "+response.body.token)

        String requestJson = "{}"
        HttpEntity<String> entity = new HttpEntity<>(requestJson.toString(), headers)


        when:

        def responseEntity = rest.exchange("http://localhost:${ port }/api/clip/user/payment", HttpMethod.GET, entity, List<PaymentDto>)


        then:"get all previous seed data"
        assert  responseEntity.statusCode == HttpStatus.OK

        responseEntity.body.first().with {
            assert it.id
            assert  it.userId == 'user'
            assert  it.amount == 123
        }
    }

    def "should get all user disbursement"() {
        given:'a body login request'
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        LoginRequest loginRequest = new LoginRequest()
        loginRequest.with {
            user = "pinky"
            password = "pwd"
        }

        def httpEntity = new HttpEntity<Object>(loginRequest, headers)

        when:
        def response = rest.exchange("http://localhost:${ port }/login",
                HttpMethod.POST, httpEntity, UserRequest)


        then:
        assert response.statusCode == HttpStatus.OK
        assert response.body
        response.body.with {
            assert token
        }


        headers.set("Authorization", "Bearer "+response.body.token)

        String requestJson = "{}"
        HttpEntity<String> entity = new HttpEntity<>(requestJson.toString(), headers)

        Transaction transaction1 = new Transaction()
        transaction1.with {
            userId = 'user'
            amount = 100
        }
        transactionRepository.save(transaction1)

        when:
        def resp = rest.exchange("http://localhost:${ port }/api/clip/user/disbursement", HttpMethod.GET, entity, List<PaymentDto>)

        then:"All the transactions must be disbursement"
        assert resp.statusCode == HttpStatus.OK
        resp.body.first().with {
            assert it.id
            assert it.userId == transaction1.userId
            assert it.amount == transaction1.amount
            assert it.status == Status.PROCESSED.toString()
            assert it.disbursement == 96.5
        }
    }

    def "should get report"() {
        given:'a body login request'
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        LoginRequest loginRequest = new LoginRequest()
        loginRequest.with {
            user = "pinky"
            password = "pwd"
        }

        def httpEntity = new HttpEntity<Object>(loginRequest, headers)

        when:
        def response = rest.exchange("http://localhost:${ port }/login",
                HttpMethod.POST, httpEntity, UserRequest)


        then:
        assert response.statusCode == HttpStatus.OK
        assert response.body
        response.body.with {
            assert token
        }


        headers.set("Authorization", "Bearer "+response.body.token)

        String requestJson = "{}"
        HttpEntity<String> entity = new HttpEntity<>(requestJson.toString(), headers)

        Transaction transaction1 = new Transaction()
        transaction1.with {
            userId = 'user'
            amount = 100
        }
        transactionRepository.save(transaction1)

        Transaction transaction2 = new Transaction()
        transaction2.with {
            userId = 'user'
            amount = 100
        }
        transaction2.processDisbursement()
        transactionRepository.save(transaction2)

        Transaction transaction3 = new Transaction()
        transaction3.with {
            userId = '666'
            amount = 1000
        }
        transactionRepository.save(transaction3)

        Transaction transaction4 = new Transaction()
        transaction4.with {
            userId = '666'
            amount = 1000
        }
        transaction4.processDisbursement()
        transactionRepository.save(transaction4)


        when:
        def resp = rest.exchange("http://localhost:${ port }/api/clip/user/report", HttpMethod.GET, entity, List<ReportDto> )


        then:
        assert resp.statusCode == HttpStatus.OK
        assert resp.body.find {it.userId == '666'}
        assert resp.body.find {it.userId == 'user'}


    }

}

