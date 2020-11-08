package com.io.mockitoDemoTests;

import java.util.stream.Stream;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.io.mockitoDemo.DemoApplication;
import com.io.mockitoDemo.exception.RecordNotFoundException;
import com.io.mockitoDemo.model.EmployeeEntity;
import com.io.mockitoDemo.repository.EmployeeRepository;
import com.io.mockitoDemo.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

	@Autowired
	private EmployeeService service;
	
	@MockBean
	private EmployeeRepository repository;
	
	@Test
	public void getAllEmployeesTest()
	{
		when(repository.findAll()).thenReturn(Stream
				.of(new EmployeeEntity(1, "Akash", "Sonawane", "xyz@email.com"), new EmployeeEntity(2, "Siddhi", "Kudale", "xyz@email.com")).collect(Collectors.toList()));
		assertEquals(2, service.getAllEmployees().size());
	}
	
	@Test
	public void getEmployeeByIdTest() throws RecordNotFoundException  
	{
		long id=3; 
		EmployeeEntity entity=new EmployeeEntity(2, "J", "D", "x");
		when(repository.findById(id)).thenReturn(Optional
					.of(new EmployeeEntity(3, "J", "D", "x")));
		assertEquals(entity.toString(), service.getEmployeeById(id).toString());
	}
	
	@Test
	public void createOrUpdateEmployee() throws RecordNotFoundException  
	{
		EmployeeEntity entity=new EmployeeEntity(2, "John", "Doe", "xyz@email.com");
		when(repository.save(entity)).thenReturn(entity);
		assertEquals(entity, service.createOrUpdateEmployee(entity));
	}
	
	@Test
	public void deleteEmployeeById() throws RecordNotFoundException  
	{
		long id=2;
		repository.deleteById(id);
		verify(repository, times(1)).deleteById(id);
	}
}
