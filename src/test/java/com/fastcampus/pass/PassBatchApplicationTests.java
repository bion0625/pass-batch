package com.fastcampus.pass;

import com.fastcampus.pass.config.TestBatchConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = TestBatchConfig.class)
class PassBatchApplicationTests {

	@Test
	void contextLoads() {
	}

}
