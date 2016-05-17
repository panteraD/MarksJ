/*
 * Created on 17 May 2016 ( Time 04:14:29 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
// This Bean has a basic Primary Key (not composite) 

package org.marks.bean.jpa;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import java.util.List;

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "groups"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="groups", schema="sheet" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="GroupsEntity.countAll", query="SELECT COUNT(x) FROM GroupsEntity x" )
} )
public class GroupsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id", nullable=false)
    private Integer    id           ;


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Column(name="course")
    private Integer    course       ;

    @Column(name="group_number")
    private Integer    groupNumber  ;

    @Column(name="faculty", length=80)
    private String     faculty      ;

	// "specialityId" (column "speciality_id") is not defined by itself because used as FK in a link 


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name="speciality_id", referencedColumnName="id")
    private SpecialitiesEntity specialities;

    @OneToMany(mappedBy="groups", targetEntity=StudentsEntity.class)
    private List<StudentsEntity> listOfStudents;

    


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public GroupsEntity() {
		super();
    }
    
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
    //--- DATABASE MAPPING : course ( int4 ) 
    public void setCourse( Integer course ) {
        this.course = course;
    }
    public Integer getCourse() {
        return this.course;
    }

    //--- DATABASE MAPPING : group_number ( int4 ) 
    public void setGroupNumber( Integer groupNumber ) {
        this.groupNumber = groupNumber;
    }
    public Integer getGroupNumber() {
        return this.groupNumber;
    }

    //--- DATABASE MAPPING : faculty ( varchar ) 
    public void setFaculty( String faculty ) {
        this.faculty = faculty;
    }
    public String getFaculty() {
        return this.faculty;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setSpecialities( SpecialitiesEntity specialities ) {
        this.specialities = specialities;
    }
    public SpecialitiesEntity getSpecialities() {
        return this.specialities;
    }

    public void setListOfStudents( List<StudentsEntity> listOfStudents ) {
        this.listOfStudents = listOfStudents;
    }
    public List<StudentsEntity> getListOfStudents() {
        return this.listOfStudents;
    }

    


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(id);
        sb.append("]:"); 
        sb.append(course);
        sb.append("|");
        sb.append(groupNumber);
        sb.append("|");
        sb.append(faculty);
        return sb.toString(); 
    } 

}