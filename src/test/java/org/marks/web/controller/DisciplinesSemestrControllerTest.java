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
import org.marks.bean.DisciplinesSemestr;
import org.marks.bean.Disciplines;
import org.marks.bean.Specialities;
import org.marks.test.DisciplinesSemestrFactoryForTest;
import org.marks.test.DisciplinesFactoryForTest;
import org.marks.test.SpecialitiesFactoryForTest;

//--- Services 
import org.marks.business.service.DisciplinesSemestrService;
import org.marks.business.service.DisciplinesService;
import org.marks.business.service.SpecialitiesService;

//--- List Items 
import org.marks.web.listitem.DisciplinesListItem;
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
public class DisciplinesSemestrControllerTest {
	
	@InjectMocks
	private DisciplinesSemestrController disciplinesSemestrController;
	@Mock
	private DisciplinesSemestrService disciplinesSemestrService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private DisciplinesService disciplinesService; // Injected by Spring
	@Mock
	private SpecialitiesService specialitiesService; // Injected by Spring

	private DisciplinesSemestrFactoryForTest disciplinesSemestrFactoryForTest = new DisciplinesSemestrFactoryForTest();
	private DisciplinesFactoryForTest disciplinesFactoryForTest = new DisciplinesFactoryForTest();
	private SpecialitiesFactoryForTest specialitiesFactoryForTest = new SpecialitiesFactoryForTest();

	List<Disciplines> discipliness = new ArrayList<Disciplines>();
	List<Specialities> specialitiess = new ArrayList<Specialities>();

	private void givenPopulateModel() {
		Disciplines disciplines1 = disciplinesFactoryForTest.newDisciplines();
		Disciplines disciplines2 = disciplinesFactoryForTest.newDisciplines();
		List<Disciplines> discipliness = new ArrayList<Disciplines>();
		discipliness.add(disciplines1);
		discipliness.add(disciplines2);
		when(disciplinesService.findAll()).thenReturn(discipliness);

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
		
		List<DisciplinesSemestr> list = new ArrayList<DisciplinesSemestr>();
		when(disciplinesSemestrService.findAll()).thenReturn(list);
		
		// When
		String viewName = disciplinesSemestrController.list(model);
		
		// Then
		assertEquals("disciplinesSemestr/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = disciplinesSemestrController.formForCreate(model);
		
		// Then
		assertEquals("disciplinesSemestr/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((DisciplinesSemestr)modelMap.get("disciplinesSemestr")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/disciplinesSemestr/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DisciplinesListItem> disciplinesListItems = (List<DisciplinesListItem>) modelMap.get("listOfDisciplinesItems");
		assertEquals(2, disciplinesListItems.size());
		
		@SuppressWarnings("unchecked")
		List<SpecialitiesListItem> specialitiesListItems = (List<SpecialitiesListItem>) modelMap.get("listOfSpecialitiesItems");
		assertEquals(2, specialitiesListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		DisciplinesSemestr disciplinesSemestr = disciplinesSemestrFactoryForTest.newDisciplinesSemestr();
		Integer id = disciplinesSemestr.getId();
		when(disciplinesSemestrService.findById(id)).thenReturn(disciplinesSemestr);
		
		// When
		String viewName = disciplinesSemestrController.formForUpdate(model, id);
		
		// Then
		assertEquals("disciplinesSemestr/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplinesSemestr, (DisciplinesSemestr) modelMap.get("disciplinesSemestr"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/disciplinesSemestr/update", modelMap.get("saveAction"));
		
		List<DisciplinesListItem> disciplinesListItems = (List<DisciplinesListItem>) modelMap.get("listOfDisciplinesItems");
		assertEquals(2, disciplinesListItems.size());
		
		List<SpecialitiesListItem> specialitiesListItems = (List<SpecialitiesListItem>) modelMap.get("listOfSpecialitiesItems");
		assertEquals(2, specialitiesListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		DisciplinesSemestr disciplinesSemestr = disciplinesSemestrFactoryForTest.newDisciplinesSemestr();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		DisciplinesSemestr disciplinesSemestrCreated = new DisciplinesSemestr();
		when(disciplinesSemestrService.create(disciplinesSemestr)).thenReturn(disciplinesSemestrCreated); 
		
		// When
		String viewName = disciplinesSemestrController.create(disciplinesSemestr, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/disciplinesSemestr/form/"+disciplinesSemestr.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplinesSemestrCreated, (DisciplinesSemestr) modelMap.get("disciplinesSemestr"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		DisciplinesSemestr disciplinesSemestr = disciplinesSemestrFactoryForTest.newDisciplinesSemestr();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = disciplinesSemestrController.create(disciplinesSemestr, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("disciplinesSemestr/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplinesSemestr, (DisciplinesSemestr) modelMap.get("disciplinesSemestr"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/disciplinesSemestr/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DisciplinesListItem> disciplinesListItems = (List<DisciplinesListItem>) modelMap.get("listOfDisciplinesItems");
		assertEquals(2, disciplinesListItems.size());
		
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

		DisciplinesSemestr disciplinesSemestr = disciplinesSemestrFactoryForTest.newDisciplinesSemestr();
		
		Exception exception = new RuntimeException("test exception");
		when(disciplinesSemestrService.create(disciplinesSemestr)).thenThrow(exception);
		
		// When
		String viewName = disciplinesSemestrController.create(disciplinesSemestr, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("disciplinesSemestr/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplinesSemestr, (DisciplinesSemestr) modelMap.get("disciplinesSemestr"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/disciplinesSemestr/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "disciplinesSemestr.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<DisciplinesListItem> disciplinesListItems = (List<DisciplinesListItem>) modelMap.get("listOfDisciplinesItems");
		assertEquals(2, disciplinesListItems.size());
		
		@SuppressWarnings("unchecked")
		List<SpecialitiesListItem> specialitiesListItems = (List<SpecialitiesListItem>) modelMap.get("listOfSpecialitiesItems");
		assertEquals(2, specialitiesListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		DisciplinesSemestr disciplinesSemestr = disciplinesSemestrFactoryForTest.newDisciplinesSemestr();
		Integer id = disciplinesSemestr.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		DisciplinesSemestr disciplinesSemestrSaved = new DisciplinesSemestr();
		disciplinesSemestrSaved.setId(id);
		when(disciplinesSemestrService.update(disciplinesSemestr)).thenReturn(disciplinesSemestrSaved); 
		
		// When
		String viewName = disciplinesSemestrController.update(disciplinesSemestr, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/disciplinesSemestr/form/"+disciplinesSemestr.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplinesSemestrSaved, (DisciplinesSemestr) modelMap.get("disciplinesSemestr"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		DisciplinesSemestr disciplinesSemestr = disciplinesSemestrFactoryForTest.newDisciplinesSemestr();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = disciplinesSemestrController.update(disciplinesSemestr, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("disciplinesSemestr/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplinesSemestr, (DisciplinesSemestr) modelMap.get("disciplinesSemestr"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/disciplinesSemestr/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DisciplinesListItem> disciplinesListItems = (List<DisciplinesListItem>) modelMap.get("listOfDisciplinesItems");
		assertEquals(2, disciplinesListItems.size());
		
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

		DisciplinesSemestr disciplinesSemestr = disciplinesSemestrFactoryForTest.newDisciplinesSemestr();
		
		Exception exception = new RuntimeException("test exception");
		when(disciplinesSemestrService.update(disciplinesSemestr)).thenThrow(exception);
		
		// When
		String viewName = disciplinesSemestrController.update(disciplinesSemestr, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("disciplinesSemestr/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplinesSemestr, (DisciplinesSemestr) modelMap.get("disciplinesSemestr"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/disciplinesSemestr/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "disciplinesSemestr.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<DisciplinesListItem> disciplinesListItems = (List<DisciplinesListItem>) modelMap.get("listOfDisciplinesItems");
		assertEquals(2, disciplinesListItems.size());
		
		@SuppressWarnings("unchecked")
		List<SpecialitiesListItem> specialitiesListItems = (List<SpecialitiesListItem>) modelMap.get("listOfSpecialitiesItems");
		assertEquals(2, specialitiesListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		DisciplinesSemestr disciplinesSemestr = disciplinesSemestrFactoryForTest.newDisciplinesSemestr();
		Integer id = disciplinesSemestr.getId();
		
		// When
		String viewName = disciplinesSemestrController.delete(redirectAttributes, id);
		
		// Then
		verify(disciplinesSemestrService).delete(id);
		assertEquals("redirect:/disciplinesSemestr", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		DisciplinesSemestr disciplinesSemestr = disciplinesSemestrFactoryForTest.newDisciplinesSemestr();
		Integer id = disciplinesSemestr.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(disciplinesSemestrService).delete(id);
		
		// When
		String viewName = disciplinesSemestrController.delete(redirectAttributes, id);
		
		// Then
		verify(disciplinesSemestrService).delete(id);
		assertEquals("redirect:/disciplinesSemestr", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "disciplinesSemestr.error.delete", exception);
	}
	
	
}
