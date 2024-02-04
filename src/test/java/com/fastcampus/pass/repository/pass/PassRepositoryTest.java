package com.fastcampus.pass.repository.pass;

import com.fastcampus.pass.config.TestBatchConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = TestBatchConfig.class)
class PassRepositoryTest {

    @Autowired
    private PassRepository passRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void test_save() {
        // given
        PassEntity passEntity = new PassEntity();
        passEntity.setPackageSeq(1);
        passEntity.setUserId("A1000000");
        passEntity.setStatus(PassStatus.READY);
        passEntity.setRemainingCount(10);
        passEntity.setStartedAt(LocalDateTime.now());
        passEntity.setEndedAt(LocalDateTime.now());
        passEntity.setExpiredAt(LocalDateTime.now());

        // when
        passRepository.save(passEntity);

        // then
        assertNotNull(passEntity.getPassSeq());
    }

    @Test
    public void test_updateStatusAndRemainingCount() {
        // given
        PassEntity passEntity = new PassEntity();
        passEntity.setPackageSeq(1);
        passEntity.setUserId("A1000000");
        passEntity.setStatus(PassStatus.PROGRESS);
        passEntity.setRemainingCount(10);
        passEntity.setStartedAt(LocalDateTime.now());
        passEntity.setEndedAt(LocalDateTime.now());
        passEntity.setExpiredAt(LocalDateTime.now());
        passRepository.save(passEntity);
        entityManager.flush();
        entityManager.clear();

        // when
        PassEntity updateEntity = passRepository.findById(passEntity.getPassSeq()).orElseThrow();
        updateEntity.setStatus(PassStatus.EXPIRED);
        updateEntity.setRemainingCount(20);
        entityManager.flush();
        entityManager.clear();
        PassEntity updatedEntity = passRepository.findById(updateEntity.getPassSeq()).orElseThrow();

        // then
        assertEquals(updatedEntity.getStatus(), PassStatus.EXPIRED);
        assertEquals(updatedEntity.getRemainingCount(), 20);
    }

    @Test
    public void test_delete() {
        // given
        PassEntity passEntity = new PassEntity();
        passEntity.setPackageSeq(1);
        passEntity.setUserId("A1000000");
        passEntity.setStatus(PassStatus.EXPIRED);
        passEntity.setRemainingCount(10);
        passEntity.setStartedAt(LocalDateTime.now());
        passEntity.setEndedAt(LocalDateTime.now());
        passEntity.setExpiredAt(LocalDateTime.now());
        passRepository.save(passEntity);

        // when
        passRepository.deleteById(passEntity.getPassSeq());

        // then
        assertTrue(passRepository.findById(passEntity.getPassSeq()).isEmpty());

    }

}