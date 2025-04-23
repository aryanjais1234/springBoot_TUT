package com.springBoot.cruddemo;

import com.springBoot.cruddemo.dao.StudentDAo;
import com.springBoot.cruddemo.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(StudentDAo studentDAo){
		return runner -> {
//			createStudent(studentDAo);
//			createMultiplpeStudent(studentDAo);

//			readStudent(studentDAo);

			queryForStudents(studentDAo);

//			queryForStudentsByLastName(studentDAo);

//			updateStudent(studentDAo);

//			deleteStudent(studentDAo);

//			deleteAllStudent(studentDAo);
		};
	}

	private void deleteAllStudent(StudentDAo studentDAo){
		System.out.println("Deleting all students");
		int numRowsDeleted = studentDAo.deleteAll();
		System.out.println("Deleted row count: " + numRowsDeleted);
	}

	private void deleteStudent(StudentDAo studentDAo){

		// delete the student
		int studentId = 3;

		System.out.println("Deleting student id: " + studentId);

		studentDAo.delete(studentId);
	}

	private  void updateStudent(StudentDAo studentDAo){

		// retrive student based on the id: primary key
		int studentId = 1;
		System.out.println("Getting student with id: " + studentId);
		Student myStudent = studentDAo.findById(studentId);
		// change first name to "Scooby"
		System.out.println("Updating student ...");
		myStudent.setFirstName("Scooby");
		// update the student
		studentDAo.update(myStudent);
		// display the student
		System.out.println("Updated student: " + myStudent);
	}

	private void queryForStudentsByLastName(StudentDAo studentDAo){

		// get a list of students
		List<Student> theStudents = studentDAo.findByLastName("Duck");

		// display list of students
		for (Student tempStudent: theStudents){
			System.out.println(tempStudent);
		}
	}

	private void queryForStudents(StudentDAo studentDAo){
		// get a list of students
		List<Student> theStudents = studentDAo.findAll();

		// display list of students


		for(Student tempStudent : theStudents){
			System.out.println(tempStudent);
		}

	}

	private void readStudent(StudentDAo studentDAo) {

		// create a student object
		System.out.println("Creating new student object...");
		Student tempStudent = new Student("Daffy","Duck","daffy@spring.com");

		// save the student
		System.out.println("Saving the student...");
		studentDAo.save(tempStudent);

		// display id of the student
		int theId = tempStudent.getId();
		System.out.println("Saved student. Generated id: " + theId);

		// retrive student based on the id: primary key
		System.out.println("Rerrieving student with id: " + theId);
		Student myStudent = studentDAo.findById(theId);

		// display student
		System.out.println("Found the student: "+ myStudent);
	}

	private void createMultiplpeStudent(StudentDAo studentDAo) {

		// create multiple students
		System.out.println("Creating 3 student object...");
		Student tempStudent1 = new Student("John","Doe","john@sprig.com");
		Student tempStudent2 = new Student("Mary","Doe","mary@sprig.com");
		Student tempStudent3 = new Student("lily","Doe","lily@sprig.com");

		// save the student objects

		System.out.println("Saving the student...");
		studentDAo.save(tempStudent1);
		studentDAo.save(tempStudent2);
		studentDAo.save(tempStudent3);

	}

	private void createStudent(StudentDAo studentDAo) {
		// create the student object
		System.out.println("Creating new student object...");
		Student tempStudent = new Student("Paul","Doe","paul@sprig.com");
		// save the student object
		System.out.println("Saving the student...");
		studentDAo.save(tempStudent);
		//display id of the saved studente
		System.out.println("Saved student. Generated id: " + tempStudent.getId());
	}
}
