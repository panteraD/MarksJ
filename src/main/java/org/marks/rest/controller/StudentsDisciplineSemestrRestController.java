/*
 * Created on 17 May 2016 ( Time 04:14:22 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.marks.rest.controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.marks.bean.StudentsDisciplineSemestr;
import org.marks.business.service.StudentsDisciplineSemestrService;
import org.marks.web.listitem.StudentsDisciplineSemestrListItem;

/**
 * Spring MVC controller for 'StudentsDisciplineSemestr' management.
 */
@Controller
public class StudentsDisciplineSemestrRestController {

	@Resource
	private StudentsDisciplineSemestrService studentsDisciplineSemestrService;
	
	@RequestMapping( value="/items/studentsDisciplineSemestr",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<StudentsDisciplineSemestrListItem> findAllAsListItems() {
		List<StudentsDisciplineSemestr> list = studentsDisciplineSemestrService.findAll();
		List<StudentsDisciplineSemestrListItem> items = new LinkedList<StudentsDisciplineSemestrListItem>();
		for ( StudentsDisciplineSemestr studentsDisciplineSemestr : list ) {
			items.add(new StudentsDisciplineSemestrListItem( studentsDisciplineSemestr ) );
		}
		return items;
	}
	
	@RequestMapping( value="/studentsDisciplineSemestr",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<StudentsDisciplineSemestr> findAll() {
		return studentsDisciplineSemestrService.findAll();
	}

	@RequestMapping( value="/studentsDisciplineSemestr/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public StudentsDisciplineSemestr findOne(@PathVariable("id") Integer id) {
		return studentsDisciplineSemestrService.findById(id);
	}
	
	@RequestMapping( value="/studentsDisciplineSemestr",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public StudentsDisciplineSemestr create(@RequestBody StudentsDisciplineSemestr studentsDisciplineSemestr) {
		return studentsDisciplineSemestrService.create(studentsDisciplineSemestr);
	}

	@RequestMapping( value="/studentsDisciplineSemestr/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public StudentsDisciplineSemestr update(@PathVariable("id") Integer id, @RequestBody StudentsDisciplineSemestr studentsDisciplineSemestr) {
		return studentsDisciplineSemestrService.update(studentsDisciplineSemestr);
	}

	@RequestMapping( value="/studentsDisciplineSemestr/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Integer id) {
		studentsDisciplineSemestrService.delete(id);
	}
	
}
