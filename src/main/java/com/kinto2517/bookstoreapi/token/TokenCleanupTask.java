package com.kinto2517.bookstoreapi.token;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class TokenCleanupTask {

    private final Logger logger = LoggerFactory.getLogger(TokenCleanupTask.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Scheduled(cron = "0 */5 * * * *")
    @Transactional
    public void cleanupExpiredTokens() {
        logger.info("Running cleanup of expired tokens");
        String deleteQuery = "DELETE FROM Token t WHERE t.expired = true";
        int deletedCount = entityManager.createQuery(deleteQuery).executeUpdate();
        logger.info("Deleted {} expired tokens", deletedCount);
        System.out.println("Deleted " + deletedCount + " expired tokens.");
    }
}
