package com.abac.crew.common.exception.resilience4j;

import com.abac.crew.common.exception.ApplicationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.CassandraInternalException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FailurePredicate.class)
public class FailurePredicateTest {

    @Autowired
    public FailurePredicate predicate;

    @Test
    public void test_NoneGenericAPIException_returnTrue() {
        final boolean circuitBreakerAffected =
                predicate.test(new CassandraInternalException("db down", new CassandraInternalException("db down")));

        assertTrue(circuitBreakerAffected);
    }

    @Test
    public void test_UnauthorizedException_returnFalse() {
        final boolean circuitBreakerAffected = predicate.test(new ApplicationException.Unauthorized());

        assertFalse(circuitBreakerAffected);
    }

    @Test
    public void test_ForbiddenException_returnFalse() {
        final boolean circuitBreakerAffected = predicate.test(new ApplicationException.Forbidden());

        assertFalse(circuitBreakerAffected);
    }

    @Test
    public void test_InternalServerErrorException_returnFalse() {
        final boolean circuitBreakerAffected = predicate.test(new ApplicationException.InternalServerError());

        assertTrue(circuitBreakerAffected);
    }

    @Test
    public void test_BadGatewayException_returnFalse() {
        final boolean circuitBreakerAffected = predicate.test(new ApplicationException.BadGateway());

        assertTrue(circuitBreakerAffected);
    }

    @Test
    public void test_ServiceUnavailableException_returnFalse() {
        final boolean circuitBreakerAffected = predicate.test(new ApplicationException.ServiceUnavailable());

        assertTrue(circuitBreakerAffected);
    }
}
