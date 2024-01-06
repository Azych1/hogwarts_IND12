package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = Logger.getLogger(StudentServiceImpl.class.toString());

    private final StudentRepository repository;

    private boolean marker = false;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student create(Student student) {
        logger.info("Was invoked method to create student and return it");

        return repository.save(student);
    }

    @Override
    public Student read(long id) {
        logger.info("Was invoked method to find student and return it");

        return repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Студент с id " + id + " не найден в хранилище"));
    }

    @Override
    public Student update(Student student) {
        logger.info("Was invoked method to update student and return saved");

        read(student.getId());

        return repository.save(student);
    }

    @Override
    public Student delete(long id) {
        logger.info("Was invoked method to delete student and return deleted");

        Student student = read(id);

        repository.delete(student);

        return student;
    }

    @Override
    public Collection<Student> readByAge(int age) {
        logger.info("Was invoked method to find student by {age}");
        return repository.findAllByAge(age);
    }

    @Override
    public Collection<Student> readByAgeBetween(int minAge, int maxAge) {
        logger.info("Was invoked method to find students between {minAge} and {maxAge} and return as Collection");
        return repository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty readStudentFaculty(long studentId) {
        logger.info("Was invoked method to find faculty by studentId");
        return read(studentId).getFaculty();
    }

    @Override
    public Collection<Student> readByFacultyId(long facultyId) {
        logger.info("Was invoked method to find all faculties by facultyId and return as Collection");
        return repository.findAllByFaculty_id(facultyId);
    }

    @Override
    public Integer getCountOfAllStudents() {
        logger.info("Was invoked method to get count of all students in repository");
        return repository.getCountOfAllStudents();
    }

    @Override
    public Double getAverageAgeOfStudents() {
        logger.info("Was invoked method to get average of all students in repository");
        return repository.getAverageAgeOfStudents();
    }

    @Override
    public Collection<Student> getLastFiveStudents() {
        logger.info("Was invoked method to get last five students in repository, filtered by name before");
        return repository.getLastFiveStudents();
    }

    public Collection<String> getFilteredByName() {
        logger.info("Was invoked method to find all students filtered by name to String Collection");
        return repository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Double getAllStudentsAvgAge() {
        logger.info("Was invoked method to find average of ages of all students in repository");
        return repository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

    public void getStudentNames() {
        Thread thread1 = new Thread(() -> {
            printName(3L);
            printName(4L);
        });
        thread1.setName("Thread 1");
        Thread thread2 = new Thread(() -> {
            printName(5L);
            printName(6L);
        });
        thread2.setName("Thread 2");
        thread1.start();
        thread2.start();

        printName(1L);
        printName(2L);
    }

    public void getStudentNamesSync() {
        Thread thread1 = new Thread(() -> {
            printNameSync(3L);
            printNameSync(4L);
        });
        Thread thread2 = new Thread(() -> {
            printNameSync(5L);
            printNameSync(6L);
        });
        printNameSync(1L);
        printNameSync(2L);
        thread1.start();
        thread2.start();


    }




    private void printName(Long id) {
        //String color = getThreadColor();
        String studentName = repository.getById(id).getName();
        System.out.println(studentName); //System.out.println(color + studentName);
    }

    private synchronized void printNameSync(Long id) {
        String studentName = repository.getById(id).getName();
        System.out.println(studentName);
    }

    public void getStudentsNameWait() {
        List<Student> allStudents = repository.findAll();
        System.out.println(allStudents.get(0).getName());
        System.out.println(allStudents.get(1).getName());

        Thread thread1 = new Thread(() -> {
            printNameSyncWait(2, 3, allStudents);
        });

        thread1.setName("Thread 1");
        Thread thread2 = new Thread(() -> {
            printNameSyncWait2(4, 5, allStudents);
        });

        thread1.setName("Thread 2");

        thread2.start();
        thread1.start();



    }

    private synchronized void printNameSyncWait(Integer id1, Integer id2, List<Student> students) {
        while (marker) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println(students.get(id1).getName());
            System.out.println(students.get(id2).getName());
            marker = true;
            notify();
        }
    }

    private synchronized void printNameSyncWait2(Integer id1, Integer id2, List<Student> students) {
        while (!marker) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println(students.get(id1).getName());
            System.out.println(students.get(id2).getName());
            //marker = true;
            //notify();
        }
    }


//    private String getThreadColor() {
//        String color;
//        if (Thread.currentThread().getName().equals("Thread 1")) {
//            color = ThreadColor.ANSI_PURPLE;
//        } else if (Thread.currentThread().getName().equals("Thread 2")) {
//            color = ThreadColor.ANSI_BLUE;
//        } else {
//            color = ThreadColor.ANSI_GREEN;
//        }
//        return color;
//    }


}
