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
import org.marks.bean.Students;
import org.marks.business.service.StudentsService;
import org.marks.web.listitem.StudentsListItem;

/**
 * Spring MVC controller for 'Students' management.
 */
@Controller
public class StudentsRestController {

	@Resource
	private StudentsService studentsService;
	
	@RequestMapping( value="/items/students",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<StudentsListItem> findAllAsListItems() {
		List<Students> list = studentsService.findAll();
		List<StudentsListItem> items = new LinkedList<StudentsListItem>();
		for ( Students students : list ) {
			items.add(new StudentsListItem( students ) );
		}
		return items;
	}
	
	@RequestMapping( value="/students",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Students> findAll() {
		return studentsService.findAll();
	}

	@RequestMapping( value="/students/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Students findOne(@PathVariable("id") Integer id) {
		return studentsService.findById(id);
	}
	
	@RequestMapping( value="/students",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Students create(@RequestBody Students students) {
		return studentsService.create(students);
	}

	@RequestMapping( value="/students/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Students update(@PathVariable("id") Integer id, @RequestBody Students students) {
		return studentsService.update(students);
	}

	@RequestMapping( value="/students/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Integer id) {
		studentsService.delete(id);
	}
	
}
