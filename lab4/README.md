## a) Identify a couple of examples on the use of AssertJ expressive methods chaining.

The AssertJ expressive methods chaining can be found almost in every test classes. For example:

EmployeeRepositoryTest:
```java
assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
```

EmployeeRestControllerIT:
```java
assertThat(found).extracting(Employee::getName).containsOnly("bob");
```

EmployeeRestControllerTemplateIT:
```java
assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");
```

EmployeeeService_UnitTest: 
```java
assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName());
```


## b) Identify an example in which you mock the behavior of the repository (and avoid involving a database).

This can be seen in the EmployeeService_UnitTest, where all expectations used in the test are grouped together in the setUp method:
```java
Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
```


## c) What is the difference between standard @Mock and @MockBean?

The @Mock is used for unit testing the business logic (only using JUnit and Mockito). 
The @MockBean is used when we need to write a test that is backed by a Spring Test Context and we want to add or replace a bean with a mocked version of it.


## d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?

The file “application-integrationtest.properties” is meant to start a connection to a database which will be used in the integration tests. For that, it stores all the configurations needed to start this connection.
