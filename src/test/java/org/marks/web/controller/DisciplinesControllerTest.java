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
import org.marks.bean.Disciplines;
import org.marks.bean.Teachers;
import org.marks.test.DisciplinesFactoryForTest;
import org.marks.test.TeachersFactoryForTest;

//--- Services 
import org.marks.business.service.DisciplinesService;
import org.marks.business.service.TeachersService;

//--- List Items 
import org.marks.web.listitem.TeachersListItem;

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
public class DisciplinesControllerTest {
	
	@InjectMocks
	private DisciplinesController disciplinesController;
	@Mock
	private DisciplinesService disciplinesService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private TeachersService teachersService; // Injected by Spring

	private DisciplinesFactoryForTest disciplinesFactoryForTest = new DisciplinesFactoryForTest();
	private TeachersFactoryForTest teachersFactoryForTest = new TeachersFactoryForTest();

	List<Teachers> teacherss = new ArrayList<Teachers>();

	private void givenPopulateModel() {
		Teachers teachers1 = teachersFactoryForTest.newTeachers();
		Teachers teachers2 = teachersFactoryForTest.newTeachers();
		List<Teachers> teacherss = new ArrayList<Teachers>();
		teacherss.add(teachers1);
		teacherss.add(teachers2);
		when(teachersService.findAll()).thenReturn(teacherss);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Disciplines> list = new ArrayList<Disciplines>();
		when(disciplinesService.findAll()).thenReturn(list);
		
		// When
		String viewName = disciplinesController.list(model);
		
		// Then
		assertEquals("disciplines/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = disciplinesController.formForCreate(model);
		
		// Then
		assertEquals("disciplines/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Disciplines)modelMap.get("disciplines")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/disciplines/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<TeachersListItem> teachersListItems = (List<TeachersListItem>) modelMap.get("listOfTeachersItems");
		assertEquals(2, teachersListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Disciplines disciplines = disciplinesFactoryForTest.newDisciplines();
		Integer id = disciplines.getId();
		when(disciplinesService.findById(id)).thenReturn(disciplines);
		
		// When
		String viewName = disciplinesController.formForUpdate(model, id);
		
		// Then
		assertEquals("disciplines/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplines, (Disciplines) modelMap.get("disciplines"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/disciplines/update", modelMap.get("saveAction"));
		
		List<TeachersListItem> teachersListItems = (List<TeachersListItem>) modelMap.get("listOfTeachersItems");
		assertEquals(2, teachersListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Disciplines disciplines = disciplinesFactoryForTest.newDisciplines();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Disciplines disciplinesCreated = new Disciplines();
		when(disciplinesService.create(disciplines)).thenReturn(disciplinesCreated); 
		
		// When
		String viewName = disciplinesController.create(disciplines, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/disciplines/form/"+disciplines.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplinesCreated, (Disciplines) modelMap.get("disciplines"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Disciplines disciplines = disciplinesFactoryForTest.newDisciplines();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = disciplinesController.create(disciplines, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("disciplines/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplines, (Disciplines) modelMap.get("disciplines"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/disciplines/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<TeachersListItem> teachersListItems = (List<TeachersListItem>) modelMap.get("listOfTeachersItems");
		assertEquals(2, teachersListItems.size());
		
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

		Disciplines disciplines = disciplinesFactoryForTest.newDisciplines();
		
		Exception exception = new RuntimeException("test exception");
		when(disciplinesService.create(disciplines)).thenThrow(exception);
		
		// When
		String viewName = disciplinesController.create(disciplines, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("disciplines/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplines, (Disciplines) modelMap.get("disciplines"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/disciplines/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "disciplines.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<TeachersListItem> teachersListItems = (List<TeachersListItem>) modelMap.get("listOfTeachersItems");
		assertEquals(2, teachersListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Disciplines disciplines = disciplinesFactoryForTest.newDisciplines();
		Integer id = disciplines.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Disciplines disciplinesSaved = new Disciplines();
		disciplinesSaved.setId(id);
		when(disciplinesService.update(disciplines)).thenReturn(disciplinesSaved); 
		
		// When
		String viewName = disciplinesController.update(disciplines, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/disciplines/form/"+disciplines.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplinesSaved, (Disciplines) modelMap.get("disciplines"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Disciplines disciplines = disciplinesFactoryForTest.newDisciplines();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = disciplinesController.update(disciplines, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("disciplines/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplines, (Disciplines) modelMap.get("disciplines"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/disciplines/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<TeachersListItem> teachersListItems = (List<TeachersListItem>) modelMap.get("listOfTeachersItems");
		assertEquals(2, teachersListItems.size());
		
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

		Disciplines disciplines = disciplinesFactoryForTest.newDisciplines();
		
		Exception exception = new RuntimeException("test exception");
		when(disciplinesService.update(disciplines)).thenThrow(exception);
		
		// When
		String viewName = disciplinesController.update(disciplines, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("disciplines/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplines, (Disciplines) modelMap.get("disciplines"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/disciplines/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "disciplines.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<TeachersListItem> teachersListItems = (List<TeachersListItem>) modelMap.get("listOfTeachersItems");
		assertEquals(2, teachersListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Disciplines disciplines = disciplinesFactoryForTest.newDisciplines();
		Integer id = disciplines.getId();
		
		// When
		String viewName = disciplinesController.delete(redirectAttributes, id);
		
		// Then
		verify(disciplinesService).delete(id);
		assertEquals("redirect:/disciplines", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Disciplines disciplines = disciplinesFactoryForTest.newDisciplines();
		Integer id = disciplines.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(disciplinesService).delete(id);
		
		// When
		String viewName = disciplinesController.delete(redirectAttributes, id);
		
		// Then
		verify(disciplinesService).delete(id);
		assertEquals("redirect:/disciplines", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "disciplines.error.delete", exception);
	}
	
	
}
