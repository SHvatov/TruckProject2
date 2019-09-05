import React, {Component} from 'react';
import PropTypes from 'prop-types';
import Table from "../tables/Table";
import {connect} from "react-redux";
import {addEntity, deleteEntity, fetchEntityList, updateEntity} from "../../../state/Requests";
import Constants from "../../../utils/Constants";
import TruckAdd from "./TruckAdd";
import TruckEdit from "./TruckEdit";

class TruckTableComponent extends Component {

	static propTypes = {
		// user's auth credentials
		credentials: PropTypes.object,

		//  function, which is called to add truck entities
		addTruckEntity: PropTypes.func,
		//  function, which is called to update truck entities
		updateTruckEntity: PropTypes.func,
		//  function, which is called to delete truck entities
		deleteTruckEntity: PropTypes.func,
		//  function, which is called to fetch truck entities
		fetchTruckEntities: PropTypes.func,
	};

	render() {
		let {
			credentials, fetchTruckEntities, addTruckEntity,
			deleteTruckEntity, updateTruckEntity
		} = this.props;
		return (
			<Table refreshTable={() => fetchTruckEntities(credentials)}
				   addEntity={(entity) => addTruckEntity(entity, credentials)}
				   updateEntity={(entity) => updateTruckEntity(entity, credentials)}
				   deleteEntity={(id) => deleteTruckEntity(id, credentials)}
				   addModalBody={<TruckAdd/>}
				   editModalBody={<TruckEdit/>}
				   postfix={{
					   capacity: 'kg.', shiftSize: 'hours',
					   latitude: 'lat.', longitude: 'long.'
				   }}/>
		);
	}
}

/**
 * Maps state state to react properties.
 * @param state - state instance.
 */
const mapStateToProps = state => {
	return {
		credentials: state.credentials
	};
};

/**
 * Dispatches action creator functions to properties.
 * @param dispatch - dispatcher instance.
 */
const mapDispatchToProps = dispatch => {
	return {
		addTruckEntity: (entity, credentials) => dispatch(addEntity(Constants.TRUCK.ADD, entity, credentials)),
		updateTruckEntity: (entity, credentials) => dispatch(updateEntity(Constants.TRUCK.UPDATE, entity, credentials)),
		deleteTruckEntity: (id, credentials) => dispatch(deleteEntity(Constants.TRUCK.DELETE, id, credentials)),
		fetchTruckEntities: (credentials) => dispatch(fetchEntityList(Constants.TRUCK.FETCH, credentials)),
	}
};

const TruckTable = connect(mapStateToProps, mapDispatchToProps)(TruckTableComponent);

export default TruckTable;