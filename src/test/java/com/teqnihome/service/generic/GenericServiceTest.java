package com.teqnihome.service.generic;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.teqnihome.config.JPAConfig;
import com.teqnihome.dto.StudentDTO;
import com.teqnihome.dto.UserDTO;
import com.teqnihome.model.Student;
import com.teqnihome.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfig.class })
public class GenericServiceTest {
     
    @Autowired
    private GenericServiceV2<User, UserDTO, Long> userService;
    
    @Autowired
    private GenericServiceV2<Student, StudentDTO, Long> studentService;
     
     
    @Test
    public void testFindOneUser() {
    	UserDTO userDto = userService.findOne(1L);
        Assert.assertNotNull(userDto);
        Assert.assertEquals(1, userDto.getId().longValue());
    }
     
    @Test
    public void testFindOneStudent() {
    	StudentDTO studentDTO = studentService.findOne(1L);
        Assert.assertNotNull(studentDTO);
        Assert.assertEquals(1, studentDTO.getId().longValue());
    }
}