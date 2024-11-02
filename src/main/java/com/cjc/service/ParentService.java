package com.cjc.service;

import java.util.List;

import com.cjc.model.Parent;
import com.cjc.model.Tutor;

public interface ParentService {

	void addParent(Parent parent);

	List<Parent> viewAllParents();

	String parentLogin(Parent parent);

	Parent getParentById(long parentmobileno);

	void updateParent(Parent existingParent);

	void sendDemoRequestEmailToParent(Parent existingParent,Tutor existingTutor);

	void acceptedDemoRequestByTutorMail(Parent existingParent, Tutor existingTutor);

	void saveParentAgain(Parent existingParent);

	void senBookedTutorEmailToParent(Parent existingParent, Tutor existingTutor);

	

	

}
