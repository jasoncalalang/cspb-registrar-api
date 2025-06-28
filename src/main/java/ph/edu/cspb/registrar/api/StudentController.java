package ph.edu.cspb.registrar.api;

import ph.edu.cspb.registrar.dto.*;
import ph.edu.cspb.registrar.mapper.*;
import ph.edu.cspb.registrar.model.*;
import ph.edu.cspb.registrar.repo.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentRepository studentRepository;
    private final AddressRepository addressRepository;
    private final RequirementTypeRepository requirementTypeRepository;
    private final RequirementRepository requirementRepository;
    private final StudentMapper studentMapper;
    private final AddressMapper addressMapper;
    private final RequirementTypeMapper requirementTypeMapper;
    private final RequirementMapper requirementMapper;

    public StudentController(StudentRepository studentRepository,
                             AddressRepository addressRepository,
                             RequirementTypeRepository requirementTypeRepository,
                             RequirementRepository requirementRepository,
                             StudentMapper studentMapper,
                             AddressMapper addressMapper,
                             RequirementTypeMapper requirementTypeMapper,
                             RequirementMapper requirementMapper) {
        this.studentRepository = studentRepository;
        this.addressRepository = addressRepository;
        this.requirementTypeRepository = requirementTypeRepository;
        this.requirementRepository = requirementRepository;
        this.studentMapper = studentMapper;
        this.addressMapper = addressMapper;
        this.requirementTypeMapper = requirementTypeMapper;
        this.requirementMapper = requirementMapper;
    }

    @GetMapping("/students")
    public Page<StudentDto> listStudents(@RequestParam(required = false) String lastName,
                                         Pageable pageable) {
        Pageable noSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        Page<Student> page = (lastName == null)
                ? studentRepository.findAll(noSort)
                : studentRepository.findByLastNameContainingIgnoreCase(lastName, noSort);
        return page.map(studentMapper::toDto);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(studentMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/students")
    public ResponseEntity<StudentDto> createStudent(@Valid @RequestBody StudentDto dto) {
        Student saved = studentRepository.save(studentMapper.toEntity(dto));
        return ResponseEntity.ok(studentMapper.toDto(saved));
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id,
                                                    @Valid @RequestBody StudentDto dto) {
        return studentRepository.findById(id)
                .map(existing -> {
                    Student entity = studentMapper.toEntity(dto);
                    entity.setId(id);
                    entity.setAddress(existing.getAddress());
                    entity.setRequirements(existing.getRequirements());
                    Student saved = studentRepository.save(entity);
                    return ResponseEntity.ok(studentMapper.toDto(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/students/{id}")
    public ResponseEntity<StudentDto> patchStudent(@PathVariable Long id,
                                                   @RequestBody StudentDto dto) {
        return studentRepository.findById(id)
                .map(existing -> {
                    Student entity = studentMapper.toEntity(dto);
                    if (dto.lrn() != null) existing.setLrn(dto.lrn());
                    if (dto.lastName() != null) existing.setLastName(dto.lastName());
                    if (dto.firstName() != null) existing.setFirstName(dto.firstName());
                    if (dto.middleName() != null) existing.setMiddleName(dto.middleName());
                    if (dto.extensionName() != null) existing.setExtensionName(dto.extensionName());
                    if (dto.birthDate() != null) existing.setBirthDate(dto.birthDate());
                    if (dto.birthPlace() != null) existing.setBirthPlace(dto.birthPlace());
                    if (dto.gender() != null) existing.setGender(dto.gender());
                    if (dto.nationality() != null) existing.setNationality(dto.nationality());
                    if (dto.religion() != null) existing.setReligion(dto.religion());
                    if (dto.numSiblings() != null) existing.setNumSiblings(dto.numSiblings());
                    if (dto.siblingNames() != null) existing.setSiblingNames(dto.siblingNames());
                    if (dto.imgPath() != null) existing.setImgPath(dto.imgPath());
                    Student saved = studentRepository.save(existing);
                    return ResponseEntity.ok(studentMapper.toDto(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/students/{id}/address")
    public ResponseEntity<AddressDto> getAddress(@PathVariable Long id) {
        return addressRepository.findById(id)
                .map(addressMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/students/{id}/address")
    public ResponseEntity<AddressDto> putAddress(@PathVariable Long id,
                                                 @Valid @RequestBody AddressDto dto) {
        return studentRepository.findById(id)
                .map(student -> {
                    Address address = addressRepository.findById(id).orElse(new Address());
                    Address entity = addressMapper.toEntity(dto);
                    entity.setStudent(student);
                    entity.setStudentId(id);
                    entity.setCreatedAt(address.getCreatedAt());
                    Address saved = addressRepository.save(entity);
                    return ResponseEntity.ok(addressMapper.toDto(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/students/{id}/address")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/requirement-types")
    public List<RequirementTypeDto> getRequirementTypes() {
        return requirementTypeRepository.findAll().stream()
                .map(requirementTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/students/{id}/requirements")
    public ResponseEntity<List<RequirementDto>> getRequirements(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(student -> requirementRepository.findAll().stream()
                        .filter(r -> r.getStudent().getId().equals(id))
                        .map(requirementMapper::toDto)
                        .collect(Collectors.toList()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/students/{id}/requirements/{typeId}")
    public ResponseEntity<Void> putRequirement(@PathVariable Long id,
                                               @PathVariable Short typeId,
                                               @RequestBody RequirementDto dto) {
        Optional<Student> studentOpt = studentRepository.findById(id);
        Optional<RequirementType> typeOpt = requirementTypeRepository.findById(typeId);
        if (studentOpt.isEmpty() || typeOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        RequirementId rid = new RequirementId(id, typeId);
        Requirement req = requirementRepository.findById(rid).orElse(new Requirement());
        req.setId(rid);
        req.setStudent(studentOpt.get());
        req.setRequirementType(typeOpt.get());
        req.setSubmitted(dto.submitted());
        req.setSubmittedDate(dto.submittedDate());
        requirementRepository.save(req);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/students/{id}/requirements")
    public ResponseEntity<Void> patchRequirements(@PathVariable Long id,
                                                  @RequestBody List<RequirementDto> list) {
        Optional<Student> studentOpt = studentRepository.findById(id);
        if (studentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        for (RequirementDto dto : list) {
            RequirementId rid = new RequirementId(id, dto.requirementTypeId());
            Requirement req = requirementRepository.findById(rid).orElse(new Requirement());
            req.setId(rid);
            req.setStudent(studentOpt.get());
            requirementTypeRepository.findById(dto.requirementTypeId())
                    .ifPresent(req::setRequirementType);
            req.setSubmitted(dto.submitted());
            req.setSubmittedDate(dto.submittedDate());
            requirementRepository.save(req);
        }
        return ResponseEntity.noContent().build();
    }
}
