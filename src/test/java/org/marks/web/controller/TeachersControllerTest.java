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
import org.marks.bean.Teachers;
import org.marks.bean.Roles;
import org.marks.test.TeachersFactoryForTest;
import org.marks.test.RolesFactoryForTest;

//--- Services 
import org.marks.business.service.TeachersService;
import org.marks.business.service.RolesService;

//--- List Items 
import org.marks.web.listitem.RolesListItem;

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
public class TeachersControllerTest {
	
	@InjectMocks
	private TeachersController teachersController;
	@Mock
	private TeachersService teachersService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private RolesService rolesService; // Injected by Spring

	private TeachersFactoryForTest teachersFactoryForTest = new TeachersFactoryForTest();
	private RolesFactoryForTest rolesFactoryForTest = new RolesFactoryForTest();

	List<Roles> roless = new ArrayList<Roles>();

	private void givenPopulateModel() {
		Roles roles1 = rolesFactoryForTest.newRoles();
		Roles roles2 = rolesFactoryForTest.newRoles();
		List<Roles> roless = new ArrayList<Roles>();
		roless.add(roles1);
		roless.add(roles2);
		when(rolesService.findAll()).thenReturn(roless);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Teachers> list = new ArrayList<Teachers>();
		when(teachersService.findAll()).thenReturn(list);
		
		// When
		String viewName = teachersController.list(model);
		
		// Then
		assertEquals("teachers/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = teachersController.formForCreate(model);
		
		// Then
		assertEquals("teachers/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Teachers)modelMap.get("teachers")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/teachers/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<RolesListItem> rolesListItems = (List<RolesListItem>) modelMap.get("listOfRolesItems");
		assertEquals(2, rolesListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Teachers teachers = teachersFactoryForTest.newTeachers();
		Integer id = teachers.getId();
		when(teachersService.findById(id)).thenReturn(teachers);
		
		// When
		String viewName = teachersController.formForUpdate(model, id);
		
		// Then
		assertEquals("teachers/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(teachers, (Teachers) modelMap.get("teachers"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/teachers/update", modelMap.get("saveAction"));
		
		List<RolesListItem> rolesListItems = (List<RolesListItem>) modelMap.get("listOfRolesItems");
		assertEquals(2, rolesListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Teachers teachers = teachersFactoryForTest.newTeachers();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Teachers teachersCreated = new Teachers();
		when(teachersService.create(teachers)).thenReturn(teachersCreated); 
		
		// When
		String viewName = teachersController.create(teachers, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/teachers/form/"+teachers.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(teachersCreated, (Teachers) modelMap.get("teachers"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Teachers teachers = teachersFactoryForTest.newTeachers();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = teachersController.create(teachers, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("teachers/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(teachers, (Teachers) modelMap.get("teachers"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/teachers/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<RolesListItem> rolesListItems = (List<RolesListItem>) modelMap.get("listOfRolesItems");
		assertEquals(2, rolesListItems.size());
		
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

		Teachers teachers = teachersFactoryForTest.newTeachers();
		
		Exception exception = new RuntimeException("test exception");
		when(teachersService.create(teachers)).thenThrow(exception);
		
		// When
		String viewName = teachersController.create(teachers, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("teachers/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(teachers, (Teachers) modelMap.get("teachers"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/teachers/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "teachers.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<RolesListItem> rolesListItems = (List<RolesListItem>) modelMap.get("listOfRolesItems");
		assertEquals(2, rolesListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Teachers teachers = teachersFactoryForTest.newTeachers();
		Integer id = teachers.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Teachers teachersSaved = new Teachers();
		teachersSaved.setId(id);
		when(teachersService.update(teachers)).thenReturn(teachersSaved); 
		
		// When
		String viewName = teachersController.update(teachers, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/teachers/form/"+teachers.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(teachersSaved, (Teachers) modelMap.get("teachers"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Teachers teachers = teachersFactoryForTest.newTeachers();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = teachersController.update(teachers, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("teachers/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(teachers, (Teachers) modelMap.get("teachers"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/teachers/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<RolesListItem> rolesListItems = (List<RolesListItem>) modelMap.get("listOfRolesItems");
		assertEquals(2, rolesListItems.size());
		
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

		Teachers teachers = teachersFactoryForTest.newTeachers();
		
		Exception exception = new RuntimeException("test exception");
		when(teachersService.update(teachers)).thenThrow(exception);
		
		// When
		String viewName = teachersController.update(teachers, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("teachers/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(teachers, (Teachers) modelMap.get("teachers"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/teachers/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "teachers.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<RolesListItem> rolesListItems = (List<RolesListItem>) modelMap.get("listOfRolesItems");
		assertEquals(2, rolesListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Teachers teachers = teachersFactoryForTest.newTeachers();
		Integer id = teachers.getId();
		
		// When
		String viewName = teachersController.delete(redirectAttributes, id);
		
		// Then
		verify(teachersService).delete(id);
		assertEquals("redirect:/teachers", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Teachers teachers = teachersFactoryForTest.newTeachers();
		Integer id = teachers.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(teachersService).delete(id);
		
		// When
		String viewName = teachersController.delete(redirectAttributes, id);
		
		// Then
		verify(teachersService).delete(id);
		assertEquals("redirect:/teachers", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "teachers.error.delete", exception);
	}
	
	
}
