package com.example.clip.model

import com.example.clip.enums.Status
import spock.lang.Specification

class TransactionModelSpec extends Specification{

    def "Should process a transaction"(){
        given:'A transaction'
        Transaction transaction = new Transaction()
        transaction.with {
            amount = 100.00
            userId = "666"
        }

        expect:
        transaction.with {
            assert amount == 100.00
            assert userId == "666"
            assert status == Status.NEW
            assert !disbursement
        }

        when:
        transaction.processDisbursement()

        then:
        transaction.with {
            assert amount == 100.00
            assert userId == "666"
            assert status == Status.PROCESSED
            assert disbursement == 96.5 //TODO check the README.md point 2.3, 3.5% of 100 isn't 97.5
        }

    }
}
