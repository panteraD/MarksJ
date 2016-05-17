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
import org.marks.bean.Groups;
import org.marks.bean.Specialities;
import org.marks.test.GroupsFactoryForTest;
import org.marks.test.SpecialitiesFactoryForTest;

//--- Services 
import org.marks.business.service.GroupsService;
import org.marks.business.service.SpecialitiesService;

//--- List Items 
import org.marks.web.listitem.SpecialitiesListItem;

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
public class GroupsControllerTest {
	
	@InjectMocks
	private GroupsController groupsController;
	@Mock
	private GroupsService groupsService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private SpecialitiesService specialitiesService; // Injected by Spring

	private GroupsFactoryForTest groupsFactoryForTest = new GroupsFactoryForTest();
	private SpecialitiesFactoryForTest specialitiesFactoryForTest = new SpecialitiesFactoryForTest();

	List<Specialities> specialitiess = new ArrayList<Specialities>();

	private void givenPopulateModel() {
		Specialities specialities1 = specialitiesFactoryForTest.newSpecialities();
		Specialities specialities2 = specialitiesFactoryForTest.newSpecialities();
		List<Specialities> specialitiess = new ArrayList<Specialities>();
		specialitiess.add(specialities1);
		specialitiess.add(specialities2);
		when(specialitiesService.findAll()).thenReturn(specialitiess);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Groups> list = new ArrayList<Groups>();
		when(groupsService.findAll()).thenReturn(list);
		
		// When
		String viewName = groupsController.list(model);
		
		// Then
		assertEquals("groups/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = groupsController.formForCreate(model);
		
		// Then
		assertEquals("groups/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Groups)modelMap.get("groups")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/groups/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<SpecialitiesListItem> specialitiesListItems = (List<SpecialitiesListItem>) modelMap.get("listOfSpecialitiesItems");
		assertEquals(2, specialitiesListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Groups groups = groupsFactoryForTest.newGroups();
		Integer id = groups.getId();
		when(groupsService.findById(id)).thenReturn(groups);
		
		// When
		String viewName = groupsController.formForUpdate(model, id);
		
		// Then
		assertEquals("groups/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(groups, (Groups) modelMap.get("groups"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/groups/update", modelMap.get("saveAction"));
		
		List<SpecialitiesListItem> specialitiesListItems = (List<SpecialitiesListItem>) modelMap.get("listOfSpecialitiesItems");
		assertEquals(2, specialitiesListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Groups groups = groupsFactoryForTest.newGroups();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Groups groupsCreated = new Groups();
		when(groupsService.create(groups)).thenReturn(groupsCreated); 
		
		// When
		String viewName = groupsController.create(groups, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/groups/form/"+groups.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(groupsCreated, (Groups) modelMap.get("groups"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Groups groups = groupsFactoryForTest.newGroups();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = groupsController.create(groups, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("groups/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(groups, (Groups) modelMap.get("groups"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/groups/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<SpecialitiesListItem> specialitiesListItems = (List<SpecialitiesListItem>) modelMap.get("listOfSpecialitiesItems");
		assertEquals(2, specialitiesListItems.size());
		
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

		Groups groups = groupsFactoryForTest.newGroups();
		
		Exception exception = new RuntimeException("test exception");
		when(groupsService.create(groups)).thenThrow(exception);
		
		// When
		String viewName = groupsController.create(groups, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("groups/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(groups, (Groups) modelMap.get("groups"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/groups/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "groups.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<SpecialitiesListItem> specialitiesListItems = (List<SpecialitiesListItem>) modelMap.get("listOfSpecialitiesItems");
		assertEquals(2, specialitiesListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Groups groups = groupsFactoryForTest.newGroups();
		Integer id = groups.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Groups groupsSaved = new Groups();
		groupsSaved.setId(id);
		when(groupsService.update(groups)).thenReturn(groupsSaved); 
		
		// When
		String viewName = groupsController.update(groups, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/groups/form/"+groups.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(groupsSaved, (Groups) modelMap.get("groups"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Groups groups = groupsFactoryForTest.newGroups();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = groupsController.update(groups, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("groups/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(groups, (Groups) modelMap.get("groups"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/groups/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<SpecialitiesListItem> specialitiesListItems = (List<SpecialitiesListItem>) modelMap.get("listOfSpecialitiesItems");
		assertEquals(2, specialitiesListItems.size());
		
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

		Groups groups = groupsFactoryForTest.newGroups();
		
		Exception exception = new RuntimeException("test exception");
		when(groupsService.update(groups)).thenThrow(exception);
		
		// When
		String viewName = groupsController.update(groups, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("groups/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(groups, (Groups) modelMap.get("groups"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/groups/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "groups.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<SpecialitiesListItem> specialitiesListItems = (List<SpecialitiesListItem>) modelMap.get("listOfSpecialitiesItems");
		assertEquals(2, specialitiesListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Groups groups = groupsFactoryForTest.newGroups();
		Integer id = groups.getId();
		
		// When
		String viewName = groupsController.delete(redirectAttributes, id);
		
		// Then
		verify(groupsService).delete(id);
		assertEquals("redirect:/groups", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Groups groups = groupsFactoryForTest.newGroups();
		Integer id = groups.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(groupsService).delete(id);
		
		// When
		String viewName = groupsController.delete(redirectAttributes, id);
		
		// Then
		verify(groupsService).delete(id);
		assertEquals("redirect:/groups", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "groups.error.delete", exception);
	}
	
	
}
