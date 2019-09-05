import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {Alert, Button, Col, Form, FormGroup, Modal, ModalBody, Row} from "react-bootstrap";
import SubmitButton from "../input/SubmitButton";
import {updateStatusById} from "../../../state/Status";
import {connect} from "react-redux";

/**
 * Delete element modal window, constructed using bootstrap components.
 * @author Sergey Hvatov
 */
class DeleteModalComponent extends Component {

	static propTypes = {
		// entity object
		entity: PropTypes.object,
		// status of the entity
		status: PropTypes.object,
		// function, which is called to delete entity
		deleteEntity: PropTypes.func,

		// error object
		error: PropTypes.object,
		// function, which is called to change status when
		// delete button is pressed
		showDeleteModal: PropTypes.func,
		// function, which is called to change status when
		// cancel button is pressed
		hideDeleteModal: PropTypes.func,
	};

	render() {
		let {
			entity, status, deleteEntity,
			showDeleteModal, hideDeleteModal, error
		} = this.props;
		return (
			<div>
				<Button variant='danger' onClick={() => showDeleteModal(entity['id'])}>
					Delete
				</Button>
				<Modal show={status ? status['del'] : false} size='lg'
					   aria-labelledby="contained-modal-title-vcenter"
					   centered>
					<Modal.Header>
						<Modal.Title id="contained-modal-title-vcenter">
							<strong>Deleting entity: #{entity['id']}</strong>
						</Modal.Title>
					</Modal.Header>
					<ModalBody>
						<h4>Are you sure, that you want to delete this entity?</h4>
						<Row>
							<Alert variant="danger" hidden={!error[entity['id'] + 'Error']}>
								<strong>Error: </strong>{error[entity['id'] + 'Error']}
							</Alert>
						</Row>
					</ModalBody>
					<Modal.Footer>
						<Form.Row className={'justify-content-center'}>
							<FormGroup as={Col} className={'text-center'}>
								<SubmitButton variant='danger' onClickHandler={() => deleteEntity(entity['id'])}
											  isFetching={status ? status['fetch'] : false} text='Delete'/>
								<Button variant='danger'
										onClick={() => hideDeleteModal(entity['id'])}>Cancel</Button>
							</FormGroup>
						</Form.Row>
					</Modal.Footer>
				</Modal>
			</div>
		);
	}
}

/**
 * Maps state state to react properties.
 * @param state - state instance.
 */
const mapStateToProps = state => {
	return {
		error: state.error
	};
};

/**
 * Dispatches action creator functions to properties.
 * @param dispatch - dispatcher instance.
 */
const mapDispatchToProps = dispatch => {
	return {
		showDeleteModal: (id) => dispatch(updateStatusById(id, {del: true})),
		hideDeleteModal: (id) => dispatch(updateStatusById(id, {del: false})),
	}
};

const DeleteModal = connect(mapStateToProps, mapDispatchToProps)(DeleteModalComponent);

export default DeleteModal;