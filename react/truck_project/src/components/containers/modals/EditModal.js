import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {Alert, Button, Col, Modal, ModalBody, Row} from "react-bootstrap";
import SubmitButton from "../input/SubmitButton";
import {updateStatusById} from "../../../state/Status";
import {connect} from "react-redux";
import {multipleActions} from "../../../state/Requests";
import {clearBuffer, updateBuffer} from "../../../state/Buffer";
import {clearError} from "../../../state/Error";

/**
 * Edit element modal window, constructed using bootstrap components.
 * @author Sergey Hvatov
 */
class EditModalComponent extends Component {

	static propTypes = {
		// entity object
		entity: PropTypes.object,
		// status of the entity
		status: PropTypes.object,
		// function, which is called to update entity
		updateEntity: PropTypes.func,

		// error object
		error: PropTypes.object,
		// buffer of the application
		buffer: PropTypes.object,
		// function, which is called to change status when
		// delete button is pressed
		showEditModal: PropTypes.func,
		// function, which is called to change status when
		// cancel button is pressed
		hideEditModal: PropTypes.func,
	};

	render() {
		let {
			entity, status, updateEntity, buffer,
			showEditModal, hideEditModal, error
		} = this.props;
		return (
			<div>
				<Button variant='primary' onClick={() => showEditModal(entity)}>
					Edit
				</Button>
				<Modal show={status ? status['edit'] : false} size='lg'
					   aria-labelledby="contained-modal-title-vcenter"
					   centered>
					<Modal.Header>
						<Modal.Title id="contained-modal-title-vcenter">
							<strong>Editing entity: #{entity['id']}</strong>
						</Modal.Title>
					</Modal.Header>
					<ModalBody>
						{this.props.children}
						<Row className='table-row' hidden={!error['id']}>
							<Alert variant="danger" hidden={!error['id']} className='custom-alert'>
								<strong>Error: </strong>{error['id']}
							</Alert>
						</Row>
						<Row Row className='table-row' hidden={!error[entity['id'] + 'Error']}>
							<Alert variant="danger" hidden={!error[entity['id'] + 'Error']}>
								<strong>Error: </strong>{error[entity['id'] + 'Error']}
							</Alert>
						</Row>
					</ModalBody>
					<Modal.Footer>
						<Row>
							<Col className='table-button'>
								<SubmitButton variant='primary' onClickHandler={() => updateEntity(buffer)}
											  isFetching={status ? status['fetch'] : false} text='Update'/>
							</Col>
							<Col className='table-button'>
								<Button variant='danger'
										onClick={() => hideEditModal(entity['id'])}>Cancel</Button>
							</Col>
						</Row>
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
		buffer: state.buffer,
		error: state.error
	};
};

/**
 * Dispatches action creator functions to properties.
 * @param dispatch - dispatcher instance.
 */
const mapDispatchToProps = dispatch => {
	return {
		showEditModal: (entity) => dispatch(multipleActions([
			updateStatusById(entity['id'], {edit: true}),
			updateBuffer(entity),
			clearError()
		])),
		hideEditModal: (id) => dispatch(multipleActions([
			updateStatusById(id, {edit: false}),
			clearBuffer(),
			clearError()
		])),
	}
};

const EditModal = connect(mapStateToProps, mapDispatchToProps)(EditModalComponent);

export default EditModal;