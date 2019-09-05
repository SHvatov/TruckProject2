import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {Alert, Button, Col, Modal, ModalBody, Row} from "react-bootstrap";
import SubmitButton from "../input/SubmitButton";
import {updateStatusById} from "../../../state/Status";
import {connect} from "react-redux";
import {multipleActions} from "../../../state/Requests";
import {clearBuffer} from "../../../state/Buffer";
import {clearError} from "../../../state/Error";

/**
 * Add element modal window, constructed using bootstrap components.
 * @author Sergey Hvatov
 */
/**
 * Edit element modal window, constructed using bootstrap components.
 * @author Sergey Hvatov
 */
class AddModalComponent extends Component {

	static propTypes = {
		// id of the page, that is calling this modal
		id: PropTypes.string,
		// status of the entity
		status: PropTypes.object,
		// function, which is called to update entity
		addEntity: PropTypes.func,

		// error object
		error: PropTypes.object,
		// buffer of the application
		buffer: PropTypes.object,
		// function, which is called to change status when
		// delete button is pressed
		showAddModal: PropTypes.func,
		// function, which is called to change status when
		// cancel button is pressed
		hideAddModal: PropTypes.func,
	};

	render() {
		let {
			id, status, addEntity, buffer,
			showAddModal, hideAddModal, error
		} = this.props;
		return (
			<div>
				<Button variant='danger' onClick={() => showAddModal(id)}>
					Add
				</Button>
				<Modal show={status ? status['add'] : false} size='lg'
					   aria-labelledby="contained-modal-title-vcenter"
					   centered>
					<Modal.Header>
						<Modal.Title id="contained-modal-title-vcenter">
							<strong>Add new entity</strong>
						</Modal.Title>
					</Modal.Header>
					<ModalBody>
						{this.props.children}
						<Row className='table-row' hidden={!error[id + 'Error']}>
							<Alert variant="danger" hidden={!error[id + 'Error']} className='custom-alert'>
								<strong>Error: </strong>{error[id + 'Error']}
							</Alert>
						</Row>
					</ModalBody>
					<Modal.Footer>
						<Row>
							<Col className='table-button'>
								<SubmitButton variant='info' onClickHandler={() => addEntity(buffer)}
											  isFetching={status ? status['fetch'] : false} text='Save'/>
							</Col>
							<Col className='table-button'>
								<Button variant='danger'
										onClick={() => hideAddModal(id)}>Cancel</Button>
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
		showAddModal: (id) => dispatch(multipleActions([
			updateStatusById(id, {add: true}),
			clearBuffer(),
			clearError()
		])),
		hideAddModal: (id) => dispatch(multipleActions([
			updateStatusById(id, {add: false}),
			clearBuffer(),
			clearError()
		])),
	}
};

const AddModal = connect(mapStateToProps, mapDispatchToProps)(AddModalComponent);

export default AddModal;