/*
 * Created on 17 May 2016 ( Time 04:14:22 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.marks.bean;

import java.io.Serializable;

import javax.validation.constraints.*;

import java.util.Date;

public class Students implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @NotNull
    private Integer id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Size( max = 2147483647 )
    private String fullName;


    private Integer groupId;


    private Integer recordBookNumber;


    private Date startYear;


    private Date endYear;



    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setId( Integer id ) {
        this.id = id ;
    }

    public Integer getId() {
        return this.id;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    public void setFullName( String fullName ) {
        this.fullName = fullName;
    }
    public String getFullName() {
        return this.fullName;
    }

    public void setGroupId( Integer groupId ) {
        this.groupId = groupId;
    }
    public Integer getGroupId() {
        return this.groupId;
    }

    public void setRecordBookNumber( Integer recordBookNumber ) {
        this.recordBookNumber = recordBookNumber;
    }
    public Integer getRecordBookNumber() {
        return this.recordBookNumber;
    }

    public void setStartYear( Date startYear ) {
        this.startYear = startYear;
    }
    public Date getStartYear() {
        return this.startYear;
    }

    public void setEndYear( Date endYear ) {
        this.endYear = endYear;
    }
    public Date getEndYear() {
        return this.endYear;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
 
        public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(fullName);
        sb.append("|");
        sb.append(groupId);
        sb.append("|");
        sb.append(recordBookNumber);
        sb.append("|");
        sb.append(startYear);
        sb.append("|");
        sb.append(endYear);
        return sb.toString(); 
    } 


}
