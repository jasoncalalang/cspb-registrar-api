package ph.edu.cspb.registrar;

import ph.edu.cspb.registrar.dto.StudentDto;
import ph.edu.cspb.registrar.repo.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class StudentControllerIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    StudentRepository studentRepository;

    @Test
    void studentCrudHappyPath() {
        StudentDto dto = new StudentDto(null, "123456789012", "Doe", "John", null, null,
                LocalDate.of(2000,1,1), null, null, "Filipino", null,
                (short)0, null, null, null, null);
        StudentDto created = restTemplate.postForObject("http://localhost:"+port+"/api/students", dto, StudentDto.class);
        assertThat(created.id()).isNotNull();

        StudentDto fetched = restTemplate.getForObject("http://localhost:"+port+"/api/students/"+created.id(), StudentDto.class);
        assertThat(fetched.lrn()).isEqualTo("123456789012");

        restTemplate.delete("http://localhost:"+port+"/api/students/"+created.id());
        assertThat(studentRepository.existsById(created.id())).isFalse();
    }
}
