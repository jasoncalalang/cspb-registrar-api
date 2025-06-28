package ph.edu.cspb.registrar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ph.edu.cspb.registrar.api.StudentController;
import ph.edu.cspb.registrar.dto.StudentDto;
import ph.edu.cspb.registrar.mapper.*;
import ph.edu.cspb.registrar.model.Student;
import ph.edu.cspb.registrar.repo.*;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    private StudentRepository studentRepository;
    private AddressRepository addressRepository;
    private RequirementTypeRepository requirementTypeRepository;
    private RequirementRepository requirementRepository;
    private StudentMapper studentMapper;
    private AddressMapper addressMapper;
    private RequirementTypeMapper requirementTypeMapper;
    private RequirementMapper requirementMapper;
    private StudentController controller;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
        addressRepository = mock(AddressRepository.class);
        requirementTypeRepository = mock(RequirementTypeRepository.class);
        requirementRepository = mock(RequirementRepository.class);
        studentMapper = Mappers.getMapper(StudentMapper.class);
        addressMapper = Mappers.getMapper(AddressMapper.class);
        requirementTypeMapper = Mappers.getMapper(RequirementTypeMapper.class);
        requirementMapper = Mappers.getMapper(RequirementMapper.class);
        controller = new StudentController(
                studentRepository,
                addressRepository,
                requirementTypeRepository,
                requirementRepository,
                studentMapper,
                addressMapper,
                requirementTypeMapper,
                requirementMapper);
    }

    @Test
    void createStudentSavesAndReturnsDto() {
        when(studentRepository.save(any(Student.class))).thenAnswer(inv -> {
            Student s = inv.getArgument(0);
            s.setId(1L);
            return s;
        });

        StudentDto dto = new StudentDto(null, "123456789012", "Doe", "John", null, null,
                LocalDate.of(2000,1,1), null, null, "Filipino", null,
                (short)0, null, null, null, null);

        ResponseEntity<StudentDto> response = controller.createStudent(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().id()).isEqualTo(1L);
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void deleteExistingStudentReturnsNoContent() {
        when(studentRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> response = controller.deleteStudent(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(studentRepository).deleteById(1L);
    }

    @Test
    void deleteMissingStudentReturnsNotFound() {
        when(studentRepository.existsById(1L)).thenReturn(false);

        ResponseEntity<Void> response = controller.deleteStudent(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(studentRepository, never()).deleteById(anyLong());
    }
}
