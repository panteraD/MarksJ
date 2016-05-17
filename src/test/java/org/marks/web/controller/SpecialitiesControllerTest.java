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
import org.marks.bean.Specialities;
import org.marks.test.SpecialitiesFactoryForTest;

//--- Services 
import org.marks.business.service.SpecialitiesService;


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
public class SpecialitiesControllerTest {
	
	@InjectMocks
	private SpecialitiesController specialitiesController;
	@Mock
	private SpecialitiesService specialitiesService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private SpecialitiesFactoryForTest specialitiesFactoryForTest = new SpecialitiesFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Specialities> list = new ArrayList<Specialities>();
		when(specialitiesService.findAll()).thenReturn(list);
		
		// When
		String viewName = specialitiesController.list(model);
		
		// Then
		assertEquals("specialities/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = specialitiesController.formForCreate(model);
		
		// Then
		assertEquals("specialities/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Specialities)modelMap.get("specialities")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/specialities/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Specialities specialities = specialitiesFactoryForTest.newSpecialities();
		Integer id = specialities.getId();
		when(specialitiesService.findById(id)).thenReturn(specialities);
		
		// When
		String viewName = specialitiesController.formForUpdate(model, id);
		
		// Then
		assertEquals("specialities/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(specialities, (Specialities) modelMap.get("specialities"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/specialities/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Specialities specialities = specialitiesFactoryForTest.newSpecialities();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Specialities specialitiesCreated = new Specialities();
		when(specialitiesService.create(specialities)).thenReturn(specialitiesCreated); 
		
		// When
		String viewName = specialitiesController.create(specialities, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/specialities/form/"+specialities.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(specialitiesCreated, (Specialities) modelMap.get("specialities"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Specialities specialities = specialitiesFactoryForTest.newSpecialities();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = specialitiesController.create(specialities, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("specialities/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(specialities, (Specialities) modelMap.get("specialities"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/specialities/create", modelMap.get("saveAction"));
		
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

		Specialities specialities = specialitiesFactoryForTest.newSpecialities();
		
		Exception exception = new RuntimeException("test exception");
		when(specialitiesService.create(specialities)).thenThrow(exception);
		
		// When
		String viewName = specialitiesController.create(specialities, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("specialities/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(specialities, (Specialities) modelMap.get("specialities"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/specialities/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "specialities.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Specialities specialities = specialitiesFactoryForTest.newSpecialities();
		Integer id = specialities.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Specialities specialitiesSaved = new Specialities();
		specialitiesSaved.setId(id);
		when(specialitiesService.update(specialities)).thenReturn(specialitiesSaved); 
		
		// When
		String viewName = specialitiesController.update(specialities, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/specialities/form/"+specialities.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(specialitiesSaved, (Specialities) modelMap.get("specialities"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Specialities specialities = specialitiesFactoryForTest.newSpecialities();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = specialitiesController.update(specialities, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("specialities/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(specialities, (Specialities) modelMap.get("specialities"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/specialities/update", modelMap.get("saveAction"));
		
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

		Specialities specialities = specialitiesFactoryForTest.newSpecialities();
		
		Exception exception = new RuntimeException("test exception");
		when(specialitiesService.update(specialities)).thenThrow(exception);
		
		// When
		String viewName = specialitiesController.update(specialities, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("specialities/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(specialities, (Specialities) modelMap.get("specialities"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/specialities/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "specialities.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Specialities specialities = specialitiesFactoryForTest.newSpecialities();
		Integer id = specialities.getId();
		
		// When
		String viewName = specialitiesController.delete(redirectAttributes, id);
		
		// Then
		verify(specialitiesService).delete(id);
		assertEquals("redirect:/specialities", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Specialities specialities = specialitiesFactoryForTest.newSpecialities();
		Integer id = specialities.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(specialitiesService).delete(id);
		
		// When
		String viewName = specialitiesController.delete(redirectAttributes, id);
		
		// Then
		verify(specialitiesService).delete(id);
		assertEquals("redirect:/specialities", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "specialities.error.delete", exception);
	}
	
	
}
