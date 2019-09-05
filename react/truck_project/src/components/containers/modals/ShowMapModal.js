import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {Map, Placemark, ZoomControl} from "react-yandex-maps";
import {Button, Col, Form, FormGroup, Modal, ModalBody} from "react-bootstrap";
import {connect} from "react-redux";
import {updateStatusById} from "../../../state/Status";

/**
 * Show element on the map modal window, constructed using bootstrap components.
 * @author Sergey Hvatov
 */
class ShowMapModalComponent extends Component {

	static propTypes = {
		// entity object
		entity: PropTypes.object,
		// status of the entity
		status: PropTypes.object,

		// function, which is called to change status when
		// map button is pressed
		showMapModal: PropTypes.func,
		// function, which is called to change status when
		// cancel button is pressed
		hideMapModal: PropTypes.func,
	};

	render() {
		let {
			entity, status,
			showMapModal, hideMapModal
		} = this.props;
		return (
			<div>
				<Button variant='info' onClick={() => showMapModal(entity['id'])}>
					Map
				</Button>
				<Modal show={status ? status['map'] : false} size='lg'
					   aria-labelledby="contained-modal-title-vcenter"
					   centered>
					<Modal.Header>
						<Modal.Title id="contained-modal-title-vcenter">
							<strong>Location of the entity: #{entity['id']}</strong>
						</Modal.Title>
					</Modal.Header>
					<ModalBody>
						<div className='table-row'>
							<Map defaultState={{
								center: [entity['longitude'], entity['latitude']],
								zoom: 7,
								controls: []
							}} modules={[
								'geoObject.addon.balloon',
								'geoObject.addon.hint'
							]} className='map'>
								<Placemark geometry={[entity['longitude'], entity['latitude']]}
										   properties={{
											   hintContent: 'Current position of the entity',
										   }}/>
								<ZoomControl options={{float: 'right'}}/>
							</Map>
						</div>
					</ModalBody>
					<Modal.Footer>
						<Form.Row className={'justify-content-center'}>
							<FormGroup as={Col} className={'text-center'}>
								<Button variant='primary'
										onClick={() => hideMapModal(entity['id'])}>Close</Button>
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
	return {};
};

/**
 * Dispatches action creator functions to properties.
 * @param dispatch - dispatcher instance.
 */
const mapDispatchToProps = dispatch => {
	return {
		showMapModal: (id) => dispatch(updateStatusById(id, {map: true})),
		hideMapModal: (id) => dispatch(updateStatusById(id, {map: false})),
	}
};

const ShowMapModal = connect(mapStateToProps, mapDispatchToProps)(ShowMapModalComponent);

export default ShowMapModal;