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
import org.marks.bean.Roles;
import org.marks.test.RolesFactoryForTest;

//--- Services 
import org.marks.business.service.RolesService;


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
public class RolesControllerTest {
	
	@InjectMocks
	private RolesController rolesController;
	@Mock
	private RolesService rolesService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private RolesFactoryForTest rolesFactoryForTest = new RolesFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Roles> list = new ArrayList<Roles>();
		when(rolesService.findAll()).thenReturn(list);
		
		// When
		String viewName = rolesController.list(model);
		
		// Then
		assertEquals("roles/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = rolesController.formForCreate(model);
		
		// Then
		assertEquals("roles/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Roles)modelMap.get("roles")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/roles/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Roles roles = rolesFactoryForTest.newRoles();
		Integer id = roles.getId();
		when(rolesService.findById(id)).thenReturn(roles);
		
		// When
		String viewName = rolesController.formForUpdate(model, id);
		
		// Then
		assertEquals("roles/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(roles, (Roles) modelMap.get("roles"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/roles/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Roles roles = rolesFactoryForTest.newRoles();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Roles rolesCreated = new Roles();
		when(rolesService.create(roles)).thenReturn(rolesCreated); 
		
		// When
		String viewName = rolesController.create(roles, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/roles/form/"+roles.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(rolesCreated, (Roles) modelMap.get("roles"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Roles roles = rolesFactoryForTest.newRoles();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = rolesController.create(roles, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("roles/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(roles, (Roles) modelMap.get("roles"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/roles/create", modelMap.get("saveAction"));
		
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

		Roles roles = rolesFactoryForTest.newRoles();
		
		Exception exception = new RuntimeException("test exception");
		when(rolesService.create(roles)).thenThrow(exception);
		
		// When
		String viewName = rolesController.create(roles, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("roles/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(roles, (Roles) modelMap.get("roles"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/roles/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "roles.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Roles roles = rolesFactoryForTest.newRoles();
		Integer id = roles.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Roles rolesSaved = new Roles();
		rolesSaved.setId(id);
		when(rolesService.update(roles)).thenReturn(rolesSaved); 
		
		// When
		String viewName = rolesController.update(roles, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/roles/form/"+roles.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(rolesSaved, (Roles) modelMap.get("roles"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Roles roles = rolesFactoryForTest.newRoles();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = rolesController.update(roles, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("roles/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(roles, (Roles) modelMap.get("roles"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/roles/update", modelMap.get("saveAction"));
		
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

		Roles roles = rolesFactoryForTest.newRoles();
		
		Exception exception = new RuntimeException("test exception");
		when(rolesService.update(roles)).thenThrow(exception);
		
		// When
		String viewName = rolesController.update(roles, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("roles/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(roles, (Roles) modelMap.get("roles"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/roles/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "roles.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Roles roles = rolesFactoryForTest.newRoles();
		Integer id = roles.getId();
		
		// When
		String viewName = rolesController.delete(redirectAttributes, id);
		
		// Then
		verify(rolesService).delete(id);
		assertEquals("redirect:/roles", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Roles roles = rolesFactoryForTest.newRoles();
		Integer id = roles.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(rolesService).delete(id);
		
		// When
		String viewName = rolesController.delete(redirectAttributes, id);
		
		// Then
		verify(rolesService).delete(id);
		assertEquals("redirect:/roles", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "roles.error.delete", exception);
	}
	
	
}
