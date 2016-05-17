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
import org.marks.bean.Teachers;
import org.marks.business.service.TeachersService;
import org.marks.web.listitem.TeachersListItem;

/**
 * Spring MVC controller for 'Teachers' management.
 */
@Controller
public class TeachersRestController {

	@Resource
	private TeachersService teachersService;
	
	@RequestMapping( value="/items/teachers",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<TeachersListItem> findAllAsListItems() {
		List<Teachers> list = teachersService.findAll();
		List<TeachersListItem> items = new LinkedList<TeachersListItem>();
		for ( Teachers teachers : list ) {
			items.add(new TeachersListItem( teachers ) );
		}
		return items;
	}
	
	@RequestMapping( value="/teachers",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Teachers> findAll() {
		return teachersService.findAll();
	}

	@RequestMapping( value="/teachers/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Teachers findOne(@PathVariable("id") Integer id) {
		return teachersService.findById(id);
	}
	
	@RequestMapping( value="/teachers",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Teachers create(@RequestBody Teachers teachers) {
		return teachersService.create(teachers);
	}

	@RequestMapping( value="/teachers/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Teachers update(@PathVariable("id") Integer id, @RequestBody Teachers teachers) {
		return teachersService.update(teachers);
	}

	@RequestMapping( value="/teachers/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Integer id) {
		teachersService.delete(id);
	}
	
}
