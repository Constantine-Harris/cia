/*
 * Copyright 2010 James Pether Sörling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *	$Id$
 *  $HeadURL$
*/
package com.hack23.cia.service.component.agent.impl.riksdagen.workers.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hack23.cia.model.external.riksdagen.documentcontent.impl.DocumentContentData;
import com.hack23.cia.model.external.riksdagen.documentcontent.impl.DocumentContentData_;
import com.hack23.cia.model.external.riksdagen.dokumentlista.impl.DocumentElement;
import com.hack23.cia.model.external.riksdagen.dokumentstatus.impl.DocumentData;
import com.hack23.cia.model.external.riksdagen.dokumentstatus.impl.DocumentData_;
import com.hack23.cia.model.external.riksdagen.dokumentstatus.impl.DocumentStatusContainer;
import com.hack23.cia.model.external.riksdagen.dokumentstatus.impl.DocumentStatusContainer_;
import com.hack23.cia.model.external.riksdagen.person.impl.AssignmentData;
import com.hack23.cia.model.external.riksdagen.person.impl.PersonAssignmentData;
import com.hack23.cia.model.external.riksdagen.person.impl.PersonData;
import com.hack23.cia.model.external.riksdagen.utskottsforslag.impl.CommitteeProposalComponentData;
import com.hack23.cia.model.external.riksdagen.utskottsforslag.impl.CommitteeProposalComponentData_;
import com.hack23.cia.model.external.riksdagen.votering.impl.VoteData;
import com.hack23.cia.model.external.riksdagen.votering.impl.VoteDataEmbeddedId;
import com.hack23.cia.model.external.riksdagen.votering.impl.VoteDataEmbeddedId_;
import com.hack23.cia.model.external.riksdagen.votering.impl.VoteData_;
import com.hack23.cia.service.data.api.CommitteeProposalComponentDataDAO;
import com.hack23.cia.service.data.api.DocumentContentDataDAO;
import com.hack23.cia.service.data.api.DocumentElementDAO;
import com.hack23.cia.service.data.api.DocumentStatusContainerDAO;
import com.hack23.cia.service.data.api.PersonDataDAO;
import com.hack23.cia.service.data.api.VoteDataDAO;

/**
 * The Class RiksdagenUpdateServiceImpl.
 */
@Component("RiksdagenUpdateService")
@Transactional(propagation = Propagation.MANDATORY)
final class RiksdagenUpdateServiceImpl implements RiksdagenUpdateService {

	/** The committee proposal component data DAO. */
	@Autowired
	private CommitteeProposalComponentDataDAO committeeProposalComponentDataDAO;

	/** The document content data DAO. */
	@Autowired
	private DocumentContentDataDAO documentContentDataDAO;

	/** The document element DAO. */
	@Autowired
	private DocumentElementDAO documentElementDAO;

	/** The document status container DAO. */
	@Autowired
	private DocumentStatusContainerDAO documentStatusContainerDAO;

	/** The person data DAO. */
	@Autowired
	private PersonDataDAO personDataDAO;

	/** The vote data DAO. */
	@Autowired
	private VoteDataDAO voteDataDAO;

	/**
	 * Instantiates a new riksdagen update service impl.
	 */
	public RiksdagenUpdateServiceImpl() {
		super();
	}

	@Override
	public void update(final PersonData personData) {
		final PersonData existData = personDataDAO.load(personData.getId());
		if (existData == null) {
			personDataDAO.persist(personData);
		} else {
			existData.setElectionRegion(personData.getElectionRegion());
			existData.setParty(personData.getParty());
			existData.setStatus(personData.getStatus());
			existData.setLastName(personData.getLastName());
			existData.setPlace(personData.getPlace());

			updatePersonAssignmentData(existData.getPersonAssignmentData(), personData.getPersonAssignmentData());

			personDataDAO.persist(existData);
		}
	}

	/**
	 * Update person assignment data.
	 *
	 * @param exist  the exist
	 * @param update the update
	 */
	private static void updatePersonAssignmentData(final PersonAssignmentData exist, final PersonAssignmentData update) {

		List<AssignmentData> assignmentList = update.getAssignmentList();

		for (AssignmentData assignmentData : assignmentList) {
			updateAssignmentData(exist.getAssignmentList(), assignmentData);
		}
	}

	/**
	 * Update assignment data.
	 *
	 * @param assignmentList the assignment list
	 * @param assignmentData the assignment data
	 */
	private static void updateAssignmentData(final List<AssignmentData> assignmentList, final AssignmentData assignmentData) {
		for (AssignmentData matchAssignmentData : assignmentList) {
			if (matchAssignmentData.getFromDate().equals(assignmentData.getFromDate())
					&& matchAssignmentData.getOrgCode().equals(assignmentData.getOrgCode()) && matchAssignmentData.getRoleCode().equals(assignmentData.getRoleCode())) {

				matchAssignmentData.setStatus(assignmentData.getStatus());
				matchAssignmentData.setToDate(assignmentData.getToDate());
				return;
			}
		}
		assignmentList.add(assignmentData);
	}

	@Override
	public void updateCommitteeProposalComponentData(final CommitteeProposalComponentData committeeProposal) {
		if (committeeProposalComponentDataDAO.findFirstByProperty(CommitteeProposalComponentData_.document,
				committeeProposal.getDocument()) == null) {
			committeeProposalComponentDataDAO.persist(committeeProposal);
		}
	}

	@Override
	public void updateDocumentContentData(final DocumentContentData documentData) {
		if (documentContentDataDAO.findFirstByProperty(DocumentContentData_.id, documentData.getId()) == null) {
			documentContentDataDAO.persist(documentData);
		}
	}

	@Override
	public void updateDocumentData(final DocumentStatusContainer documentData) {
		if (documentStatusContainerDAO.findListByEmbeddedProperty(DocumentStatusContainer_.document, DocumentData.class,
				DocumentData_.id, documentData.getDocument().getId()).isEmpty()) {
			documentStatusContainerDAO.persist(documentData);
		}
	}

	@Override
	public void updateDocumentElement(final DocumentElement documentData) {
		DocumentElement existDocumentElement = documentElementDAO.load(documentData.getId());
		if (existDocumentElement == null) {
			documentElementDAO.persist(documentData);
		} else {			
			existDocumentElement.setCommitteeReportUrlXml(documentData.getCommitteeReportUrlXml());
			existDocumentElement.setStatus(documentData.getStatus());
			existDocumentElement.setDebateName(documentData.getDebateName());
			existDocumentElement.setRelatedId(documentData.getRelatedId());
			existDocumentElement.setSystemDate(documentData.getSystemDate());
			existDocumentElement.setMadePublicDate(documentData.getMadePublicDate());			
			existDocumentElement.setLabel(documentData.getLabel());
			existDocumentElement.setNote(documentData.getNote());
			existDocumentElement.setNoteTitle(documentData.getNoteTitle());
			existDocumentElement.setTempLabel(documentData.getTempLabel());
			existDocumentElement.setTitle(documentData.getTitle());
			documentElementDAO.persist(existDocumentElement);
		}
	}

	@Override
	public void updateVoteDataData(final List<VoteData> list) {
		if (list != null && !list.isEmpty()
				&& voteDataDAO.findListByEmbeddedProperty(VoteData_.embeddedId, VoteDataEmbeddedId.class,
						VoteDataEmbeddedId_.ballotId, list.get(0).getEmbeddedId().getBallotId()).isEmpty()) {
			voteDataDAO.persist(list);
		}
	}

}
