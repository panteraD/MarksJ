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
 * Persistent class for entity stored in table "discipline_params"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="discipline_params", schema="sheet" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="DisciplineParamsEntity.countAll", query="SELECT COUNT(x) FROM DisciplineParamsEntity x" )
} )
public class DisciplineParamsEntity implements Serializable {

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
    @Column(name="activity_name", length=2147483647)
    private String     activityName ;

    @Column(name="activity_max_quantity")
    private Integer    activityMaxQuantity ;

    @Column(name="score_per_activity")
    private Integer    scorePerActivity ;

    @Column(name="score_max")
    private Integer    scoreMax     ;

	// "disciplineSemestrId" (column "discipline_semestr_id") is not defined by itself because used as FK in a link 


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name="discipline_semestr_id", referencedColumnName="id")
    private DisciplinesSemestrEntity disciplinesSemestr;

    @OneToMany(mappedBy="disciplineParams", targetEntity=StudentsDisciplineSemestrEntity.class)
    private List<StudentsDisciplineSemestrEntity> listOfStudentsDisciplineSemestr;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public DisciplineParamsEntity() {
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
    //--- DATABASE MAPPING : activity_name ( text ) 
    public void setActivityName( String activityName ) {
        this.activityName = activityName;
    }
    public String getActivityName() {
        return this.activityName;
    }

    //--- DATABASE MAPPING : activity_max_quantity ( int4 ) 
    public void setActivityMaxQuantity( Integer activityMaxQuantity ) {
        this.activityMaxQuantity = activityMaxQuantity;
    }
    public Integer getActivityMaxQuantity() {
        return this.activityMaxQuantity;
    }

    //--- DATABASE MAPPING : score_per_activity ( int4 ) 
    public void setScorePerActivity( Integer scorePerActivity ) {
        this.scorePerActivity = scorePerActivity;
    }
    public Integer getScorePerActivity() {
        return this.scorePerActivity;
    }

    //--- DATABASE MAPPING : score_max ( int4 ) 
    public void setScoreMax( Integer scoreMax ) {
        this.scoreMax = scoreMax;
    }
    public Integer getScoreMax() {
        return this.scoreMax;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setDisciplinesSemestr( DisciplinesSemestrEntity disciplinesSemestr ) {
        this.disciplinesSemestr = disciplinesSemestr;
    }
    public DisciplinesSemestrEntity getDisciplinesSemestr() {
        return this.disciplinesSemestr;
    }

    public void setListOfStudentsDisciplineSemestr( List<StudentsDisciplineSemestrEntity> listOfStudentsDisciplineSemestr ) {
        this.listOfStudentsDisciplineSemestr = listOfStudentsDisciplineSemestr;
    }
    public List<StudentsDisciplineSemestrEntity> getListOfStudentsDisciplineSemestr() {
        return this.listOfStudentsDisciplineSemestr;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(id);
        sb.append("]:"); 
        sb.append(activityName);
        sb.append("|");
        sb.append(activityMaxQuantity);
        sb.append("|");
        sb.append(scorePerActivity);
        sb.append("|");
        sb.append(scoreMax);
        return sb.toString(); 
    } 

}
