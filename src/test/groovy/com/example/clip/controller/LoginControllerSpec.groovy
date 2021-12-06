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
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerSpec extends Specification {
    @Value('${local.server.port}')
    int port
    RestTemplate rest = new RestTemplate()

    @Autowired
    PaymentRepository paymentRepository

    @Autowired
    TransactionRepository transactionRepository

    def "Should get login"(){
        given:'a body request'
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


    }

}
