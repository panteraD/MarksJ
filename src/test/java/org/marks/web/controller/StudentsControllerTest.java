package org.marks.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//--- Entities
import org.marks.bean.Students;
import org.marks.bean.Groups;
import org.marks.test.StudentsFactoryForTest;
import org.marks.test.GroupsFactoryForTest;

//--- Services 
import org.marks.business.service.StudentsService;
import org.marks.business.service.GroupsService;

//--- List Items 
import org.marks.web.listitem.GroupsListItem;

import org.marks.web.common.Message;
import org.marks.web.common.MessageHelper;
import org.marks.web.common.MessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(MockitoJUnitRunner.class)
public class StudentsControllerTest {
	
	@InjectMocks
	private StudentsController studentsController;
	@Mock
	private StudentsService studentsService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private GroupsService groupsService; // Injected by Spring

	private StudentsFactoryForTest studentsFactoryForTest = new StudentsFactoryForTest();
	private GroupsFactoryForTest groupsFactoryForTest = new GroupsFactoryForTest();

	List<Groups> groupss = new ArrayList<Groups>();

	private void givenPopulateModel() {
		Groups groups1 = groupsFactoryForTest.newGroups();
		Groups groups2 = groupsFactoryForTest.newGroups();
		List<Groups> groupss = new ArrayList<Groups>();
		groupss.add(groups1);
		groupss.add(groups2);
		when(groupsService.findAll()).thenReturn(groupss);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Students> list = new ArrayList<Students>();
		when(studentsService.findAll()).thenReturn(list);
		
		// When
		String viewName = studentsController.list(model);
		
		// Then
		assertEquals("students/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = studentsController.formForCreate(model);
		
		// Then
		assertEquals("students/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Students)modelMap.get("students")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/students/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<GroupsListItem> groupsListItems = (List<GroupsListItem>) modelMap.get("listOfGroupsItems");
		assertEquals(2, groupsListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Students students = studentsFactoryForTest.newStudents();
		Integer id = students.getId();
		when(studentsService.findById(id)).thenReturn(students);
		
		// When
		String viewName = studentsController.formForUpdate(model, id);
		
		// Then
		assertEquals("students/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(students, (Students) modelMap.get("students"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/students/update", modelMap.get("saveAction"));
		
		List<GroupsListItem> groupsListItems = (List<GroupsListItem>) modelMap.get("listOfGroupsItems");
		assertEquals(2, groupsListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Students students = studentsFactoryForTest.newStudents();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Students studentsCreated = new Students();
		when(studentsService.create(students)).thenReturn(studentsCreated); 
		
		// When
		String viewName = studentsController.create(students, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/students/form/"+students.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(studentsCreated, (Students) modelMap.get("students"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Students students = studentsFactoryForTest.newStudents();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = studentsController.create(students, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("students/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(students, (Students) modelMap.get("students"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/students/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<GroupsListItem> groupsListItems = (List<GroupsListItem>) modelMap.get("listOfGroupsItems");
		assertEquals(2, groupsListItems.size());
		
	}

	@Test
	public void createException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		Students students = studentsFactoryForTest.newStudents();
		
		Exception exception = new RuntimeException("test exception");
		when(studentsService.create(students)).thenThrow(exception);
		
		// When
		String viewName = studentsController.create(students, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("students/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(students, (Students) modelMap.get("students"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/students/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "students.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<GroupsListItem> groupsListItems = (List<GroupsListItem>) modelMap.get("listOfGroupsItems");
		assertEquals(2, groupsListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Students students = studentsFactoryForTest.newStudents();
		Integer id = students.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Students studentsSaved = new Students();
		studentsSaved.setId(id);
		when(studentsService.update(students)).thenReturn(studentsSaved); 
		
		// When
		String viewName = studentsController.update(students, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/students/form/"+students.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(studentsSaved, (Students) modelMap.get("students"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Students students = studentsFactoryForTest.newStudents();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = studentsController.update(students, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("students/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(students, (Students) modelMap.get("students"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/students/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<GroupsListItem> groupsListItems = (List<GroupsListItem>) modelMap.get("listOfGroupsItems");
		assertEquals(2, groupsListItems.size());
		
	}

	@Test
	public void updateException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		Students students = studentsFactoryForTest.newStudents();
		
		Exception exception = new RuntimeException("test exception");
		when(studentsService.update(students)).thenThrow(exception);
		
		// When
		String viewName = studentsController.update(students, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("students/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(students, (Students) modelMap.get("students"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/students/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "students.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<GroupsListItem> groupsListItems = (List<GroupsListItem>) modelMap.get("listOfGroupsItems");
		assertEquals(2, groupsListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Students students = studentsFactoryForTest.newStudents();
		Integer id = students.getId();
		
		// When
		String viewName = studentsController.delete(redirectAttributes, id);
		
		// Then
		verify(studentsService).delete(id);
		assertEquals("redirect:/students", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Students students = studentsFactoryForTest.newStudents();
		Integer id = students.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(studentsService).delete(id);
		
		// When
		String viewName = studentsController.delete(redirectAttributes, id);
		
		// Then
		verify(studentsService).delete(id);
		assertEquals("redirect:/students", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "students.error.delete", exception);
	}
	
	
}
